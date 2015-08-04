package book.twju.timeline.model;

import static book.twju.timeline.model.FakeItems.FIRST_ITEM;
import static book.twju.timeline.model.FakeItems.SECOND_ITEM;
import static book.twju.timeline.model.FakeItems.THIRD_ITEM;
import static book.twju.timeline.model.FetchItemsData.createMemento;
import static book.twju.timeline.model.MementoAssert.assertThat;
import static book.twju.timeline.model.Timeline.ERROR_EXCEEDS_LOWER_BOUND;
import static book.twju.timeline.model.Timeline.ERROR_EXCEEDS_UPPER_BOUND;
import static book.twju.timeline.model.Timeline.ERROR_ITEM_PROVIDER_MUST_NOT_BE_NULL;
import static book.twju.timeline.model.Timeline.ERROR_SESSION_PROVIDER_MUST_NOT_BE_NULL;
import static book.twju.timeline.model.Timeline.ERROR_TOP_ITEM_MUST_NOT_BE_NULL;
import static book.twju.timeline.model.Timeline.FETCH_COUNT_LOWER_BOUND;
import static book.twju.timeline.model.Timeline.FETCH_COUNT_UPPER_BOUND;
import static book.twju.timeline.test.util.ThrowableCaptor.thrownBy;
import static book.twju.timeline.util.Assertion.formatErrorMessage;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import com.squareup.burst.BurstJUnit4;

@RunWith( BurstJUnit4.class )
public class TimelineTest {
  
  private static final int SECOND_STORAGE_CALL = 2;
  
  private SessionStorage<FakeItem> sessionStorage;
  private FakeItemProviderStub itemProvider;
  private Timeline<FakeItem> timeline;
  
  @Before
  public void setUp() {
    itemProvider = new FakeItemProviderStub();
    sessionStorage = stubSessionStorage();
    timeline = new Timeline<>( itemProvider, sessionStorage );
  }
  
  @Test
  public void initialStatus() {
    assertThat( timeline.getNewCount() ).isZero();
    assertThat( timeline.getItems().isEmpty() );
    assertThat( timeline.getTopItem() ).isEmpty();
    assertThat( timeline.getFetchCount() )
      .isGreaterThanOrEqualTo( FETCH_COUNT_LOWER_BOUND )
      .isLessThanOrEqualTo( FETCH_COUNT_UPPER_BOUND );
  }
  
  @Test
  public void restore() {
    when( sessionStorage.read() ).thenReturn( createMemento( FIRST_ITEM, SECOND_ITEM, FIRST_ITEM ) );
    
    Timeline<FakeItem> actual = new Timeline<>( itemProvider, sessionStorage );
    
    assertThat( actual.getItems() ).containsExactly( SECOND_ITEM, FIRST_ITEM );
    assertThat( actual.getTopItem() ).contains( FIRST_ITEM );
  }
  
  @Test
  public void setFetchCount() {
    int expected = timeline.getFetchCount() + 1;
    
    timeline.setFetchCount( expected  );
    int actual = timeline.getFetchCount();
    
    assertThat( actual ).isEqualTo( expected );
  }
  
  @Test
  public void setFetchCountExceedsLowerBound() {
    int tooSmall = FETCH_COUNT_LOWER_BOUND - 1;

    Throwable actual = thrownBy( () -> timeline.setFetchCount( tooSmall ) );

    assertThat( actual )
      .isInstanceOf( IllegalArgumentException.class )
      .hasMessage( formatErrorMessage( ERROR_EXCEEDS_LOWER_BOUND, tooSmall ) );
  }

  @Test
  public void setFetchCountExceedsUpperBound() {
    int tooLarge = FETCH_COUNT_UPPER_BOUND + 1;
    
    Throwable actual = thrownBy( () -> timeline.setFetchCount( tooLarge ) );
    
    assertThat( actual )
      .isInstanceOf( IllegalArgumentException.class )
      .hasMessage( formatErrorMessage( ERROR_EXCEEDS_UPPER_BOUND, tooLarge ) );
  }
  
  @Test
  public void fetchItems( FetchItemsData data ) {
    itemProvider.addItems( data.getInput() );
    timeline.setFetchCount( data.getPageSize() );    
    timeline.fetchItems();

    timeline.fetchItems();
    List<FakeItem> actual = timeline.getItems();
      
    assertThat( data.getOutput() ).containsExactlyElementsOf( actual );
    assertThat( captureStorageMemento( SECOND_STORAGE_CALL ) ).isEqualTo( data.getMemento() );
  }
  
