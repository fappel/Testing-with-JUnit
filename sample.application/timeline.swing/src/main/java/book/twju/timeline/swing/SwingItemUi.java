package book.twju.timeline.swing;

import java.awt.Component;

import book.twju.timeline.model.Item;
import book.twju.timeline.ui.ItemUi;

public interface SwingItemUi<T extends Item> extends ItemUi<T>{
  Component getComponent();
}
