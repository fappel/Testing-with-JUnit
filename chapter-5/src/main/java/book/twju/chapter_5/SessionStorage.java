package book.twju.chapter_5;

import java.io.IOException;

public interface SessionStorage {
  void storeTop( Item top ) throws IOException;
  Item readTop() throws IOException;
}