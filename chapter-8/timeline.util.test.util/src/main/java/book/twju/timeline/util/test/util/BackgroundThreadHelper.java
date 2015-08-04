package book.twju.timeline.util.test.util;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import org.mockito.invocation.InvocationOnMock;

import book.twju.timeline.util.BackgroundProcessor;
import book.twju.timeline.util.UiThreadDispatcher;

public class BackgroundThreadHelper {
  
  public static BackgroundProcessor directBackgroundProcessor() {
    BackgroundProcessor result = mock( BackgroundProcessor.class );
    doAnswer( invocation -> runDirectly( invocation ) )
      .when( result ).process( any( Runnable.class ) );
    doAnswer( invocation -> runDirectly( invocation ) )
      .when( result ).dispatchToUiThread( any( Runnable.class ) );
    return result;
  }
  
  public static UiThreadDispatcher directUiThreadDispatcher() {
    UiThreadDispatcher result = mock( UiThreadDispatcher.class );
    doAnswer( invocation -> runDirectly( invocation ) )
      .when( result ).dispatch( any( Runnable.class ) );
    return result;
  }
  
  private static Object runDirectly( InvocationOnMock invocation ) {
    Runnable runnable = ( Runnable )invocation.getArguments()[ 0 ];
    runnable.run();
    return null;
  }
}