package book.twju.timeline.swing;

import static book.twju.timeline.swing.UiThreadDispatcher.RUNNABLE_MUST_NOT_BE_NULL;
import static book.twju.timeline.test.util.ThrowableCaptor.thrownBy;
import static java.lang.Thread.currentThread;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import book.twju.timeline.swing.UiThreadDispatcher;

public class UiThreadDispatcherITest {
  
  private UiThreadDispatcher dispatcher;
  private volatile Thread uiThread;

  @Before
  public void setUp() {
    dispatcher = new UiThreadDispatcher();
  }
  
  @Test
  public void dispatch() {
    dispatcher.dispatch( () -> executeInUiThread() );
    
    ThreadHelper.sleep( 100 );
    
    assertThat( uiThread ).isNotSameAs( currentThread() );
  }
  
  @Test
  public void dispatchWithNullAsRunnable() {
    Throwable actual = thrownBy( () -> dispatcher.dispatch( null ) );
    
    assertThat( actual )
      .hasMessage( RUNNABLE_MUST_NOT_BE_NULL )
      .isInstanceOf( IllegalArgumentException.class );
  }

  private void executeInUiThread() {
    uiThread = Thread.currentThread();
  }
}