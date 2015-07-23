package book.twju.chapter_5;

import static book.twju.chapter_5.ThrowableCaptor.thrownBy;
import static book.twju.chapter_5.Timeline.ERROR_EXCEEDS_LOWER_BOUND;
import static book.twju.chapter_5.Timeline.ERROR_EXCEEDS_UPPER_BOUND;
import static book.twju.chapter_5.Timeline.FETCH_COUNT_LOWER_BOUND;
import static book.twju.chapter_5.Timeline.FETCH_COUNT_UPPER_BOUND;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import com.squareup.burst.BurstJUnit4;

@Category( Unit.class )
@RunWith( BurstJUnit4.class )
public class TimelineTest {
  
  private static final FakeItem FIRST_ITEM = new FakeItem( 10 );
  private static final FakeItem SECOND_ITEM = new FakeItem( 20 );
  private static final FakeItem THIRD_ITEM = new FakeItem( 30 );
  private static final FakeItem FOURTH_ITEM = new FakeItem( 40 );
  private static final FakeItem FIFTH_ITEM = new FakeItem( 50 );
  private static final FakeItem SIXTH_ITEM = new FakeItem( 60 );
  
  private SessionStorage sessionStorage;
  private ItemProviderStub itemProvider;
  private Timeline timeline;
  
  @Before
  public void setUp() {
    itemProvider = new ItemProviderStub();
    sessionStorage = mock( SessionStorage.class );
    timeline = new Timeline( itemProvider, sessionStorage );
  }
  

  @Test
  public void fetchItems( FetchItemsEnum data ) {
    itemProvider.addItems( data.getInput() );
    timeline.setFetchCount( data.getFetchCount() );    
    timeline.fetchItems();

    timeline.fetchItems();
    List<Item> actual = timeline.getItems();
      
    assertArrayEquals( data.getOutput(), 
                       actual.toArray() );
  }

  @Test
  public void setFetchCountExceedsUpperBound() {
    int tooLarge = FETCH_COUNT_UPPER_BOUND + 1;
      
    Throwable actual = thrownBy( () -> timeline.setFetchCount( tooLarge ) );

    assertNotNull( actual );  
    assertTrue( actual instanceof IllegalArgumentException );
    assertTrue( actual.getMessage().contains( valueOf( tooLarge ) ) );
    assertEquals( format( ERROR_EXCEEDS_UPPER_BOUND, tooLarge ), 
                  actual.getMessage() );
  }
  
  @Test
  public void setFetchCountExceedsLowerBound() {
    int tooSmall = FETCH_COUNT_LOWER_BOUND - 1;
    
    Throwable actual = thrownBy( () -> timeline.setFetchCount( tooSmall ) );
    
    assertNotNull( actual );  
    assertTrue( actual instanceof IllegalArgumentException );
    assertTrue( actual.getMessage().contains( valueOf( tooSmall ) ) );
    assertEquals( format( ERROR_EXCEEDS_LOWER_BOUND, tooSmall ), 
                  actual.getMessage() );
  }
  
  @Test( expected = IllegalArgumentException.class )
  public void constructWithNullAsItemProvider() {
    new Timeline( null, mock( SessionStorage.class ) );
  }
  
  @Test( expected = IllegalArgumentException.class )
  public void constructWithNullAsSessionStorage() {
    new Timeline( mock( ItemProvider.class ), null );
  }
  
  @Test
  public void fetchItemWithExceptionOnStoreTop()
    throws IOException
  {
    IOException cause = new IOException();
    doThrow( cause ).when( sessionStorage ).storeTop( any( Item.class ) );
       
    Throwable actual = thrownBy( () -> timeline.fetchItems() );

    assertNotNull( actual );
    assertTrue( actual instanceof IllegalStateException );
    assertSame( cause, actual.getCause() );
    assertEquals( Timeline.ERROR_STORE_TOP, actual.getMessage() );
  }
  
  @Test
  public void fetchItemWithRuntimeExceptionOnStoreTop()
      throws IOException
  {
    RuntimeException cause = new RuntimeException();
    doThrow( cause ).when( sessionStorage ).storeTop( any( Item.class ) );
    
    Throwable actual = thrownBy( () -> timeline.fetchItems() );
    
    assertNotNull( actual );
    assertSame( cause, actual );
  }
  
