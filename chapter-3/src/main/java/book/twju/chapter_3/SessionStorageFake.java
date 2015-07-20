package book.twju.chapter_3;

// This fake session storage implementation is only available to avoid 
// compile error due to test listing evaluation and will be removed
class SessionStorageFake implements SessionStorage {

  @Override
  public void storeTop( Item top ) {
  }

  @Override
  public Item readTop() {
    return null;
  }
}
