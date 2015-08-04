package book.twju.timeline.swt;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import book.twju.timeline.model.Item;
import book.twju.timeline.model.Timeline;
import book.twju.timeline.ui.ItemUiMap;

class TopItemTestHelper {

  @SuppressWarnings("unchecked")
  static SwtItemUiList<Item> stubUiItemList( Composite parent ) {
    SwtItemUiList<Item> result = mock( SwtItemUiList.class );
    ScrolledComposite scrolledComposite = new ScrolledComposite( parent, SWT.V_SCROLL );
    when( result.getUiRoot() ).thenReturn( scrolledComposite );
    return result;
  }

  @SuppressWarnings("unchecked")
  static ItemUiMap<Item, Composite> stubUiItemMap() {
    return mock( ItemUiMap.class );
  }

  @SuppressWarnings("unchecked")
  static Timeline<Item> stubTimeline() {
    Timeline<Item> result = mock( Timeline.class );
    when( result.getTopItem() ).thenReturn( Optional.empty() );
    return result;
  }

  @SuppressWarnings("unchecked")
  static SwtItemUi<Item> stubItemUi( Control control ) {
    SwtItemUi<Item> result = mock( SwtItemUi.class );
    when( result.getControl() ).thenReturn( control );
    return result;
  }
  
  static void map( ItemUiMap<Item, Composite> itemUiMap, Item item, SwtItemUi<Item> itemUi ) {
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
  
  static Composite newChildComposite( Composite parent ) {
    return new Composite( parent, SWT.NONE );
  }
}