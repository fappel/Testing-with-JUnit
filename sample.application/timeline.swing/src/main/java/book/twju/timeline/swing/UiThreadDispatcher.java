package book.twju.timeline.swing;

import static book.twju.timeline.util.Conditions.checkArgument;
import static javax.swing.SwingUtilities.invokeLater;

public class UiThreadDispatcher {
  
  static final String RUNNABLE_MUST_NOT_BE_NULL = "Argument 'runnable' must not be null.";

  public void dispatch( Runnable runnable ) {
    checkArgument( runnable != null, RUNNABLE_MUST_NOT_BE_NULL );
    
    invokeLater( runnable );
  }
}