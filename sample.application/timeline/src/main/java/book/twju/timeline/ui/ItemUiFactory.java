package book.twju.timeline.ui;

import book.twju.timeline.model.Item;

public interface ItemUiFactory<T extends Item, U> {
  ItemUi<T> create( U uiContext, T item, int index  );
}