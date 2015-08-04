package book.twju.timeline.swt;

import static book.twju.timeline.util.Assertion.checkArgument;

import org.eclipse.swt.widgets.Composite;

import book.twju.timeline.model.Item;
import book.twju.timeline.model.Timeline;
import book.twju.timeline.ui.ItemUiFactory;
import book.twju.timeline.ui.ItemUiList;
import book.twju.timeline.ui.ItemUiMap;
import book.twju.timeline.ui.ItemViewerCompound;
import book.twju.timeline.ui.TopItemScroller;
import book.twju.timeline.ui.TopItemUpdater;

class SwtItemViewerCompound<T extends Item> implements ItemViewerCompound<T, Composite> {

  static final String TIMELINE_MUST_NOT_BE_NULL = "Argument 'timeline' must not be null.";
  static final String ITEM_UI_FACTORY_MUST_NOT_BE_NULL = "Argument 'itemUiFactory' must not be null.";

  private final SwtTopItemUpdater<T> topItemUpdater;
  private final SwtTopItemScroller<T> scroller;
  private final SwtItemUiList<T> itemUiList;

  SwtItemViewerCompound( Timeline<T> timeline, ItemUiFactory<T, Composite> itemUiFactory ) {
    checkArgument( timeline != null, TIMELINE_MUST_NOT_BE_NULL );
    checkArgument( itemUiFactory != null, ITEM_UI_FACTORY_MUST_NOT_BE_NULL );
    
    ItemUiMap<T, Composite> itemUiMap = new ItemUiMap<>( timeline, itemUiFactory );
    itemUiList = new SwtItemUiList<T>( itemUiMap );
    scroller = new SwtTopItemScroller<>( timeline, itemUiMap, itemUiList );
    topItemUpdater = new SwtTopItemUpdater<>( timeline, itemUiMap, itemUiList );
  }
  
  @Override
  public TopItemUpdater<T, Composite> getTopItemUpdater() {
    return topItemUpdater;
  }
  
  @Override
  public TopItemScroller<T> getScroller() {
    return scroller;
  }
  
  @Override
  public ItemUiList<T, Composite> getItemUiList() {
    return itemUiList;
  }
}