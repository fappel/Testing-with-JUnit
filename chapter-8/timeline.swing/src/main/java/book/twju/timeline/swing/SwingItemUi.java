package book.twju.timeline.swing;

import static java.awt.GridBagConstraints.HORIZONTAL;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import book.twju.timeline.model.Item;
import book.twju.timeline.ui.ItemUi;

public interface SwingItemUi<T extends Item> extends ItemUi<T> {

  Component getComponent();

  public static GridBagConstraints createUiItemConstraints() {
    GridBagConstraints result = new GridBagConstraints();
    result.gridx = 0;
    result.fill = HORIZONTAL;
    result.weightx = 1;
    result.insets = new Insets( 15, 10, 5, 10 );
    return result;
  }
}
