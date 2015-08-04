package book.twju.timeline.model;

import static book.twju.timeline.util.Assertion.checkArgument;

public abstract class Item implements Comparable<Item> {
  
  protected final long timeStamp;
  protected final String id;
  
  public Item( String id , long timeStamp   ) {
    checkArgument( timeStamp >= 0, "Argument 'timeStamp' must be a non negative long value but is <%s>.", timeStamp );
    checkArgument( id != null, "Argument 'id' must not be null." );
    
    this.timeStamp = timeStamp;
    this.id = id;
  }
  
  public long getTimeStamp() {
    return timeStamp;
  }
  
  public String getId() {
    return id;
  }

  @Override
  public int compareTo( Item item ) {
    int result = Long.compare( getTimeStamp(), item.getTimeStamp() );
    if( result == 0 ) {
      result = getId().compareTo( item.getId() );
    }
    return result;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + id.hashCode();
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
    Item other = ( Item )obj;
    if( !id.equals( other.id ) ) {
      return false;
    }
    if( timeStamp != other.timeStamp ) {
      return false;
    }
    return true;
  }
}