package book.twju.timeline.swing;

import static book.twju.timeline.util.Conditions.checkArgument;
import static java.util.concurrent.Executors.defaultThreadFactory;

import java.util.concurrent.ThreadFactory;

class BackgroundProcessor {

  static final String RUNNABLE_MUST_NOT_BE_NULL = "Argument 'runnable' must not be null.";
  
  private final UiThreadDispatcher uiThreadDispatcher;
  private final ThreadFactory threadFactory;

  BackgroundProcessor() {
    this( new UiThreadDispatcher() );
  }
  
  BackgroundProcessor( UiThreadDispatcher uiThreadDispatcher  ) {
    this.threadFactory = defaultThreadFactory();
    this.uiThreadDispatcher = uiThreadDispatcher;
  }

  void process( Runnable runnable ) {
    checkArgument( runnable != null, RUNNABLE_MUST_NOT_BE_NULL );
    
    Thread handler = threadFactory.newThread( runnable );
    handler.setUncaughtExceptionHandler( ( thread, throwable ) -> dispatchToUiThread( throwable ) );
    handler.start();
  }
  
  void dispatchToUiThread( Runnable runnable ) {
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