package book.twju.timeline.swing;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.swing.JPanel;

import book.twju.timeline.model.Item;
import book.twju.timeline.swing.AutoUpdate;
import book.twju.timeline.swing.Header;
import book.twju.timeline.swing.ItemViewer;
import book.twju.timeline.swing.SwingTimelineCompound;

class SwingTimelineCompoundHelper {

  @SuppressWarnings("unchecked")
  static Header<Item> stubHeader() {
    Header<Item> result = mock( Header.class );
    when( result.getComponent() ).thenReturn( new JPanel() );
    return result;
  }
  
  @SuppressWarnings("unchecked")
  static ItemViewer<Item> stubItemViewer() {
    ItemViewer<Item> result = mock( ItemViewer.class );
    when( result.getComponent() ).thenReturn( new JPanel() );
    return result;
  }
  
  @SuppressWarnings("unchecked")
  static AutoUpdate<Item> stubAutoUpdate() {
    return mock( AutoUpdate.class );
  }

  @SuppressWarnings("unchecked")
  static SwingTimelineCompound<Item> stubCompound(
    Header<Item> header , ItemViewer<Item> itemViewer , AutoUpdate<Item> autoUpdate )
  {
    SwingTimelineCompound<Item> result = mock( SwingTimelineCompound.class );
    when( result.getAutoUpdate() ).thenReturn( autoUpdate );
    when( result.getItemViewer() ).thenReturn( itemViewer );
    when( result.getHeader() ).thenReturn( header );
    return result;
  }
}