package book.twju.timeline.swing;

import static book.twju.timeline.util.Assertion.checkArgument;

import java.awt.Container;

import book.twju.timeline.model.Item;
import book.twju.timeline.model.ItemProvider;
import book.twju.timeline.model.SessionStorage;
import book.twju.timeline.model.Timeline;
import book.twju.timeline.ui.AutoUpdate;
import book.twju.timeline.ui.ItemUiFactory;
import book.twju.timeline.ui.ItemViewer;
import book.twju.timeline.util.BackgroundProcessor;

class SwingTimelineCompound<T extends Item> {

  static final String ITEM_PROVIDER_MUST_NOT_BE_NULL = "Argument 'itemProvider' must not be null.";
  static final String ITEM_UI_FACTORY_MUST_NOT_BE_NULL = "Argument 'itemUiFactory' must not be null.";
  static final String SESSION_STORAGE_MUST_NOT_BE_NULL = "Argument 'sessionStorage' must not be null.";

  private final ItemViewer<T, Container> itemViewer;
  private final AutoUpdate<T, Container> autoUpdate;
  private final Header<T> header;
  
  static BackgroundProcessor createBackgroundProcessor() {
    return new BackgroundProcessor( new SwingUiThreadDispatcher() );
  }

  SwingTimelineCompound( 
    ItemProvider<T> itemProvider, ItemUiFactory<T, Container> itemUiFactory, SessionStorage<T> sessionStorage )
  {
    checkArgument( itemProvider != null, ITEM_PROVIDER_MUST_NOT_BE_NULL );
    checkArgument( itemUiFactory != null, ITEM_UI_FACTORY_MUST_NOT_BE_NULL );
    checkArgument( sessionStorage != null, SESSION_STORAGE_MUST_NOT_BE_NULL );
    
    Timeline<T> timeline = new Timeline<>( itemProvider, sessionStorage );
    itemViewer = new ItemViewer<>( new SwingItemViewerCompound<>( timeline, itemUiFactory ) );
    header = new Header<T>( timeline );
    autoUpdate = new SwingAutoUpdate<>( header, itemViewer, 5_000 );
  }
  
  ItemViewer<T, Container> getItemViewer() {
    return itemViewer;
  }
  
  Header<T> getHeader() {
    return header;
  }
  
  AutoUpdate<T, Container> getAutoUpdate() {
    return autoUpdate;
  }
}