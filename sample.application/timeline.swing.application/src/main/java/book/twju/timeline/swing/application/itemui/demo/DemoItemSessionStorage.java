package book.twju.timeline.swing.application.itemui.demo;

import book.twju.timeline.model.Memento;
import book.twju.timeline.model.SessionStorage;
import book.twju.timeline.provider.demo.DemoItem;

public class DemoItemSessionStorage implements SessionStorage<DemoItem> {

  @Override
  public void store( Memento<DemoItem> Memento ) {
  }

  @Override
  public Memento<DemoItem> read() {
    return Memento.empty();
  }
}