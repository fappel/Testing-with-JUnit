package book.twju.timeline.swt;

import static book.twju.timeline.util.Assertion.checkArgument;

import org.eclipse.swt.widgets.Display;

import book.twju.timeline.util.UiThreadDispatcher;

public class SwtUiThreadDispatcher implements UiThreadDispatcher {
  
  static final String RUNNABLE_MUST_NOT_BE_NULL = "Argument 'runnable' must not be null.";
  static final String DISPLAY_MUST_NOT_BE_NULL = "Argument 'display' must not be null.";
  
  private final Display display;


  public SwtUiThreadDispatcher() {
    this( Display.getCurrent() );
  }
  
  public SwtUiThreadDispatcher( Display display ) {
    checkArgument( display != null, DISPLAY_MUST_NOT_BE_NULL );
    
    this.display = display;
  }

  @Override
  public void dispatch( Runnable runnable ) {
    checkArgument( runnable != null, RUNNABLE_MUST_NOT_BE_NULL );
    
    if( !display.isDisposed() ) {
      display.asyncExec( runnable );
    }
  }
}