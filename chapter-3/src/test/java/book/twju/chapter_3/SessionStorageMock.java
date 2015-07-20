package book.twju.chapter_3;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

class SessionStorageMock implements SessionStorage {

  private boolean storeTopDone;
  private Item expectedTopItem;
  
  @Override
  public void storeTop( Item top ) {
    assertFalse( storeTopDone );
    assertSame( expectedTopItem, top );
    storeTopDone = true;
  }
  
  void setExpectedTopItem( Item expectedTopItem ) {
    this.expectedTopItem = expectedTopItem;
  }

  public void verify() {
    assertTrue( storeTopDone );
  }

  @Override
  public Item readTop() {
    return null;
  }
}