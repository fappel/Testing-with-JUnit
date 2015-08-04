package book.twju.timeline.swt;

import org.eclipse.swt.widgets.Control;

import book.twju.timeline.model.Item;
import book.twju.timeline.ui.ItemUi;

public interface SwtItemUi<T extends Item> extends ItemUi<T> {
  Control getControl();
}
