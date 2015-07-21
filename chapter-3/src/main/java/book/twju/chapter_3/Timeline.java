package book.twju.chapter_3;

import java.util.ArrayList;
import java.util.List;

public class Timeline {

  public static final int FETCH_COUNT_LOWER_BOUND = 1;
  public static final int FETCH_COUNT_UPPER_BOUND = 20;

  static final int DEFAULT_FETCH_COUNT = 10;

  private final SessionStorage sessionStorage;
  private final ItemProvider itemProvider;
  private final List<Item> items;

  private int fetchCount;
  
  // Note: this constructor is only available for listing evolution and will be removed
  public Timeline( ItemProvider itemProvider ) {
    this( itemProvider, new SessionStorageFake() );
  }
  
  public Timeline( ItemProvider itemProvider, SessionStorage sessionStorage  ) {
    this.itemProvider = itemProvider;
    this.sessionStorage = sessionStorage;
    this.fetchCount = DEFAULT_FETCH_COUNT;
    this.items = new ArrayList<>();
  }

  public void setFetchCount( int fetchCount ) {
    if(    fetchCount >= FETCH_COUNT_LOWER_BOUND 
        && fetchCount <= FETCH_COUNT_UPPER_BOUND )
    {
      this.fetchCount = fetchCount;
    }
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
    sessionStorage.storeTop( getTopItem() );
  }

  private int restoreIfNeeded() {
    int result = 0;
    if( items.isEmpty() ) {
      Item top = sessionStorage.readTop();
      if( top != null ) {
        items.add( top );
        result = -1;
      }
    }
    return result;
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
}