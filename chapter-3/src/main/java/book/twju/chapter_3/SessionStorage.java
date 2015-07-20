package book.twju.chapter_3;

public interface SessionStorage {
  void storeTop( Item top );
  Item readTop();
}