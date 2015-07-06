package book.twju.timeline.swing;

import static book.twju.timeline.util.Conditions.checkArgument;

import java.awt.Container;

import book.twju.timeline.model.Item;
import book.twju.timeline.model.Timeline;
import book.twju.timeline.ui.ItemUiFactory;
import book.twju.timeline.ui.ItemUiMap;

class ItemViewerCompound<T extends Item> {

  static final String TIMELINE_MUST_NOT_BE_NULL = "Argument 'timeline' must not be null.";
  static final String ITEM_UI_FACTORY_MUST_NOT_BE_NULL = "Argument 'itemUiFactory' must not be null.";

  private final TopItemUpdater<T> topItemUpdater;
  private final TopItemScroller<T> scroller;
  private final ItemUiList<T> itemUiList;

  ItemViewerCompound( Timeline<T> timeline, ItemUiFactory<T, Container> itemUiFactory ) {
    checkArgument( timeline != null, TIMELINE_MUST_NOT_BE_NULL );
    checkArgument( itemUiFactory != null, ITEM_UI_FACTORY_MUST_NOT_BE_NULL );
    
    ItemUiMap<T, Container> itemUiMap = new ItemUiMap<>( timeline, itemUiFactory );
    itemUiList = new ItemUiList<T>( itemUiMap );
    scroller = new TopItemScroller<>( timeline, itemUiMap, itemUiList );
    topItemUpdater = new TopItemUpdater<>( timeline, itemUiMap, itemUiList );
  }
  
  TopItemUpdater<T> getTopItemUpdater() {
    return topItemUpdater;
  }
  
  TopItemScroller<T> getScroller() {
    return scroller;
  }
  
  ItemUiList<T> getItemUiList() {
    return itemUiList;
  }
}