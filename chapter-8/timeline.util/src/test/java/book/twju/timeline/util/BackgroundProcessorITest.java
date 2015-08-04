package book.twju.timeline.util;

import static book.twju.timeline.test.util.ThreadHelper.sleep;
import static book.twju.timeline.test.util.ThrowableCaptor.thrownBy;
import static book.twju.timeline.util.BackgroundProcessor.RUNNABLE_MUST_NOT_BE_NULL;
import static java.lang.Thread.currentThread;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class BackgroundProcessorITest {
  
  private UiThreadDispatcher uiThreadDispatcher;
  private BackgroundProcessor backgroundProcessor;
  private volatile Thread backgroundThread;

  @Before
  public void setUp() {
    uiThreadDispatcher = mock( UiThreadDispatcher.class );
    backgroundProcessor = new BackgroundProcessor( uiThreadDispatcher );
  }
  
  @Test
  public void process() {
    backgroundProcessor.process( () -> executeInBackground() );
    
    sleep( 100 );
    
    assertThat( backgroundThread ).isNotSameAs( currentThread() );
  }
  
  @Test
  public void processWithNullAsRunnable() {
    Throwable actual = thrownBy( () -> backgroundProcessor.process( null ) );
    
    assertThat( actual )
      .hasMessage( RUNNABLE_MUST_NOT_BE_NULL )
      .isInstanceOf( IllegalArgumentException.class );
  }
  
  @Test
  public void processWithRuntimeProblem() {
    ArgumentCaptor<Runnable> captor = forClass( Runnable.class );
    RuntimeException expected = new RuntimeException();
    
    backgroundProcessor.process( () -> { throw expected; } );
    sleep( 100 );
    
    verify( uiThreadDispatcher ).dispatch( captor.capture() );
    assertThat( thrownBy( () -> captor.getValue().run() ) ).isSameAs( expected );
  }
  
  @Test
  public void processWithError() {
    ArgumentCaptor<Runnable> captor = forClass( Runnable.class );
    Error expected = new Error();
    
    backgroundProcessor.process( () -> { throw expected; } );
    sleep( 100 );
    
    verify( uiThreadDispatcher ).dispatch( captor.capture() );
    assertThat( thrownBy( () -> captor.getValue().run() ) ).isSameAs( expected );
  }

  private void executeInBackground() {
    backgroundThread = Thread.currentThread();
  }
}