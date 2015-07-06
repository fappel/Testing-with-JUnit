package book.twju.timeline.ui;

import book.twju.timeline.model.Item;

public interface ItemUi<T extends Item> {
  void update();
}
