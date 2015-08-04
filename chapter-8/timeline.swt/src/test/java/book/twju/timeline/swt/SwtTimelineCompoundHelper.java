package book.twju.timeline.swt;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import book.twju.timeline.model.Item;
import book.twju.timeline.ui.ItemViewer;

class SwtTimelineCompoundHelper {

  @SuppressWarnings("unchecked")
  static Header<Item> stubHeader( Composite parent ) {
    Header<Item> result = mock( Header.class );
    when( result.getControl() ).thenReturn( new Composite( parent, SWT.NONE ) );
    return result;
  }
  
  @SuppressWarnings("unchecked")
  static ItemViewer<Item, Composite> stubItemViewer( Composite parent) {
    ItemViewer<Item, Composite> result = mock( ItemViewer.class );
    when( result.getUiRoot() ).thenReturn( new Composite( parent, SWT.NONE ) );
    return result;
  }
  
  @SuppressWarnings("unchecked")
  static SwtAutoUpdate<Item> stubAutoUpdate() {
    return mock( SwtAutoUpdate.class );
  }

  @SuppressWarnings("unchecked")
  static SwtTimelineCompound<Item> stubCompound(
    Header<Item> header, ItemViewer<Item, Composite> itemViewer, SwtAutoUpdate<Item> autoUpdate )
  {
    SwtTimelineCompound<Item> result = mock( SwtTimelineCompound.class );
    when( result.getAutoUpdate() ).thenReturn( autoUpdate );
    when( result.getItemViewer() ).thenReturn( itemViewer );
    when( result.getHeader() ).thenReturn( header );
    return result;
  }
}