package book.twju.timeline.ui;

import book.twju.timeline.model.Item;

public interface AutoUpdate<T extends Item, U> {
  void start();
  void stop();
}