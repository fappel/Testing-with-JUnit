package book.twju.timeline.model;

public interface SessionStorage<T extends Item> {
  void store( Memento<T> Memento );
  Memento<T> read();
}