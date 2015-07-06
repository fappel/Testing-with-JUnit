package book.twju.timeline.swing;

import static book.twju.timeline.swing.SwingTimelines.WHITE;
import static book.twju.timeline.swing.SwingTimelines.createUiItemConstraints;
import static book.twju.timeline.ui.FetchOperation.MORE;
import static book.twju.timeline.util.Conditions.checkArgument;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

import java.awt.Container;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import book.twju.timeline.model.Item;
import book.twju.timeline.ui.FetchOperation;
import book.twju.timeline.ui.ItemUiMap;

class ItemUiList<T extends Item> {
  
  static final String OPERATION_MUST_NOT_BE_NULL = "Argument 'operation' must not be null.";

  private final BackgroundProcessor backgroundProcessor;
  private final ItemUiMap<T, Container> itemUiMap;

  JScrollPane component;
  JButton fetchMore;
  JPanel content;
  
  ItemUiList( ItemUiMap<T, Container> itemUiMap ) {
    this( itemUiMap, new BackgroundProcessor() );
  }
  
  ItemUiList( ItemUiMap<T, Container> itemUiMap, BackgroundProcessor backgroundProcessor ) {
    this.backgroundProcessor = backgroundProcessor;
    this.itemUiMap = itemUiMap;
  }

  JScrollPane getComponent() {
    return component;
  }
  
  boolean isTimelineEmpty() {
    return itemUiMap.isTimelineEmpty();
  }
  
  void fetchInBackground( FetchOperation operation ) {
    checkArgument( operation != null, OPERATION_MUST_NOT_BE_NULL );

    backgroundProcessor.process( () -> fetch( operation ) );
  }
  
  void fetch( FetchOperation operation ) {
    checkArgument( operation != null, OPERATION_MUST_NOT_BE_NULL );
    
    itemUiMap.fetch( operation );
    backgroundProcessor.dispatchToUiThread( () -> update() );
  }

  void update() {
    content.remove( fetchMore );
    itemUiMap.update( content );
    content.add( fetchMore, createUiItemConstraints() );
    content.getParent().doLayout();
  }
  
  void createUi() {
    createContent();
    createComponent();
    createFetchMore();
  }
  
  private void createContent() {
    content = new JPanel();
    content.setLayout( new GridBagLayout() );
    content.setBackground( WHITE );
  }
  
  private void createComponent() {
    component = new JScrollPane( content );
    component.setVerticalScrollBarPolicy( VERTICAL_SCROLLBAR_ALWAYS );
  }
  
  private void createFetchMore() {
    fetchMore = new JButton( "more" );
    fetchMore.addActionListener( event -> fetchInBackground( MORE ) );
    content.add( fetchMore );
  }
}