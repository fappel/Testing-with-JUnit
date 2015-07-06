package book.twju.timeline.swing;

import static book.twju.timeline.ui.FetchOperation.MORE;
import static book.twju.timeline.ui.FetchOperation.NEW;

import java.awt.Component;
import java.awt.Container;

import book.twju.timeline.model.Item;
import book.twju.timeline.model.Timeline;
import book.twju.timeline.ui.ItemUiFactory;

class ItemViewer<T extends Item> {

  private final ItemUiList<T> itemUiList;
  private final TopItemScroller<T> scroller;
  private final TopItemUpdater<T> topItemUpdater;

  ItemViewer( Timeline<T> timeline, ItemUiFactory<T, Container> itemUiFactory ) {
    this( new ItemViewerCompound<>( timeline, itemUiFactory ) );
  }

  ItemViewer( ItemViewerCompound<T> itemViewerCompound ) {
    itemUiList = itemViewerCompound.getItemUiList();
    scroller = itemViewerCompound.getScroller();
    topItemUpdater = itemViewerCompound.getTopItemUpdater();
  }

  void createUi() {
    itemUiList.createUi();
  }
  
  Component getComponent() {
    return itemUiList.getComponent();
  }

  void initialize() {
    if( itemUiList.isTimelineEmpty() ) {
      itemUiList.fetch( MORE );
    }
    itemUiList.update();
    scroller.scrollIntoView();
    topItemUpdater.register();
  }
  
  void fetchNew() {
    itemUiList.fetch( NEW  );
  }

  void update() {
    itemUiList.update();
  }
}