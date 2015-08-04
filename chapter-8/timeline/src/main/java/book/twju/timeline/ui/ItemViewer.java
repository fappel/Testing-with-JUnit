package book.twju.timeline.ui;

import static book.twju.timeline.ui.FetchOperation.MORE;
import static book.twju.timeline.ui.FetchOperation.NEW;
import book.twju.timeline.model.Item;

public class ItemViewer<T extends Item, U> {

  private final ItemUiList<T, U> itemUiList;
  private final TopItemScroller<T> scroller;
  private final TopItemUpdater<T, U> topItemUpdater;

  public ItemViewer( ItemViewerCompound<T, U> itemViewerCompound ) {
    itemUiList = itemViewerCompound.getItemUiList();
    scroller = itemViewerCompound.getScroller();
    topItemUpdater = itemViewerCompound.getTopItemUpdater();
  }

  public void createUi( U parent ) {
    itemUiList.createUi( parent );
  }
  
  public U getUiRoot() {
    return itemUiList.getUiRoot();
  }

  public void initialize() {
    if( itemUiList.isTimelineEmpty() ) {
      itemUiList.fetch( MORE );
    }
    itemUiList.update();
    scroller.scrollIntoView();
    topItemUpdater.register();
  }
  
  public void fetchNew() {
    itemUiList.fetch( NEW  );
  }

  public void update() {
    itemUiList.update();
  }
}