package book.twju.timeline.ui;

import book.twju.timeline.model.Item;

public interface ItemViewerCompound<T extends Item, U> {
  ItemUiList<T, U> getItemUiList();
  TopItemScroller<T> getScroller();
  TopItemUpdater<T, U> getTopItemUpdater();
}