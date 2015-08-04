package book.twju.timeline.util;

import static book.twju.timeline.util.Assertion.checkArgument;
import static java.util.concurrent.Executors.defaultThreadFactory;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ThreadFactory;

public class BackgroundProcessor {

  static final String RUNNABLE_MUST_NOT_BE_NULL = "Argument 'runnable' must not be null.";
  
  private final UiThreadDispatcher uiThreadDispatcher;
  private final ThreadFactory threadFactory;
  
  public BackgroundProcessor( UiThreadDispatcher uiThreadDispatcher  ) {
    this.threadFactory = defaultThreadFactory();
    this.uiThreadDispatcher = uiThreadDispatcher;
  }

  public void process( Runnable runnable ) {
    checkArgument( runnable != null, RUNNABLE_MUST_NOT_BE_NULL );
    
    UncaughtExceptionHandler exceptionHandler = ( thread, throwable ) -> dispatchToUiThread( throwable );
    Thread handler = threadFactory.newThread( runnable );
    handler.setUncaughtExceptionHandler( exceptionHandler );
    handler.start();
  }
  
  public void dispatchToUiThread( Runnable runnable ) {
    uiThreadDispatcher.dispatch( runnable );
  }
  
  private void dispatchToUiThread( Throwable problemOnFetch ) {
    dispatchToUiThread( () -> rethrow( problemOnFetch ) );
  }

  private void rethrow( Throwable problemOnFetch ) {
    if( problemOnFetch instanceof Error ) {
      throw ( Error )problemOnFetch;
    }
    throw ( RuntimeException )problemOnFetch;
  }
}