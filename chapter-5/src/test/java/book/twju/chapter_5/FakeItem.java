package book.twju.chapter_5;

public class FakeItem implements Item {

  private final long timeStamp;
  
  FakeItem( long timeStamp ) {
    this.timeStamp = timeStamp;
  }
  
  @Override
  public long getTimeStamp() {
    return timeStamp;
  }

  @Override
  public String toString() {
    return "FakeItem [timeStamp=" + timeStamp + "]";
  }
}