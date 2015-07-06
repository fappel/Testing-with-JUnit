package book.twju.timeline.swing;

import static book.twju.timeline.swing.ThreadHelper.directUiThreadDispatcher;
import static book.twju.timeline.swing.TopItemScroller.TOP_POSITION;
import static book.twju.timeline.swing.TopItemTestHelper.showInFrame;
import static book.twju.timeline.swing.TopItemTestHelper.stubSwingItemUi;
import static book.twju.timeline.swing.TopItemTestHelper.stubTimeline;
import static book.twju.timeline.swing.TopItemTestHelper.stubUiItemList;
import static book.twju.timeline.swing.TopItemTestHelper.stubUiItemMap;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Container;
import java.util.Optional;

import javax.swing.JPanel;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import book.twju.timeline.model.Item;
import book.twju.timeline.model.Timeline;
import book.twju.timeline.ui.ItemUiMap;

public class TopItemScrollerITest {

  private ItemUiMap<Item, Container> itemUiMap;
  private TopItemScroller<Item> scroller;
  private ItemUiList<Item> itemUiList;
  private Timeline<Item> timeline;

  @Before
  public void setUp() {
    timeline = stubTimeline();
    itemUiMap = stubUiItemMap();
    itemUiList = stubUiItemList();
    scroller = spy( new TopItemScroller<>( timeline, itemUiMap, itemUiList, directUiThreadDispatcher() ) );
  }

  @Test
  public void scrollIntoViewIfBelowTop() throws Exception  {
    Item item = equipTimelineWithTopItem( new Item( "id", 20L ) {} );
    JPanel component = showInFrame( new JPanel() );
    SwingItemUi<Item> itemUi = stubSwingItemUi( component );
    component.setLocation( 0, TOP_POSITION - 1 );
    map( item, itemUi );
    
    scroller.scrollIntoView();
    
    verify( scroller ).setScrollbarSelection( anyInt() );
  }
  
  @Test
  @Ignore
  public void scrollIntoViewIfAboveTop() throws Exception {
    Item item = equipTimelineWithTopItem( new Item( "id", 20L ) {} );
    JPanel component = showInFrame( new JPanel() );
    SwingItemUi<Item> itemUi = stubSwingItemUi( component );
    component.setLocation( 0, TOP_POSITION + 1 );
    map( item, itemUi );
    
    scroller.scrollIntoView();
    
    verify( scroller, never() ).setScrollbarSelection( anyInt() );
  }
  
  @Test
  public void scrollIntoViewWithoutTopItem() {
    scroller.scrollIntoView();
    
    verify( scroller, never() ).setScrollbarSelection( anyInt() );
  }
  
  @Test
  public void scrollIntoViewWithoutItemUi() {
    equipTimelineWithTopItem( new Item( "id", 20L ) {} );

    scroller.scrollIntoView();
    
    verify( scroller, never() ).setScrollbarSelection( anyInt() );
  }
  
  @Test
  public void scrollIntoViewIfItemUiComponentIsNotShowing() {
    Item item = equipTimelineWithTopItem( new Item( "id", 20L ) {} );
    SwingItemUi<Item> itemUi = stubSwingItemUi( new JPanel() );
    map( item, itemUi );
    
    scroller.scrollIntoView();
    
    verify( scroller, never() ).setScrollbarSelection( anyInt() );
  }

  private Item equipTimelineWithTopItem( Item item ) {
    when( timeline.getTopItem() ).thenReturn( Optional.of( item ) );
    return item;
  }
  
  private void map( Item item, SwingItemUi<Item> itemUi ) {
    when( itemUiMap.findByItemId( item.getId() ) ).thenReturn( itemUi );
  }
}