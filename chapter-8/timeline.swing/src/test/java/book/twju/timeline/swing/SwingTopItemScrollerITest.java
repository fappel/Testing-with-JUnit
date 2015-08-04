package book.twju.timeline.swing;

import static book.twju.timeline.swing.SwingTopItemScroller.TOP_POSITION;
import static book.twju.timeline.swing.TopItemTestHelper.equipWithTopItem;
import static book.twju.timeline.swing.TopItemTestHelper.map;
import static book.twju.timeline.swing.TopItemTestHelper.newChildContainer;
import static book.twju.timeline.swing.TopItemTestHelper.stubItemUi;
import static book.twju.timeline.swing.TopItemTestHelper.stubTimeline;
import static book.twju.timeline.swing.TopItemTestHelper.stubUiItemList;
import static book.twju.timeline.swing.TopItemTestHelper.stubUiItemMap;
import static book.twju.timeline.util.test.util.BackgroundThreadHelper.directUiThreadDispatcher;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.awt.Container;

import javax.swing.JPanel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import book.twju.timeline.model.Item;
import book.twju.timeline.model.Timeline;
import book.twju.timeline.ui.ItemUiMap;

public class SwingTopItemScrollerITest {
  
  @Rule
  public final FrameRule frameRule = new FrameRule();
  
  private ItemUiMap<Item, Container> itemUiMap;
  private SwingTopItemScroller<Item> scroller;
  private SwingItemUiList<Item> itemUiList;
  private Timeline<Item> timeline;

  @Before
  public void setUp() {
    timeline = stubTimeline();
    itemUiMap = stubUiItemMap();
    itemUiList = stubUiItemList();
    scroller = spy( new SwingTopItemScroller<>( timeline, itemUiMap, itemUiList, directUiThreadDispatcher() ) );
  }

  @Test
  public void scrollIntoViewIfBelowTop() throws Exception  {
    SwingItemUi<Item> itemUi = equipItemListWithItem();
    itemUi.getComponent().setLocation( 0, TOP_POSITION - 1 );
    
    scroller.scrollIntoView();
    
    verify( scroller ).setScrollbarSelection( anyInt() );
  }
  
  @Test
  public void scrollIntoViewIfAboveTop() throws Exception {
    SwingItemUi<Item> itemUi = equipItemListWithItem();
    itemUi.getComponent().setLocation( 0, TOP_POSITION + 1 );
    
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
    equipWithTopItem( timeline, new Item( "id", 20L ) {} );

    scroller.scrollIntoView();
    
    verify( scroller, never() ).setScrollbarSelection( anyInt() );
  }
  
  @Test
  public void scrollIntoViewIfItemUiComponentIsNotShowing() {
    Item item = equipWithTopItem( timeline, new Item( "id", 20L ) {} );
    SwingItemUi<Item> itemUi = stubItemUi( new JPanel() );
    map( itemUiMap, item, itemUi );
    
    scroller.scrollIntoView();
    
    verify( scroller, never() ).setScrollbarSelection( anyInt() );
  }

  private SwingItemUi<Item> equipItemListWithItem() throws Exception {
    Item item = equipWithTopItem( timeline, new Item( "id", 20L ) {} );
    Container container = frameRule.showInFrame( itemUiList.getUiRoot() );
    SwingItemUi<Item> result = stubItemUi( newChildContainer( container ) );
    map( itemUiMap, item, result );
    return result;
  }
}