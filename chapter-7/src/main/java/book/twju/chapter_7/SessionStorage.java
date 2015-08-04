package book.twju.chapter_7;

public interface SessionStorage {
  void store( Memento memento );
  Memento read();
}