package book.twju.timeline.swing;

import static book.twju.timeline.swing.ItemUiList.OPERATION_MUST_NOT_BE_NULL;
import static book.twju.timeline.swing.ThreadHelper.directBackgroundProcessor;
import static book.twju.timeline.test.util.ThrowableCaptor.thrownBy;
import static book.twju.timeline.ui.FetchOperation.MORE;
import static book.twju.timeline.ui.FetchOperation.NEW;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Container;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import book.twju.timeline.model.Item;
import book.twju.timeline.ui.FetchOperation;
import book.twju.timeline.ui.ItemUiMap;

public class ItemUiListTest {
  
  private BackgroundProcessor backgroundProcessor;
  private ItemUiMap<Item, Container> itemUiMap;
  private ItemUiList<Item> itemUiList;

  @Before
  public void setUp() {
    itemUiMap = stubItemUiMap();
    backgroundProcessor = directBackgroundProcessor();
    itemUiList = new ItemUiList<>( itemUiMap, backgroundProcessor );
  }
  
  @Test
  public void update() {
    itemUiList.createUi();
    
    itemUiList.update();
    
    verify( itemUiMap ).update( itemUiList.content );
  }
  
  @Test
  public void isTimelineEmpty() {
    when( itemUiMap.isTimelineEmpty() ).thenReturn( true );
    
    boolean actual = itemUiList.isTimelineEmpty();
    
    assertThat( actual ).isTrue();
  }
  
  @Test
  public void fetchInBackground() {
    itemUiList.createUi();
    
    itemUiList.fetchInBackground( NEW );
    
    InOrder order = inOrder( itemUiMap, backgroundProcessor );
    order.verify( backgroundProcessor ).process( any( Runnable.class ) );
    order.verify( itemUiMap ).fetch( NEW );
    order.verify( backgroundProcessor ).dispatchToUiThread( any( Runnable.class ) );
    order.verify( itemUiMap ).update( itemUiList.content );
    order.verifyNoMoreInteractions();
  }
  
  @Test
  public void fetchInBackgroundWithNullAsOperation() {
    Throwable actual = thrownBy( () -> itemUiList.fetchInBackground( null ) );
    
    assertThat( actual )
      .hasMessage( OPERATION_MUST_NOT_BE_NULL );
  }
  
  @Test
  public void fetch() {
    itemUiList.createUi();
    FetchOperation expected = NEW;
    
    itemUiList.fetch( expected );
    
    InOrder order = inOrder( itemUiMap, backgroundProcessor );
    order.verify( backgroundProcessor ).dispatchToUiThread( any( Runnable.class ) );
    order.verify( itemUiMap ).update( itemUiList.content );
    order.verifyNoMoreInteractions();
  }
  
  @Test
  public void fetchWithNullAsOperation() {
    Throwable actual = thrownBy( () -> itemUiList.fetch( null ) );
    
    assertThat( actual )
      .hasMessage( OPERATION_MUST_NOT_BE_NULL );
  }
  
  @Test
  public void createUi() {
    itemUiList.createUi();
    
    assertThat( itemUiList.getComponent() ).isNotNull();
  }

  @Test
  public void fetchMoreOnClick() {
    itemUiList.createUi();
    
    itemUiList.fetchMore.doClick();
    
    InOrder order = inOrder( itemUiMap, backgroundProcessor );
    order.verify( backgroundProcessor ).process( any( Runnable.class ) );
    order.verify( itemUiMap ).fetch( MORE );
    order.verify( backgroundProcessor ).dispatchToUiThread( any( Runnable.class ) );
    order.verify( itemUiMap ).update( itemUiList.content );
    order.verifyNoMoreInteractions();
  }
  
  @SuppressWarnings("unchecked")
  private ItemUiMap<Item, Container> stubItemUiMap() {
    return mock( ItemUiMap.class );
  }
}