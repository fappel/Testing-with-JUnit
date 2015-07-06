package book.twju.timeline.swing;

import static book.twju.timeline.swing.AutoUpdate.DELAY_MUST_NOT_BE_NEGATIVE;
import static book.twju.timeline.swing.AutoUpdate.HEADER_MUST_NOT_BE_NULL;
import static book.twju.timeline.swing.AutoUpdate.ITEM_VIEWER_MUST_NOT_BE_NULL;
import static book.twju.timeline.swing.SwingTimelineCompoundHelper.stubHeader;
import static book.twju.timeline.swing.ThreadHelper.sleep;
import static book.twju.timeline.test.util.ThrowableCaptor.thrownBy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import book.twju.timeline.model.Item;

public class AutoUpdateITest {
  
  private static final int DELAY = 0;
  
  private AutoUpdate<Item> autoUpdate;
  private ItemViewer<Item> itemViewer;
  private Header<Item> header;

  @Before
  public void setUp() {
    header = stubHeader();
    itemViewer = SwingTimelineCompoundHelper.stubItemViewer();
    autoUpdate = new AutoUpdate<>( header, itemViewer, DELAY );
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
    Throwable actual = thrownBy( () -> new AutoUpdate<>( null, itemViewer, DELAY ) );
    
    assertThat( actual )
      .hasMessage( HEADER_MUST_NOT_BE_NULL )
      .isInstanceOf( IllegalArgumentException.class );
  }
  
  @Test
  public void constructWithNullAsItemViewer() {
    Throwable actual = thrownBy( () -> new AutoUpdate<>( header, null, DELAY ) );
    
    assertThat( actual )
      .hasMessage( ITEM_VIEWER_MUST_NOT_BE_NULL )
      .isInstanceOf( IllegalArgumentException.class );     
  }
  
  @Test
  public void constructWithNegativeDelay() {
    Throwable actual = thrownBy( () -> new AutoUpdate<>( header, itemViewer, -1 ) );
    
    assertThat( actual )
      .hasMessage( DELAY_MUST_NOT_BE_NEGATIVE )
      .isInstanceOf( IllegalArgumentException.class );
  }
}