package book.twju.timeline.swing;

import static book.twju.timeline.util.Assertion.checkArgument;

import java.awt.Container;

import book.twju.timeline.model.Item;
import book.twju.timeline.model.Timeline;
import book.twju.timeline.ui.ItemUiFactory;
import book.twju.timeline.ui.ItemUiList;
import book.twju.timeline.ui.ItemUiMap;
import book.twju.timeline.ui.ItemViewerCompound;
import book.twju.timeline.ui.TopItemScroller;
import book.twju.timeline.ui.TopItemUpdater;

class SwingItemViewerCompound<T extends Item> implements ItemViewerCompound<T, Container> {

  static final String TIMELINE_MUST_NOT_BE_NULL = "Argument 'timeline' must not be null.";
  static final String ITEM_UI_FACTORY_MUST_NOT_BE_NULL = "Argument 'itemUiFactory' must not be null.";

  private final SwingTopItemUpdater<T> topItemUpdater;
  private final SwingTopItemScroller<T> scroller;
  private final SwingItemUiList<T> itemUiList;

  SwingItemViewerCompound( Timeline<T> timeline, ItemUiFactory<T, Container> itemUiFactory ) {
    checkArgument( timeline != null, TIMELINE_MUST_NOT_BE_NULL );
    checkArgument( itemUiFactory != null, ITEM_UI_FACTORY_MUST_NOT_BE_NULL );
    
    ItemUiMap<T, Container> itemUiMap = new ItemUiMap<>( timeline, itemUiFactory );
    itemUiList = new SwingItemUiList<T>( itemUiMap );
    scroller = new SwingTopItemScroller<>( timeline, itemUiMap, itemUiList );
    topItemUpdater = new SwingTopItemUpdater<>( timeline, itemUiMap, itemUiList );
  }
  
  @Override
  public TopItemUpdater<T, Container> getTopItemUpdater() {
    return topItemUpdater;
  }
  
  @Override
  public TopItemScroller<T> getScroller() {
    return scroller;
  }
  
  @Override
  public ItemUiList<T, Container> getItemUiList() {
    return itemUiList;
  }
}