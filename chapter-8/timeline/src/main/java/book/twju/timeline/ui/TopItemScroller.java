package book.twju.timeline.ui;

import book.twju.timeline.model.Item;

public interface TopItemScroller<T extends Item> {
  void scrollIntoView();
}
