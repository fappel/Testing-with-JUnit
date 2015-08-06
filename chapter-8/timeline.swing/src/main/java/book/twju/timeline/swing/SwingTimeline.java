package book.twju.timeline.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JPanel;

import book.twju.timeline.model.Item;
import book.twju.timeline.model.ItemProvider;
import book.twju.timeline.model.SessionStorage;
import book.twju.timeline.ui.AutoUpdate;
import book.twju.timeline.ui.ItemUiFactory;
import book.twju.timeline.ui.ItemViewer;

public class SwingTimeline<T extends Item> {

  private final ItemViewer<T, Container> itemViewer;
  private final AutoUpdate<T, Container> autoUpdate;
  private final Header<T> header;
  private final JPanel component;

  public SwingTimeline(
    ItemProvider<T> itemProvider, ItemUiFactory<T, Container> itemUiFactory, SessionStorage<T> sessionStorage )
  {
    this( new SwingTimelineCompound<>( itemProvider, itemUiFactory, sessionStorage ) );
  }

  SwingTimeline( SwingTimelineCompound<T> compound ) {
    itemViewer = compound.getItemViewer();
    header = compound.getHeader();
    autoUpdate = compound.getAutoUpdate();
    component = initialize();
  }

  public Component getComponent() {
    return component;
  }
  
  public void startAutoRefresh() {
    autoUpdate.start();
  }
  
  public void stopAutoRefresh() {
    autoUpdate.stop();
  }
  
  public void setTitle( String title ) {
    header.setTitle( title );
  }

  private JPanel initialize() {
    header.createUi();
    itemViewer.createUi( null );
    JPanel result = createComponent();
    itemViewer.initialize();
    header.onFetchNew( event -> itemViewer.fetchNew() );
    return result;
  }
  
  private JPanel createComponent() {
    JPanel result = new JPanel();
    result.setLayout( new BorderLayout() );
    result.add( header.getComponent(), BorderLayout.NORTH );
    result.add( itemViewer.getUiRoot(), BorderLayout.CENTER );
    return result;
  }
}