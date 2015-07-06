package book.twju.timeline.swing;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import org.mockito.invocation.InvocationOnMock;

import book.twju.timeline.swing.BackgroundProcessor;
import book.twju.timeline.swing.UiThreadDispatcher;

class ThreadHelper {
  
  private static BackgroundProcessor directBackgroundProcessor;
  private static UiThreadDispatcher directUiThreadDispatcher;

  static void sleep( int delay ) {
    try {
      Thread.sleep( delay + 100 );
    } catch( InterruptedException shouldNotHappen ) {
      throw new IllegalStateException( shouldNotHappen );
    }
  }
  
  static BackgroundProcessor directBackgroundProcessor() {
    if( directBackgroundProcessor == null ) {
      directBackgroundProcessor = mock( BackgroundProcessor.class );
      doAnswer( invocation -> runDirectly( invocation ) )
        .when( directBackgroundProcessor ).process( any( Runnable.class ) );
      doAnswer( invocation -> runDirectly( invocation ) )
        .when( directBackgroundProcessor ).dispatchToUiThread( any( Runnable.class ) );
    }
    return directBackgroundProcessor;
  }
  
  static UiThreadDispatcher directUiThreadDispatcher() {
    if( directUiThreadDispatcher == null ) {
      directUiThreadDispatcher = mock( UiThreadDispatcher.class );
      doAnswer( invocation -> runDirectly( invocation ) )
        .when( directUiThreadDispatcher ).dispatch( any( Runnable.class ) );
    }
    return directUiThreadDispatcher;
  }
  
  private static Object runDirectly( InvocationOnMock invocation ) {
    Runnable runnable = ( Runnable )invocation.getArguments()[ 0 ];
    runnable.run();
    return null;
  }
}