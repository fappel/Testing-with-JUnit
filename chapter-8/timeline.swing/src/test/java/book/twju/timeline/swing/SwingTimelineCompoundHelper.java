package book.twju.timeline.swing;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Container;

import javax.swing.JPanel;

import book.twju.timeline.model.Item;
import book.twju.timeline.ui.ItemViewer;

class SwingTimelineCompoundHelper {

  @SuppressWarnings("unchecked")
  static Header<Item> stubHeader() {
    Header<Item> result = mock( Header.class );
    when( result.getComponent() ).thenReturn( new JPanel() );
    return result;
  }
  
  @SuppressWarnings("unchecked")
  static ItemViewer<Item, Container> stubItemViewer() {
    ItemViewer<Item, Container> result = mock( ItemViewer.class );
    when( result.getUiRoot() ).thenReturn( new JPanel() );
    return result;
  }
  
  @SuppressWarnings("unchecked")
  static SwingAutoUpdate<Item> stubAutoUpdate() {
    return mock( SwingAutoUpdate.class );
  }

  @SuppressWarnings("unchecked")
  static SwingTimelineCompound<Item> stubCompound(
    Header<Item> header, ItemViewer<Item, Container> itemViewer, SwingAutoUpdate<Item> autoUpdate )
  {
    SwingTimelineCompound<Item> result = mock( SwingTimelineCompound.class );
    when( result.getAutoUpdate() ).thenReturn( autoUpdate );
    when( result.getItemViewer() ).thenReturn( itemViewer );
    when( result.getHeader() ).thenReturn( header );
    return result;
  }
}