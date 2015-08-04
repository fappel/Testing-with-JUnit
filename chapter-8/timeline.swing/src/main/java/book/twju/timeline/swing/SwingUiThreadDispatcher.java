package book.twju.timeline.swing;

import static book.twju.timeline.util.Assertion.checkArgument;
import static javax.swing.SwingUtilities.invokeLater;
import book.twju.timeline.util.UiThreadDispatcher;

public class SwingUiThreadDispatcher implements UiThreadDispatcher {
  
  static final String RUNNABLE_MUST_NOT_BE_NULL = "Argument 'runnable' must not be null.";

  @Override
  public void dispatch( Runnable runnable ) {
    checkArgument( runnable != null, RUNNABLE_MUST_NOT_BE_NULL );
    
    invokeLater( runnable );
  }
}