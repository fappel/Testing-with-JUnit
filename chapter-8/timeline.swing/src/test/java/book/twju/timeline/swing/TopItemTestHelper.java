package book.twju.timeline.swing;

import static java.util.Arrays.asList;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Component;
import java.awt.Container;
import java.util.Optional;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import book.twju.timeline.model.Item;
import book.twju.timeline.model.Timeline;
import book.twju.timeline.ui.ItemUiMap;

class TopItemTestHelper {

  @SuppressWarnings("unchecked")
  static SwingItemUiList<Item> stubUiItemList() {
    SwingItemUiList<Item> result = mock( SwingItemUiList.class );
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setVerticalScrollBarPolicy( VERTICAL_SCROLLBAR_ALWAYS );
    when( result.getUiRoot() ).thenReturn( scrollPane );
    return result;
  }

  @SuppressWarnings("unchecked")
  static ItemUiMap<Item, Container> stubUiItemMap() {
    return mock( ItemUiMap.class );
  }

  @SuppressWarnings("unchecked")
  static Timeline<Item> stubTimeline() {
    Timeline<Item> result = mock( Timeline.class );
    when( result.getTopItem() ).thenReturn( Optional.empty() );
    return result;
  }

  @SuppressWarnings("unchecked")
  static SwingItemUi<Item> stubItemUi( Component component ) {
    SwingItemUi<Item> result = mock( SwingItemUi.class );
    when( result.getComponent() ).thenReturn( component );
    return result;
  }
  
  static void map( ItemUiMap<Item, Container> itemUiMap, Item item, SwingItemUi<Item> itemUi ) {
    when( itemUiMap.findByItemId( item.getId() ) ).thenReturn( itemUi );
  }

  static Item equipWithTopItem( Timeline<Item> timeline, Item item ) {
    when( timeline.getTopItem() ).thenReturn( Optional.of( item ) );
    return item;
  }

  static Item equipWithItems( Timeline<Item> timeline , Item item ) {
    when( timeline.getItems() ).thenReturn( asList( item ) );
    return item;
  }
  
  static Container newChildContainer( Container parent ) {
    JPanel result = new JPanel();
    parent.add( result );
    return result;
  }
}