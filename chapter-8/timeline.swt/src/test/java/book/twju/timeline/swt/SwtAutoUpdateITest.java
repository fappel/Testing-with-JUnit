package book.twju.timeline.swt;

import static book.twju.timeline.swt.SwtAutoUpdate.DELAY_MUST_NOT_BE_NEGATIVE;
import static book.twju.timeline.swt.SwtAutoUpdate.HEADER_MUST_NOT_BE_NULL;
import static book.twju.timeline.swt.SwtAutoUpdate.ITEM_VIEWER_MUST_NOT_BE_NULL;
import static book.twju.timeline.swt.SwtTimelineCompoundHelper.stubHeader;
import static book.twju.timeline.swt.SwtTimelineCompoundHelper.stubItemViewer;
import static book.twju.timeline.swt.test.util.DisplayHelper.flushPendingEvents;
import static book.twju.timeline.test.util.ThreadHelper.sleep;
import static book.twju.timeline.test.util.ThrowableCaptor.thrownBy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import book.twju.timeline.model.Item;
import book.twju.timeline.swt.test.util.DisplayHelper;
import book.twju.timeline.ui.ItemViewer;

public class SwtAutoUpdateITest {
  
  private static final int DELAY = 10;
  
  @Rule
  public final DisplayHelper displayHelper = new DisplayHelper();
  
  private SwtAutoUpdate<Item> autoUpdate;
  private ItemViewer<Item, Composite> itemViewer;
  private Header<Item> header;

  @Before
  public void setUp() {
    Shell parent = displayHelper.createShell();
    header = stubHeader( parent );
    itemViewer = stubItemViewer( parent );
    autoUpdate = new SwtAutoUpdate<>( header, itemViewer, DELAY );
  }
  
  @After
  public void tearDown() {
    autoUpdate.stop();
  }
  
  @Test
  public void start() {
    autoUpdate.start();
    
    waitForDelay();
    
    verify( header, atLeastOnce() ).update();
    verify( itemViewer, atLeastOnce() ).update();
  }
  
  @Test
  public void stop() {
    autoUpdate.start();
    waitForDelay();

    autoUpdate.stop();
    reset( header, itemViewer );
    waitForDelay();
    
    verify( header, never() ).update();
    verify( itemViewer, never() ).update();
  }

  @Test
  public void constructWithNullAsHeader() {
    Throwable actual = thrownBy( () -> new SwtAutoUpdate<>( null, itemViewer, DELAY ) );
    
    assertThat( actual )
      .hasMessage( HEADER_MUST_NOT_BE_NULL )
      .isInstanceOf( IllegalArgumentException.class );
  }
  
  @Test
  public void constructWithNullAsItemViewer() {
    Throwable actual = thrownBy( () -> new SwtAutoUpdate<>( header, null, DELAY ) );
    
    assertThat( actual )
      .hasMessage( ITEM_VIEWER_MUST_NOT_BE_NULL )
      .isInstanceOf( IllegalArgumentException.class );     
  }
  
  @Test
  public void constructWithNegativeDelay() {
    Throwable actual = thrownBy( () -> new SwtAutoUpdate<>( header, itemViewer, -1 ) );
    
    assertThat( actual )
      .hasMessage( DELAY_MUST_NOT_BE_NEGATIVE )
      .isInstanceOf( IllegalArgumentException.class );
  }
  
  private void waitForDelay() {
    sleep( DELAY );
    flushPendingEvents();
  }
}