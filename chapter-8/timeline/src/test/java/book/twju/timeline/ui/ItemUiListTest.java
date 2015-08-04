package book.twju.timeline.ui;

import static book.twju.timeline.test.util.ThrowableCaptor.thrownBy;
import static book.twju.timeline.ui.FetchOperation.NEW;
import static book.twju.timeline.ui.ItemUiList.OPERATION_MUST_NOT_BE_NULL;
import static book.twju.timeline.util.test.util.BackgroundThreadHelper.directBackgroundProcessor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import book.twju.timeline.model.Item;
import book.twju.timeline.util.BackgroundProcessor;

public class ItemUiListTest {
  
  private BackgroundProcessor backgroundProcessor;
  private ItemUiMap<Item, Object> itemUiMap;
  private ItemUiList<Item, Object> itemUiList;
  
  @Before
  @SuppressWarnings("unchecked")
  public void setUp() {
    itemUiMap = mock( ItemUiMap.class );
    backgroundProcessor = directBackgroundProcessor();
    itemUiList = spy( new TestItemUiList( itemUiMap, backgroundProcessor ) );
  }

  @Test
  public void update() {
    itemUiList.update();
    
    InOrder order = inOrder( itemUiMap, itemUiList );
    order.verify( itemUiList ).beforeContentUpdate();
    order.verify( itemUiMap ).update( itemUiList.getContent() );
    order.verify( itemUiList ).afterContentUpdate();
  }
  
  @Test
  public void fetchInBackground() {
    itemUiList.fetchInBackground( NEW );
    
    InOrder order = inOrder( itemUiMap, itemUiList );
    order.verify( itemUiMap ).fetch( NEW );
    order.verify( itemUiList ).update();
  }
  
  @Test
  public void fetchInBackgroundIfThreadDoesNotRun() {
    doNothing().when( backgroundProcessor ).process( any( Runnable.class ) );
    
    itemUiList.fetchInBackground( NEW );
    
    verify( itemUiMap, never() ).fetch( NEW );
    verify( itemUiList, never() ).update();
  }
  
  @Test
  public void fetchInBackgroundIfUiDispatcherDoesNotRun() {
    doNothing().when( backgroundProcessor ).dispatchToUiThread( any( Runnable.class ) );
    
    itemUiList.fetchInBackground( NEW );
    
    verify( itemUiMap ).fetch( NEW );
    verify( itemUiList, never() ).update();
  }

  @Test
  public void fetchInBackgroundWithNullAsOperation() {
    Throwable actual = thrownBy( () -> itemUiList.fetchInBackground( null ) );
    
    assertThat( actual )
      .hasMessage( OPERATION_MUST_NOT_BE_NULL );
  }
  
  @Test
  public void fetch() {
    itemUiList.fetch( NEW );
    
    InOrder order = inOrder( itemUiMap, itemUiList );
    order.verify( itemUiMap ).fetch( NEW );
    order.verify( itemUiList ).update();
  }
  
  @Test
  public void fetchIfUiDispatcherDoesNotRun() {
    doNothing().when( backgroundProcessor ).dispatchToUiThread( any( Runnable.class ) );
    
    itemUiList.fetchInBackground( NEW );
    
    verify( itemUiMap ).fetch( NEW );
    verify( itemUiList, never() ).update();
  }

  @Test
  public void fetchWithNullAsOperation() {
    Throwable actual = thrownBy( () -> itemUiList.fetch( null ) );
    
    assertThat( actual )
      .hasMessage( OPERATION_MUST_NOT_BE_NULL );
  }

  @Test
  public void isTimelineEmpty() {
    when( itemUiMap.isTimelineEmpty() ).thenReturn( true );
    
    boolean actual = itemUiList.isTimelineEmpty();
    
    assertThat( actual ).isTrue();
  }
}