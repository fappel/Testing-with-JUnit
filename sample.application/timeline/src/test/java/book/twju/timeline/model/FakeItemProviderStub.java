package book.twju.timeline.model;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class FakeItemProviderStub implements ItemProvider<FakeItem> {
  
  private final Set<FakeItem> items;

  FakeItemProviderStub() {
    items = new HashSet<>();
  }

  @Override
  public List<FakeItem> fetchItems( FakeItem ancestor, int count ) {
    return items
        .stream()
        .sorted( descending() )
        .filter( item -> isApplicable( ancestor, item ) )
        .limit( count )
        .collect( toList() );
  }
  
  @Override
  public int getNewCount( FakeItem predecessor ) {
    return fetchNew( predecessor ).size();
  }
  
  @Override
  public List<FakeItem> fetchNew( FakeItem predecessor ) {
    return items
        .stream()
        .sorted( descending() )
        .filter( item -> isNewer( item, predecessor ) )
        .collect( toList() );
  }

  FakeItemProviderStub addItems( FakeItem ... itemsToAdd ) {
    items.addAll( asList( itemsToAdd ) );
    return this;
  }
  
  private Comparator<? super FakeItem> descending() {
    return ( first, second ) -> -first.compareTo( second );
  }
  
  private boolean isNewer( FakeItem item, FakeItem predecessor ) {
    return item.compareTo( predecessor ) > 0;
  }
  
  private boolean isApplicable( Item ancestor, Item item ) {
    return ancestor == null || item.compareTo( ancestor ) < 0;
  }
}