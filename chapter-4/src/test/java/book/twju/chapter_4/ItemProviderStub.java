package book.twju.chapter_4;

import static java.lang.Long.compare;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class ItemProviderStub implements ItemProvider {
  
  private final Set<Item> items;

  ItemProviderStub() {
    items = new HashSet<>();
  }

  @Override
  // location 1: stub implementation to provide items to fetch
  public List<Item> fetchItems( Item ancestor, int itemCount ) {
    return items
      .stream()
      .sorted( descending() )
      .filter( item -> isApplicable( ancestor, item ) )
      .limit( itemCount )
      .collect( toList() );
  }

  // location 2: method for stub configuration
  void addItems( Item ... itemsToAdd ) {
    items.addAll( asList( itemsToAdd ) );
  }
  
  private Comparator<? super Item> descending() {
    return ( first, second ) 
      -> compare( second.getTimeStamp(), first.getTimeStamp() );
  }
  
  private boolean isApplicable( Item ancestor, Item item ) {
    return    ancestor == null 
           || item.getTimeStamp() < ancestor.getTimeStamp();
  }
  
  @Override
  public int getNewCount( Item predecessor ) {
    return 0;
  }
  
  @Override
  public List<Item> fetchNew( Item predecessor ) {
    return emptyList();
  }
}