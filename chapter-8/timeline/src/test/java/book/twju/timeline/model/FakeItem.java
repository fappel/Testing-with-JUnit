package book.twju.timeline.model;

public class FakeItem extends Item {

  public FakeItem( String id , long timeStamp  ) {
    super( id, timeStamp );
  }
  
  @Override
  public String toString() {
    return "FakeItem [timeStamp=" + timeStamp + ", id=" + id + "]";
  }
}