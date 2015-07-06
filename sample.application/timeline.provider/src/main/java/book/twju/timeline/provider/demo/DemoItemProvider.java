package book.twju.timeline.provider.demo;

import static java.lang.Long.compare;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import book.twju.timeline.model.Item;
import book.twju.timeline.model.ItemProvider;

public class DemoItemProvider implements ItemProvider<DemoItem> {
  
  private Set<DemoItem> items;

  DemoItemProvider() {
    items = new CopyOnWriteArraySet<>();
  }

  @Override
  public List<DemoItem> fetchItems( DemoItem oldestItem, int count ) {
    return items
        .stream()
        .sorted( sort() )
        .filter( item -> { return apply( oldestItem, item ); } )
        .limit( count )
        .collect( toList() );
  }
  
  @Override
  public int getNewCount( DemoItem latestItem ) {
    return fetchNew( latestItem ).size();
  }
  
  @Override
  public List<DemoItem> fetchNew( DemoItem latestItem ) {
    return items
        .stream()
        .sorted( sort() )
        .filter( item -> { return item.getTimeStamp() > latestItem.getTimeStamp(); } )
        .collect( toList() );
  }

  DemoItemProvider addItem( DemoItem ... itemsToAdd ) {
    items.addAll( asList( itemsToAdd ) );
    return this;
  }
  
  private Comparator<? super Item> sort() {
    return ( first, second ) -> compare( second.getTimeStamp(), first.getTimeStamp() );
  }
  
  private boolean apply( Item oldestItem, Item item ) {
    return oldestItem == null || item.getTimeStamp() < oldestItem.getTimeStamp();
  }
}