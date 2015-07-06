package book.twju.timeline.swing;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import book.twju.timeline.model.Item;
import book.twju.timeline.swing.ItemUiList;
import book.twju.timeline.swing.ItemViewerCompound;
import book.twju.timeline.swing.TopItemScroller;
import book.twju.timeline.swing.TopItemUpdater;

class ItemViewerCompoundHelper {

  @SuppressWarnings("unchecked")
  static ItemViewerCompound<Item> stubItemViewer( 
    ItemUiList<Item> itemUiList, TopItemScroller<Item> scroller, TopItemUpdater<Item> topItemUpdater )
  {
    ItemViewerCompound<Item> itemViewerCompound = mock( ItemViewerCompound.class );
    when( itemViewerCompound.getItemUiList() ).thenReturn( itemUiList );
    when( itemViewerCompound.getScroller() ).thenReturn( scroller );
    when( itemViewerCompound.getTopItemUpdater() ).thenReturn( topItemUpdater );
    return itemViewerCompound;
  }

  @SuppressWarnings("unchecked")
  static TopItemUpdater<Item> stubTopItemUpdater() {
    return mock( TopItemUpdater.class );
  }

  @SuppressWarnings("unchecked")
  static TopItemScroller<Item> stubScroller() {
    return mock( TopItemScroller.class );
  }

  @SuppressWarnings("unchecked")
  static ItemUiList<Item> stubItemList() {
    return mock( ItemUiList.class );
  }
}