package book.twju.timeline.swing;

import static book.twju.timeline.swing.ThreadHelper.directUiThreadDispatcher;
import static book.twju.timeline.swing.TopItemTestHelper.showInFrame;
import static book.twju.timeline.swing.TopItemTestHelper.stubSwingItemUi;
import static book.twju.timeline.swing.TopItemTestHelper.stubTimeline;
import static book.twju.timeline.swing.TopItemTestHelper.stubUiItemList;
import static book.twju.timeline.swing.TopItemTestHelper.stubUiItemMap;
import static java.util.Arrays.asList;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Container;
import java.util.Optional;

import javax.swing.JPanel;

import org.junit.Before;
import org.junit.Test;

import book.twju.timeline.model.Item;
import book.twju.timeline.model.Timeline;
import book.twju.timeline.ui.ItemUiMap;

public class TopItemUpdaterITest {
  
  private ItemUiMap<Item, Container> itemUiMap;
  private TopItemUpdater<Item> updater;
  private ItemUiList<Item> itemUiList;
  private Timeline<Item> timeline;

  @Before
  public void setUp() {
    timeline = stubTimeline();
    itemUiMap = stubUiItemMap();
    itemUiList = stubUiItemList();
    updater = spy( new TopItemUpdater<>( timeline, itemUiMap, itemUiList, directUiThreadDispatcher() ) );
  }

  @Test
  public void updateIfBelowTop() throws Exception  {
    Item item = equipTimelineWithItems( new Item( "id", 20L ) {} );
    JPanel component = showInFrame( new JPanel() );
    SwingItemUi<Item> itemUi = stubSwingItemUi( component );
    component.setLocation( 0, 1 );
    map( item, itemUi );
    
    updater.update();
    
    verify( timeline ).setTopItem( item );
  }
  
  @Test
  public void updateIfBelowTopWithDifferentTopItem() throws Exception  {
    Item item = equipTimelineWithItems( new Item( "id", 20L ) {} );
    equipTimelineWithTopItem( new Item( "other", 30L ) {} );
    JPanel component = showInFrame( new JPanel() );
    SwingItemUi<Item> itemUi = stubSwingItemUi( component );
    component.setLocation( 0, 1 );
    map( item, itemUi );
    
    updater.update();
    
    verify( timeline ).setTopItem( item );
  }
  
  @Test
  public void updateIfBelowTopWithEqualTopItem() throws Exception  {
    Item item = equipTimelineWithItems( new Item( "id", 20L ) {} );
    equipTimelineWithTopItem( item );
    JPanel component = showInFrame( new JPanel() );
    SwingItemUi<Item> itemUi = stubSwingItemUi( component );
    component.setLocation( 0, 1 );
    map( item, itemUi );
    
    updater.update();
    
    verify( timeline, never() ).setTopItem( item );
  }
  
  @Test
  public void updateIfAboveTop() throws Exception  {
    Item item = equipTimelineWithItems( new Item( "id", 20L ) {} );
    JPanel component = showInFrame( new JPanel() );
    SwingItemUi<Item> itemUi = stubSwingItemUi( component );
    component.setLocation( 0, -1 );
    map( item, itemUi );
    
    updater.update();
    
    verify( timeline, never() ).setTopItem( item );
  }
  
  @Test
  public void updateWithoutItems() throws Exception  {
    equipTimelineWithItems( new Item( "id", 20L ) {} );

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
    Item item = equipTimelineWithItems( new Item( "id", 20L ) {} );
    SwingItemUi<Item> itemUi = stubSwingItemUi( new JPanel() );
    new JPanel().setLocation( 0, 1 );
    map( item, itemUi );
    
    updater.update();
    
    verify( timeline, never() ).setTopItem( item );
  }
  
  @Test
  public void register() {
    updater.register();
    updater.getVerticalScrollBarModel().setValue( 10 );
    
    verify( updater ).update();
  }
  
  private Item equipTimelineWithItems( Item item ) {
    when( timeline.getItems() ).thenReturn( asList( item ) );
    return item;
  }

  private Item equipTimelineWithTopItem( Item item ) {
    when( timeline.getTopItem() ).thenReturn( Optional.of( item ) );
    return item;
  }

  private void map( Item item, SwingItemUi<Item> itemUi ) {
    when( itemUiMap.findByItemId( item.getId() ) ).thenReturn( itemUi );
  }
}