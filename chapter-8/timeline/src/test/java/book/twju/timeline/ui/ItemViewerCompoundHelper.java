package book.twju.timeline.ui;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import book.twju.timeline.model.Item;

class ItemViewerCompoundHelper {

  @SuppressWarnings("unchecked")
  static ItemViewerCompound<Item, Object> stubItemViewerCompound( 
    ItemUiList<Item, Object> itemUiList, TopItemScroller<Item> scroller, TopItemUpdater<Item, Object> topItemUpdater )
  {
    ItemViewerCompound<Item, Object> itemViewerCompound = mock( ItemViewerCompound.class );
    when( itemViewerCompound.getItemUiList() ).thenReturn( itemUiList );
    when( itemViewerCompound.getScroller() ).thenReturn( scroller );
    when( itemViewerCompound.getTopItemUpdater() ).thenReturn( topItemUpdater );
    return itemViewerCompound;
  }

  @SuppressWarnings("unchecked")
  static TopItemUpdater<Item, Object> stubTopItemUpdater() {
    return mock( TopItemUpdater.class );
  }

  @SuppressWarnings("unchecked")
  static TopItemScroller<Item> stubScroller() {
    return mock( TopItemScroller.class );
  }

  @SuppressWarnings("unchecked")
  static ItemUiList<Item, Object> stubItemList() {
    return mock( ItemUiList.class );
  }
}