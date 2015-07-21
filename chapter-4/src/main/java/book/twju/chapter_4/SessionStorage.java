package book.twju.chapter_4;

import java.io.IOException;

public interface SessionStorage {
  void storeTop( Item top ) throws IOException;
  Item readTop() throws IOException;
}