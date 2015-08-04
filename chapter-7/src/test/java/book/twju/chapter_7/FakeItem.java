package book.twju.chapter_7;

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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ( int )( timeStamp ^ ( timeStamp >>> 32 ) );
    return result;
  }

  @Override
  public boolean equals( Object obj ) {
    if( this == obj ) {
      return true;
    }
    if( obj == null ) {
      return false;
    }
    if( getClass() != obj.getClass() ) {
      return false;
    }
    FakeItem other = ( FakeItem )obj;
    if( timeStamp != other.timeStamp ) {
      return false;
    }
    return true;
  }
}