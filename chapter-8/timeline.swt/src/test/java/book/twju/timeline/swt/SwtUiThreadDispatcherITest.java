package book.twju.timeline.swt;

import static book.twju.timeline.swt.SwtUiThreadDispatcher.RUNNABLE_MUST_NOT_BE_NULL;
import static book.twju.timeline.swt.test.util.DisplayHelper.flushPendingEvents;
import static book.twju.timeline.test.util.ThreadHelper.sleep;
import static book.twju.timeline.test.util.ThrowableCaptor.thrownBy;
import static java.lang.Thread.currentThread;
import static org.assertj.core.api.Assertions.assertThat;

import org.eclipse.swt.SWTException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import book.twju.timeline.swt.test.util.DisplayHelper;
import book.twju.timeline.util.BackgroundProcessor;

public class SwtUiThreadDispatcherITest {
  
  @Rule public final DisplayHelper displayHelper = new DisplayHelper();
  
  private BackgroundProcessor backgroundProcessor;
  private SwtUiThreadDispatcher dispatcher;
  private volatile Thread backgroundThread;
  private volatile Thread uiThread;

  @Before
  public void setUp() {
    displayHelper.ensureDisplay();
    dispatcher = new SwtUiThreadDispatcher();
    backgroundProcessor = new BackgroundProcessor( dispatcher );
  }
  
  @Test
  public void dispatch() {
    backgroundProcessor.process( () -> {
      backgroundThread = Thread.currentThread();
      dispatcher.dispatch( () -> executeInUiThread() );
    } );
    
    sleep( 200 );
    flushPendingEvents();
    
    assertThat( uiThread )
      .isNotNull()
      .isSameAs( currentThread() )
      .isNotSameAs( backgroundThread );
  }
  
  @Test
  public void dispatchIfDisplayIsDisposed() {
    displayHelper.getDisplay().dispose();
    
    Throwable actual = thrownBy( () -> dispatcher.dispatch( () -> { throw new RuntimeException(); } ) );
    
    assertThat( actual ).isNull();
  }
  
  @Test
  public void dispatchWithNullAsRunnable() {
    Throwable actual = thrownBy( () -> dispatcher.dispatch( null ) );
    
    assertThat( actual )
      .hasMessage( RUNNABLE_MUST_NOT_BE_NULL )
      .isInstanceOf( IllegalArgumentException.class );
  }
  
  @Test
  public void constructWithNullAsDisplay() {
    Throwable actual = thrownBy( () -> new SwtUiThreadDispatcher( null ) );
    
    assertThat( actual )
      .hasMessage( SwtUiThreadDispatcher.DISPLAY_MUST_NOT_BE_NULL )
      .isInstanceOf( IllegalArgumentException.class );
  }
  
  @Test
  public void constructInNonUiThread() {
    backgroundProcessor.process( () -> new SwtUiThreadDispatcher() );
    sleep( 200 );
    Throwable actual = thrownBy( () -> flushPendingEvents() );
    
    assertThat( actual )
      .hasMessageContaining( SwtUiThreadDispatcher.DISPLAY_MUST_NOT_BE_NULL )
      .isInstanceOf( SWTException.class )
      .hasCauseInstanceOf( IllegalArgumentException.class );
  }

  private void executeInUiThread() {
    uiThread = Thread.currentThread();
  }
}