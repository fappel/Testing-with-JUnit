package book.twju.timeline.swing;

import static book.twju.timeline.swing.SwingAutoUpdate.DELAY_MUST_NOT_BE_NEGATIVE;
import static book.twju.timeline.swing.SwingAutoUpdate.HEADER_MUST_NOT_BE_NULL;
import static book.twju.timeline.swing.SwingAutoUpdate.ITEM_VIEWER_MUST_NOT_BE_NULL;
import static book.twju.timeline.swing.SwingTimelineCompoundHelper.stubHeader;
import static book.twju.timeline.swing.SwingTimelineCompoundHelper.stubItemViewer;
import static book.twju.timeline.test.util.ThreadHelper.sleep;
import static book.twju.timeline.test.util.ThrowableCaptor.thrownBy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

import java.awt.Container;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import book.twju.timeline.model.Item;
import book.twju.timeline.ui.AutoUpdate;
import book.twju.timeline.ui.ItemViewer;

public class SwingAutoUpdateITest {
  
  private static final int DELAY = 0;
  
  private AutoUpdate<Item, Container> autoUpdate;
  private ItemViewer<Item, Container> itemViewer;
  private Header<Item> header;

  @Before
  public void setUp() {
    header = stubHeader();
    itemViewer = stubItemViewer();
    autoUpdate = new SwingAutoUpdate<>( header, itemViewer, DELAY );
  }
  
  @After
  public void tearDown() {
    autoUpdate.stop();
  }
  
  @Test
  public void start() {
    autoUpdate.start();
    
    sleep( DELAY );
    
    verify( header, atLeastOnce() ).update();
    verify( itemViewer, atLeastOnce() ).update();
  }
  
  @Test
  public void stop() {
    autoUpdate.start();
    sleep( DELAY );

    autoUpdate.stop();
    reset( header, itemViewer );
    sleep( DELAY );
    
    verify( header, never() ).update();
    verify( itemViewer, never() ).update();
  }

  @Test
  public void constructWithNullAsHeader() {
    Throwable actual = thrownBy( () -> new SwingAutoUpdate<>( null, itemViewer, DELAY ) );
    
    assertThat( actual )
      .hasMessage( HEADER_MUST_NOT_BE_NULL )
      .isInstanceOf( IllegalArgumentException.class );
  }
  
  @Test
  public void constructWithNullAsItemViewer() {
    Throwable actual = thrownBy( () -> new SwingAutoUpdate<>( header, null, DELAY ) );
    
    assertThat( actual )
      .hasMessage( ITEM_VIEWER_MUST_NOT_BE_NULL )
      .isInstanceOf( IllegalArgumentException.class );     
  }
  
  @Test
  public void constructWithNegativeDelay() {
    Throwable actual = thrownBy( () -> new SwingAutoUpdate<>( header, itemViewer, -1 ) );
    
    assertThat( actual )
      .hasMessage( DELAY_MUST_NOT_BE_NEGATIVE )
      .isInstanceOf( IllegalArgumentException.class );
  }
}