  @Test
  public void getNewCountIfNoNewItemIsAvailable() {
    itemProvider.addItems( FIRST_ITEM, SECOND_ITEM );
    timeline.fetchItems();

    int actual = timeline.getNewCount();

    assertThat( actual ).isEqualTo( 0 );
  }
  
  @Test
  public void getNewCountIfNewItemIsAvailable() {
    itemProvider.addItems( FIRST_ITEM, SECOND_ITEM );
    timeline.fetchItems();
    itemProvider.addItems( THIRD_ITEM );
    
    int actual = timeline.getNewCount();
    
    assertThat( actual ).isEqualTo( 1 );
  }

  @Test
  public void fetchNew() {
    itemProvider.addItems( FIRST_ITEM, SECOND_ITEM );
    timeline.fetchItems();
    itemProvider.addItems( THIRD_ITEM );

    timeline.fetchNew();
    List<FakeItem> actual = timeline.getItems();
     
    assertThat( actual ).containsExactly( THIRD_ITEM, SECOND_ITEM, FIRST_ITEM );
    assertThat( captureStorageMemento( SECOND_STORAGE_CALL ) )
     .isEqualTo( createMemento( SECOND_ITEM, THIRD_ITEM, SECOND_ITEM, FIRST_ITEM ) );
  }
  
  @Test
  public void fetchNewIfNoItemIsAvailable() {
    itemProvider.addItems( FIRST_ITEM, SECOND_ITEM );
    timeline.fetchItems();
    
    timeline.fetchNew();
    List<FakeItem> actual = timeline.getItems();
    
    assertThat( actual ).containsExactly( SECOND_ITEM, FIRST_ITEM );
    assertThat( captureStorageMemento( SECOND_STORAGE_CALL ) )
      .isEqualTo( createMemento( SECOND_ITEM, SECOND_ITEM, FIRST_ITEM ) );
  }
  
  @Test
  public void setTopItem() {
    itemProvider.addItems( FIRST_ITEM, SECOND_ITEM );
    timeline.fetchItems();
    
    timeline.setTopItem( FIRST_ITEM );

    assertThat( timeline.getTopItem() ).contains( FIRST_ITEM );
    assertThat( captureStorageMemento( SECOND_STORAGE_CALL ) )
      .isEqualTo( createMemento( FIRST_ITEM, SECOND_ITEM, FIRST_ITEM ) );
  }

  @Test
  public void setTopItemIfNoElementOfItemList() {
    Throwable actual = thrownBy( () -> timeline.setTopItem( FIRST_ITEM ) );
    
    assertThat( actual )
      .hasMessageContaining( FIRST_ITEM.getId() )
      .isInstanceOf( IllegalArgumentException.class );
  }
  
  @Test
  public void setTopItemWithNull() {
    Throwable actual = thrownBy( () -> timeline.setTopItem( null ) );
    
    assertThat( actual )
      .hasMessageContaining( ERROR_TOP_ITEM_MUST_NOT_BE_NULL )
      .isInstanceOf( IllegalArgumentException.class );
  }
  
  @Test
  public void constructWithNullAsItemProvider() {
    Throwable actual = thrownBy( () -> new Timeline<>( null, sessionStorage ) );
    
    assertThat( actual )
      .hasMessage( ERROR_ITEM_PROVIDER_MUST_NOT_BE_NULL )
      .isInstanceOf( IllegalArgumentException.class );
  }
  
  @Test
  public void constructWithNullAsSessionStorage() {
    Throwable actual = thrownBy( () -> new Timeline<>( itemProvider, null ) );
     
    assertThat( actual )
      .hasMessage( ERROR_SESSION_PROVIDER_MUST_NOT_BE_NULL )
      .isInstanceOf( IllegalArgumentException.class );
  }
  
  @SuppressWarnings( { "rawtypes", "unchecked" } )
  private Memento<FakeItem> captureStorageMemento( int numberOfInvocations ) {
    ArgumentCaptor<Memento> captor = forClass( Memento.class );
    verify( sessionStorage, times( numberOfInvocations ) ).store( captor.capture() );
    return captor.getValue();
  }

  @SuppressWarnings("unchecked")
  private SessionStorage<FakeItem> stubSessionStorage() {
    SessionStorage<FakeItem> result = mock( SessionStorage.class );
    when( result.read() ).thenReturn( Memento.empty() );
    return result;
  }
}