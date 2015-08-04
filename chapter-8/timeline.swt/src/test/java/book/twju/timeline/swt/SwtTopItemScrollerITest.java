package book.twju.timeline.swt;

import static book.twju.timeline.swt.SwtTopItemScroller.TOP_POSITION;
import static book.twju.timeline.swt.TopItemTestHelper.equipWithTopItem;
import static book.twju.timeline.swt.TopItemTestHelper.map;
import static book.twju.timeline.swt.TopItemTestHelper.newChildComposite;
import static book.twju.timeline.swt.TopItemTestHelper.stubItemUi;
import static book.twju.timeline.swt.TopItemTestHelper.stubTimeline;
import static book.twju.timeline.swt.TopItemTestHelper.stubUiItemList;
import static book.twju.timeline.swt.TopItemTestHelper.stubUiItemMap;
import static book.twju.timeline.util.test.util.BackgroundThreadHelper.directUiThreadDispatcher;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import book.twju.timeline.model.Item;
import book.twju.timeline.model.Timeline;
import book.twju.timeline.swt.test.util.DisplayHelper;
import book.twju.timeline.ui.ItemUiMap;

public class SwtTopItemScrollerITest {

  @Rule
  public final DisplayHelper displayHelper = new DisplayHelper();
  
  private ItemUiMap<Item, Composite> itemUiMap;
  private SwtTopItemScroller<Item> scroller;
  private SwtItemUiList<Item> itemUiList;
  private Timeline<Item> timeline;
  private Shell shell;

  @Before
  public void setUp() {
    shell = displayHelper.createShell();
    timeline = stubTimeline();
    itemUiMap = stubUiItemMap();
    itemUiList = stubUiItemList( shell );
    scroller = spy( new SwtTopItemScroller<>( timeline, itemUiMap, itemUiList, directUiThreadDispatcher() ) );
  }

  @Test
  public void scrollIntoViewIfBelowTop() throws Exception  {
    SwtItemUi<Item> itemUi = equipItemListWithItem();
    itemUi.getControl().setLocation( 0, TOP_POSITION + 1 );
    
    scroller.scrollIntoView();
    
    verify( scroller ).setScrollbarSelection( anyInt() );
  }
  
  @Test
  public void scrollIntoViewIfAboveTop() throws Exception {
    SwtItemUi<Item> itemUi = equipItemListWithItem();
    itemUi.getControl().setLocation( 0, TOP_POSITION - 1 );
    
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
    SwtItemUi<Item> itemUi = stubItemUi( displayHelper.createShell() );
    map( itemUiMap, item, itemUi );
    
    scroller.scrollIntoView();
    
    verify( scroller, never() ).setScrollbarSelection( anyInt() );
  }

  private SwtItemUi<Item> equipItemListWithItem() throws Exception {
    Item item = equipWithTopItem( timeline, new Item( "id", 20L ) {} );
    ShellHelper.showShell( shell );
    SwtItemUi<Item> result = stubItemUi( newChildComposite( itemUiList.getUiRoot() ) );
    map( itemUiMap, item, result );
    return result;
  }
}