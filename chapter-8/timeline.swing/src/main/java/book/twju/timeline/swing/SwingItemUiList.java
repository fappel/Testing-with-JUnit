package book.twju.timeline.swing;

import static book.twju.timeline.swing.SwingItemUi.createUiItemConstraints;
import static book.twju.timeline.swing.SwingTimelineCompound.createBackgroundProcessor;
import static book.twju.timeline.swing.Resources.WHITE;
import static book.twju.timeline.ui.FetchOperation.MORE;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

import java.awt.Container;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import book.twju.timeline.model.Item;
import book.twju.timeline.ui.ItemUiList;
import book.twju.timeline.ui.ItemUiMap;
import book.twju.timeline.util.BackgroundProcessor;

class SwingItemUiList<T extends Item> extends ItemUiList<T, Container> {
  
  JScrollPane uiRoot;
  JButton fetchMore;
  JPanel content;
  
  SwingItemUiList( ItemUiMap<T, Container> itemUiMap ) {
    this( itemUiMap, createBackgroundProcessor() );
  }
  
  SwingItemUiList( ItemUiMap<T, Container> itemUiMap, BackgroundProcessor backgroundProcessor ) {
    super( itemUiMap, backgroundProcessor );
  }

  @Override
  protected JScrollPane getUiRoot() {
    return uiRoot;
  }
  
  @Override
  protected Container getContent() {
    return content;
  }
  
  @Override
  protected void beforeContentUpdate() {
    content.remove( fetchMore );
  }
  
  @Override
  protected void afterContentUpdate() {
    content.add( fetchMore, createUiItemConstraints() );
    content.getParent().doLayout();
  }

  @Override
  protected void createUi( Container parent ) {
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
    uiRoot = new JScrollPane( content );
    uiRoot.setVerticalScrollBarPolicy( VERTICAL_SCROLLBAR_ALWAYS );
  }
  
  private void createFetchMore() {
    fetchMore = new JButton( "more" );
    fetchMore.addActionListener( event -> fetchInBackground( MORE ) );
    content.add( fetchMore );
  }
}