package book.twju.timeline.swing;

import static java.util.stream.Collectors.toList;

import java.awt.Component;
import java.awt.Container;
import java.util.List;
import java.util.Optional;

import javax.swing.BoundedRangeModel;
import javax.swing.JScrollPane;

import book.twju.timeline.model.Item;
import book.twju.timeline.model.Timeline;
import book.twju.timeline.ui.ItemUiMap;

class TopItemUpdater<T extends Item> {
  
  private final UiThreadDispatcher uiThreadDispatcher;
  private final ItemUiMap<T, Container> itemUiMap;
  private final ItemUiList<T> itemUiList;
  private final Timeline<T> timeline;

  TopItemUpdater( Timeline<T> timeline, ItemUiMap<T, Container> itemUiMap, ItemUiList<T> itemUiList ) {
    this( timeline, itemUiMap, itemUiList, new UiThreadDispatcher() );
  }
  
  TopItemUpdater(
    Timeline<T> timeline, ItemUiMap<T, Container> itemUiMap, ItemUiList<T> itemUiList, UiThreadDispatcher dispatcher )
  {
    this.uiThreadDispatcher = dispatcher;
    this.itemUiList = itemUiList;
    this.itemUiMap = itemUiMap;
    this.timeline = timeline;
  }

  void register() {
    uiThreadDispatcher.dispatch( () -> getVerticalScrollBarModel().addChangeListener( ( evt ) -> update() ) );
  }

  void update() {
    List<T> items = calculateItemsBelowTop();
    if( !items.isEmpty() ) {
      updateTopItem( items.get( 0 ) );
    }
  }
  
  BoundedRangeModel getVerticalScrollBarModel() {
    return ( ( JScrollPane )itemUiList.getComponent() ).getVerticalScrollBar().getModel();
  }
  
  private List<T> calculateItemsBelowTop() {
    return timeline.getItems()
      .stream()
      .filter( item -> isBelowTop( item ) )
      .collect( toList() );
  }
  
  private boolean isBelowTop( T item ) {
    SwingItemUi<T> itemUi = getItemUi( item );
    if( itemUi != null ) {
      Component component = itemUi.getComponent();
      if( component.isShowing() ) {
        return isBelowTop( component );
      }
    }
    return false;
  }

  private boolean isBelowTop( Component component ) {
    double y1 = component.getParent().getParent().getLocationOnScreen().getY();
    double y2 = component.getLocationOnScreen().getY();
    return y2 - y1 >= 0;
  }

  private SwingItemUi<T> getItemUi( T item ) {
    return ( SwingItemUi<T> )itemUiMap.findByItemId( item.getId() );
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