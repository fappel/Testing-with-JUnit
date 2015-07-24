package book.twju.chapter_6;

public interface SessionStorage {
  void store( Memento memento );
  Memento read();
}