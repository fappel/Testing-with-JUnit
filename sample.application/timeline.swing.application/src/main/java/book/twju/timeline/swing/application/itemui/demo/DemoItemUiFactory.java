package book.twju.timeline.swing.application.itemui.demo;

import java.awt.Container;

import book.twju.timeline.provider.demo.DemoItem;
import book.twju.timeline.ui.ItemUi;
import book.twju.timeline.ui.ItemUiFactory;

public class DemoItemUiFactory implements ItemUiFactory<DemoItem, Container> {

  @Override
  public ItemUi<DemoItem> create( Container uiContext, DemoItem item, int index ) {
    return new DemoItemUi( uiContext, item, index );
  }
}
