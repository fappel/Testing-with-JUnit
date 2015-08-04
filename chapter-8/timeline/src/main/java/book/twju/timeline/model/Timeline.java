package book.twju.timeline.model;

import static book.twju.timeline.util.Assertion.checkArgument;
import static java.lang.Long.compare;
import static java.util.Collections.sort;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class Timeline<T extends Item> {

  public static final int FETCH_COUNT_LOWER_BOUND = 1;
  public static final int FETCH_COUNT_UPPER_BOUND = 20;
  public static final int DEFAULT_FETCH_COUNT = 10;

  static final String ERROR_EXCEEDS_LOWER_BOUND 
  = "FetchCount of %s exceeds lower bound of " + FETCH_COUNT_LOWER_BOUND + ".";
  static final String ERROR_EXCEEDS_UPPER_BOUND 
    = "FetchCount of %s exceeds upper bound of " + FETCH_COUNT_UPPER_BOUND + ".";
  static final String ERROR_TOP_ITEM_IS_UNRELATED = "TopItem <%s> is not contained in item list.";
  static final String ERROR_TOP_ITEM_MUST_NOT_BE_NULL = "Argument 'topItem' must not be null.";
  static final String ERROR_SESSION_PROVIDER_MUST_NOT_BE_NULL = "Argument 'sessionProvider' must not be null.";
  static final String ERROR_ITEM_PROVIDER_MUST_NOT_BE_NULL = "Argument 'itemProvider' must not be null.";

  private final SessionStorage<T> sessionStorage;
  private final ItemProvider<T> itemProvider;
  private final List<T> items;

  private Optional<T> topItem;
  private int fetchCount;

  public Timeline( ItemProvider<T> itemProvider, SessionStorage<T> sessionStorage ) {
    checkArgument( itemProvider != null, ERROR_ITEM_PROVIDER_MUST_NOT_BE_NULL );
    checkArgument( sessionStorage != null, ERROR_SESSION_PROVIDER_MUST_NOT_BE_NULL );
    
    this.itemProvider = itemProvider;
    this.sessionStorage = sessionStorage;
    this.items = new ArrayList<>();
    this.fetchCount = DEFAULT_FETCH_COUNT;
    this.topItem = Optional.empty();
    restore();
  }

  public void setFetchCount( int fetchCount ) {
    checkArgument( fetchCount <= FETCH_COUNT_UPPER_BOUND, ERROR_EXCEEDS_UPPER_BOUND, fetchCount );
    checkArgument( fetchCount >= FETCH_COUNT_LOWER_BOUND, ERROR_EXCEEDS_LOWER_BOUND, fetchCount );
    this.fetchCount = fetchCount;
  }
  
  public int getFetchCount() {
    return fetchCount;
  }

  public Optional<T> getTopItem() {
    return topItem;
  }
  
  public void setTopItem( T topItem ) {
    checkArgument( topItem != null, ERROR_TOP_ITEM_MUST_NOT_BE_NULL );
    checkArgument( items.contains( topItem ), ERROR_TOP_ITEM_IS_UNRELATED, topItem.getId() );
    
    this.topItem = Optional.of( topItem );
    sessionStorage.store( createMemento() );
  }
  
  public void fetchItems() {
    addSorted( itemProvider.fetchItems( getOldest(), getFetchCount() ) );
    updateTopItem();
    sessionStorage.store( createMemento() );
  }
  
  public int getNewCount() {
    return itemProvider.getNewCount( getLatest() );
  }
  
  public void fetchNew() {
    addSorted( itemProvider.fetchNew( getLatest() ) );
    sessionStorage.store( createMemento() );
  }
  
  public List<T> getItems() {
    return new ArrayList<>( items );
  }
  
  private T getLatest() {
    if( items.isEmpty() ) {
      return null;
    }
    return items.get( 0 );
  }
  
  private T getOldest() {
    if( items.isEmpty() ) {
      return null;
    }
    return items.get( items.size() - 1 );
  }
  
  private void addSorted( List<T> additionalItems ) {
    items.addAll( additionalItems );
    sort( items, ( first, second ) -> compare( second.getTimeStamp(), first.getTimeStamp() ) );
  }

  private void restore() {
    Memento<T> memento = sessionStorage.read();
    if( !memento.getItems().isEmpty() ) {
      addSorted( new ArrayList<>( memento.getItems() ) );
      setTopItem( memento.getTopItem().get() );
    }
  }

  private void updateTopItem() {
    if( !topItem.isPresent() ) {
      topItem = items.isEmpty() ? Optional.empty() : Optional.of( items.get( 0 ) );
    }
  }
  
  private Memento<T> createMemento() {
    return new Memento<>( new HashSet<>( items ), getTopItem() );
  }
}