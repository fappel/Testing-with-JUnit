package book.twju.timeline.ui;

import static book.twju.timeline.util.Assertion.checkArgument;

import java.util.HashMap;
import java.util.Map;

import book.twju.timeline.model.Item;
import book.twju.timeline.model.Timeline;

public class ItemUiMap<T extends Item, U> {

  static final String TIMELINE_MUST_NOT_BE_NULL = "Argument 'timeline' must not be null.";
  static final String ITEM_UI_FACTORY_MUST_NOT_BE_NULL = "Argument 'itemUiFactory' must not be null.";
  static final String ID_MUST_NOT_BE_NULL = "Argument 'id' must not be null.";
  static final String UI_CONTEXT_MUST_NOT_BE_NULL = "Argument 'uiContext' must not be null.";
  static final String UNKNOWN_ITEM_UI_ENTRY = "Id <%s> does not refer to a mapped ItemUi entry.";
  static final String OPERATION_MUST_NOT_BE_NULL = "Argument 'operation' must not be null.";
  
  protected final ItemUiFactory<T, U> itemUiFactory;
  protected final Map<String, ItemUi<T>> itemUis;
  protected final Timeline<T> timeline;
  
  public ItemUiMap( Timeline<T> timeline, ItemUiFactory<T, U> itemUiFactory ) {
    checkArgument( timeline != null, TIMELINE_MUST_NOT_BE_NULL );
    checkArgument( itemUiFactory != null, ITEM_UI_FACTORY_MUST_NOT_BE_NULL );

    this.itemUiFactory = itemUiFactory;
    this.timeline = timeline;
    this.itemUis = new HashMap<>();
  }
  
  public boolean containsItemUi( String id ) {
    checkArgument( id != null, ID_MUST_NOT_BE_NULL );

    return itemUis.containsKey( id );
  }
  
  public ItemUi<T> findByItemId( String id ) {
    checkArgument( id != null, ID_MUST_NOT_BE_NULL );
    checkArgument( containsItemUi( id ), UNKNOWN_ITEM_UI_ENTRY, id );
    
    return itemUis.get( id );
  }
  
  public boolean isTimelineEmpty() {
    return timeline.getItems().isEmpty();
  }
  
  public void fetch( FetchOperation operation ) {
    checkArgument( operation != null, OPERATION_MUST_NOT_BE_NULL );
    
    operation.fetch( timeline );
  }
  
  public void update( U uiContext ) {
    checkArgument( uiContext != null, UI_CONTEXT_MUST_NOT_BE_NULL );
    
    timeline.getItems().forEach( item -> doUpdate( uiContext, item ) );
  }

  private void doUpdate( U uiContext, T item ) {
    if( containsItemUi( item.getId() ) ) {
      findByItemId( item.getId() ).update();
    } else {
      createItem( uiContext, item );
    }
  }

  private void createItem( U uiContext, T item ) {
    int index = timeline.getItems().indexOf( item );
    ItemUi<T> itemUi = itemUiFactory.create( uiContext, item, index );
    itemUis.put( item.getId(), itemUi );
  }
}