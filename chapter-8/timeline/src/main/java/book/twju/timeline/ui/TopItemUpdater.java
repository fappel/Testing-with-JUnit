package book.twju.timeline.ui;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;

import book.twju.timeline.model.Item;
import book.twju.timeline.model.Timeline;

public abstract class TopItemUpdater<T extends Item, U> {
  
  protected final ItemUiMap<T, U> itemUiMap;
  protected final Timeline<T> timeline;

  protected TopItemUpdater( Timeline<T> timeline, ItemUiMap<T, U> itemUiMap ) {
    this.timeline = timeline;
    this.itemUiMap = itemUiMap;
  }
  
  public void update() {
    List<T> items = calculateItemsBelowTop();
    if( !items.isEmpty() ) {
      updateTopItem( items.get( 0 ) );
    }
  }

  protected abstract void register();
  protected abstract boolean isBelowTop( ItemUi<T> itemUi );
  
  private List<T> calculateItemsBelowTop() {
    return timeline.getItems()
      .stream()
      .filter( item -> isBelowTop( item ) )
      .collect( toList() );
  }

  private boolean isBelowTop( T item ) {
    if( getItemUi( item ) == null ) {
      return false;
    }
    return isBelowTop( getItemUi( item ) );
  }
  
  private ItemUi<T> getItemUi( T item ) {
    return itemUiMap.findByItemId( item.getId() );
  }
  
  private void updateTopItem( T newTopItem ) {
    Optional<T> oldTopItem = timeline.getTopItem();
    if( mustUpdate( newTopItem, oldTopItem ) ) {
      timeline.setTopItem( newTopItem );
    }
  }

  private boolean mustUpdate( T newTopItem, Optional<T> oldTopItem ) {
    return oldTopItem.isPresent() && !oldTopItem.get().equals( newTopItem ) || !oldTopItem.isPresent();
  }
}