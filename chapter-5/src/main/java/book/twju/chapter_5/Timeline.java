package book.twju.chapter_5;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Timeline {

  public static final int FETCH_COUNT_LOWER_BOUND = 1;
  public static final int FETCH_COUNT_UPPER_BOUND = 20;

  static final String ERROR_EXCEEDS_LOWER_BOUND 
  = "FetchCount of %s exceeds lower bound of " + FETCH_COUNT_LOWER_BOUND + ".";
  static final String ERROR_EXCEEDS_UPPER_BOUND 
    = "FetchCount of %s exceeds upper bound of " + FETCH_COUNT_UPPER_BOUND + ".";
  static final String ERROR_SESSION_STORAGE_MUST_NOT_BE_NULL = "Argument 'sessionStorage' must not be null.";
  static final String ERROR_ITEM_PROVIDER_MUST_NOT_BE_NULL = "Argument 'itemProvider' must not be null.";
  static final String ERROR_STORE_TOP = "Unable to store top item";
  static final String ERROR_READ_TOP = "Unable to read top item";
  static final int DEFAULT_FETCH_COUNT = 10;

  private final SessionStorage sessionStorage;
  private final ItemProvider itemProvider;
  private final List<Item> items;

  private int fetchCount;
  
  public Timeline( ItemProvider itemProvider, SessionStorage sessionStorage  ) {
    checkArgument( itemProvider != null, ERROR_ITEM_PROVIDER_MUST_NOT_BE_NULL );
    checkArgument( sessionStorage != null, ERROR_SESSION_STORAGE_MUST_NOT_BE_NULL );
    
    this.itemProvider = itemProvider;
    this.sessionStorage = sessionStorage;
    this.fetchCount = DEFAULT_FETCH_COUNT;
    this.items = new ArrayList<>();
  }

  public void setFetchCount( int fetchCount ) {
    checkArgument( fetchCount >= FETCH_COUNT_LOWER_BOUND, ERROR_EXCEEDS_LOWER_BOUND, fetchCount );
    checkArgument( fetchCount <= FETCH_COUNT_UPPER_BOUND, ERROR_EXCEEDS_UPPER_BOUND, fetchCount );
    
    this.fetchCount = fetchCount;
  }

  public int getFetchCount() {
    return fetchCount;
  }
  
  public List<Item> getItems() {
    return items;
  }

  public void fetchItems() {
    int restoreOffset = restoreIfNeeded();
    items.addAll( itemProvider.fetchItems( getOldest(), getFetchCount() + restoreOffset ) );
    storeTopItem();
  }
  
  private void storeTopItem() {
    try {
      sessionStorage.storeTop( getTopItem() );
    } catch( RuntimeException rte ) {
      throw rte;
    } catch( IOException cause ) {
      throw new IllegalStateException( ERROR_STORE_TOP, cause );
    }
  }

  private int restoreIfNeeded() {
    int result = 0;
    if( items.isEmpty() ) {
      Item top = readTopItem();
      if( top != null ) {
        items.add( top );
        result = -1;
      }
    }
    return result;
  }

  private Item readTopItem() {
    try {
      return sessionStorage.readTop();
    } catch( RuntimeException rte ) {
      throw rte;
    } catch( IOException cause ) {
      throw new IllegalStateException( ERROR_READ_TOP, cause );
    }
  }

  private Item getOldest() {
    if( items.isEmpty() ) {
      return null;
    }
    return items.get( items.size() - 1 );
  }
  
  private Item getTopItem() {
    if( items.isEmpty() ) {
      return null;
    }
    return items.get( 0 );
  }
  
  private void checkArgument( boolean condition, String pattern, Object ... args ) {
    if( !condition ) {
      throw new IllegalArgumentException( String.format( pattern, args ) );
    }
  }
}