  @Test
  public void fetchItemWithExceptionOnReadTop()
      throws IOException
  {
    IOException cause = new IOException();
    doThrow( cause ).when( sessionStorage ).readTop();
    
    Throwable actual = thrownBy( () -> timeline.fetchItems() );
    
    assertNotNull( actual );
    assertTrue( actual instanceof IllegalStateException );
    assertSame( cause, actual.getCause() );
    assertEquals( Timeline.ERROR_READ_TOP, actual.getMessage() );
  }
  
  @Test
  public void fetchItemWithRuntimeExceptionOnReadTop()
      throws IOException
  {
    RuntimeException cause = new RuntimeException();
    doThrow( cause ).when( sessionStorage ).readTop();
    
    Throwable actual = thrownBy( () -> timeline.fetchItems() );
    
    assertNotNull( actual );
    assertSame( cause, actual );
  }
  
  @Test
  public void fetchItems() {
    itemProvider.addItems( FIRST_ITEM, SECOND_ITEM, THIRD_ITEM, FOURTH_ITEM, FIFTH_ITEM, SIXTH_ITEM );
    timeline.setFetchCount( 2 );    
    timeline.fetchItems();

    timeline.fetchItems();
    List<Item> actual = timeline.getItems();

    assertArrayEquals( new Item[] { SIXTH_ITEM, FIFTH_ITEM, FOURTH_ITEM, THIRD_ITEM }, 
                       actual.toArray( new Item[ 2 ] ) );
  }
  
  @Test
  public void fetchFirstItemsWithTopItemToRecover() throws IOException {
    itemProvider.addItems( FIRST_ITEM, SECOND_ITEM, THIRD_ITEM, FOURTH_ITEM );
    when( sessionStorage.readTop() ).thenReturn( SECOND_ITEM );
    timeline.setFetchCount( 2 );
      
    timeline.fetchItems();
    List<Item> actual = timeline.getItems();

    assertSame( SECOND_ITEM, actual.get( 0 ) );
    assertArrayEquals( new Item[] { SECOND_ITEM, FIRST_ITEM }, 
                       actual.toArray( new Item[ 2 ] ) );
    verify( sessionStorage ).storeTop( SECOND_ITEM );
  }
  
  @Test
  public void fetchFirstItemsWithoutTopItemToRecover() throws IOException {
    itemProvider.addItems( FIRST_ITEM, SECOND_ITEM, THIRD_ITEM, FOURTH_ITEM );
    timeline.setFetchCount( 2 );
      
    timeline.fetchItems();
    List<Item> actual = timeline.getItems();
      
    assertArrayEquals( new Item[] { FOURTH_ITEM, THIRD_ITEM }, 
                       actual.toArray( new Item[ 2 ] ) );
    verify( sessionStorage ).storeTop( FOURTH_ITEM );
  }

  @Test
  public void fetchItemsIfNonAvailable() throws IOException {
    timeline.fetchItems();
    List<Item> actual = timeline.getItems();

    assertTrue( actual.isEmpty() );
    verify( sessionStorage ).storeTop( null );
  }
  
  @Test
  public void fetchItemsIfFetchCountExceedsAvailableItems() throws IOException {
    itemProvider.addItems( FIRST_ITEM, SECOND_ITEM );
    
    timeline.fetchItems();
    List<Item> actual = timeline.getItems();
    
    assertArrayEquals( new Item[] { SECOND_ITEM, FIRST_ITEM }, 
                       actual.toArray( new Item[ 2 ] ) );
    verify( sessionStorage ).storeTop( SECOND_ITEM );
  }
  
  @Test
  public void setFetchCount() {
    int newFetchCount = timeline.getFetchCount() + 1;
    
    timeline.setFetchCount( newFetchCount );
    
    assertEquals( newFetchCount, timeline.getFetchCount() );
  }
  
  @Test
  public void initialState() {
    assertTrue( timeline.getFetchCount() > 0 ); 
  }
}