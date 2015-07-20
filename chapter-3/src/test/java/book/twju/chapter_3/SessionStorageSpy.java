package book.twju.chapter_3;

import java.util.ArrayList;
import java.util.List;

class SessionStorageSpy implements SessionStorage {

  private final List<Item> log;

  SessionStorageSpy() {
    log = new ArrayList<>();
  }
  
  @Override
  public void storeTop( Item top ) {
    log.add( top );
  }

  public List<Item> getLog() {
    return log;
  }

  @Override
  public Item readTop() {
    return null;
  }
}