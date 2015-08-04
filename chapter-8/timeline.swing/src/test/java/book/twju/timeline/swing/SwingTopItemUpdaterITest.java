package book.twju.timeline.swing;

import static book.twju.timeline.swing.TopItemTestHelper.equipWithItems;
import static book.twju.timeline.swing.TopItemTestHelper.equipWithTopItem;
import static book.twju.timeline.swing.TopItemTestHelper.map;
import static book.twju.timeline.swing.TopItemTestHelper.newChildContainer;
import static book.twju.timeline.swing.TopItemTestHelper.stubItemUi;
import static book.twju.timeline.swing.TopItemTestHelper.stubTimeline;
import static book.twju.timeline.swing.TopItemTestHelper.stubUiItemList;
import static book.twju.timeline.swing.TopItemTestHelper.stubUiItemMap;
import static book.twju.timeline.util.test.util.BackgroundThreadHelper.directUiThreadDispatcher;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import book.twju.timeline.model.Item;
import book.twju.timeline.model.Timeline;
import book.twju.timeline.ui.ItemUiMap;

public class SwingTopItemUpdaterITest {
  
  @Rule
  public final FrameRule frameRule = new FrameRule();
  
  private ItemUiMap<Item, Container> itemUiMap;
  private SwingTopItemUpdater<Item> updater;
  private SwingItemUiList<Item> itemUiList;
  private Timeline<Item> timeline;

  @Before
  public void setUp() {
    timeline = stubTimeline();
    itemUiMap = stubUiItemMap();
    itemUiList = stubUiItemList();
    updater = spy( new SwingTopItemUpdater<>( timeline, itemUiMap, itemUiList, directUiThreadDispatcher() ) );
  }

  @Test
  public void updateIfBelowTop() throws Exception  {
    Item item = equipItemListWithItem();
    getItemUiComponent( item ).setLocation( 0, 1 );
    
    updater.update();
    
    verify( timeline ).setTopItem( item );
  }
  
  @Test
  public void updateIfBelowTopWithDifferentTopItem() throws Exception  {
    Item item = equipItemListWithItem();    
    equipWithTopItem( timeline, new Item( "other", 30L ) {} );
    getItemUiComponent( item ).setLocation( 0, 1 );
    
    updater.update();
    
    verify( timeline ).setTopItem( item );
  }
  
  @Test
  public void updateIfBelowTopWithEqualTopItem() throws Exception  {
    Item item = equipItemListWithItem();
    equipWithTopItem( timeline, item );
    getItemUiComponent( item ).setLocation( 0, 1 );
    
    updater.update();
    
    verify( timeline, never() ).setTopItem( item );
  }
  
  @Test
  public void updateIfAboveTop() throws Exception  {
    Item item = equipItemListWithItem();
    getItemUiComponent( item ).setLocation( 0, -1 );
    
    updater.update();
    
    verify( timeline, never() ).setTopItem( item );
  }
  
  @Test
  public void updateWithoutItems() throws Exception  {
    equipWithItems( timeline, new Item( "id", 20L ) {} );

    updater.update();
    
    verify( timeline, never() ).setTopItem( any( Item.class ) );
  }
  
  @Test
  public void updateWithoutItemUi() throws Exception  {
    updater.update();
    
    verify( timeline, never() ).setTopItem( any( Item.class ) );
  }
  
  @Test
  public void updateIfItemUiComponentIsNotShowing() throws Exception  {
    Item item = equipWithItems( timeline, new Item( "id", 20L ) {} );
    SwingItemUi<Item> itemUi = stubItemUi( new JPanel() );
    new JPanel().setLocation( 0, 1 );
    map( itemUiMap, item, itemUi );
    
    updater.update();
    
    verify( timeline, never() ).setTopItem( item );
  }
  
  @Test
  public void register() {
    updater.register();
    
    triggerScrollbarSelectionChange( 10 );
    
    verify( updater ).update();
  }

  private Item equipItemListWithItem() throws Exception {
    Item result = equipWithItems( timeline, new Item( "id", 20L ) {} );
    Container container = frameRule.showInFrame( itemUiList.getUiRoot() );
    SwingItemUi<Item> itemUi = stubItemUi( newChildContainer( container ) );
    map( itemUiMap, result, itemUi );
    return result;
  }

  private Component getItemUiComponent( Item item ) {
    return ( ( SwingItemUi<Item> )itemUiMap.findByItemId( item.getId() ) ).getComponent();
  }

  private void triggerScrollbarSelectionChange( int newValue ) {
    ( ( JScrollPane )itemUiList.getUiRoot() ).getVerticalScrollBar().getModel().setValue( newValue );
  }
}