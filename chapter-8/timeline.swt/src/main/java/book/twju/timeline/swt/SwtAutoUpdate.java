package book.twju.timeline.swt;

import static book.twju.timeline.util.Assertion.checkArgument;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import book.twju.timeline.model.Item;
import book.twju.timeline.ui.AutoUpdate;
import book.twju.timeline.ui.ItemViewer;

class SwtAutoUpdate<T extends Item> implements AutoUpdate<T, Composite> {

  static final String HEADER_MUST_NOT_BE_NULL = "Argument 'header' must not be null.";
  static final String ITEM_VIEWER_MUST_NOT_BE_NULL = "Argument 'itemViewer' must not be null.";
  static final String DELAY_MUST_NOT_BE_NEGATIVE = "Argument 'delay' must not be negative.";

  private final ItemViewer<T, Composite> itemViewer;
  private final Header<T> header;
  private final Display display;
  private final int delay;
  
  private volatile boolean started;

  SwtAutoUpdate( Header<T> header, ItemViewer<T, Composite> itemViewer, int delay ) {
    checkArgument( header != null, HEADER_MUST_NOT_BE_NULL );
    checkArgument( itemViewer != null, ITEM_VIEWER_MUST_NOT_BE_NULL );
    checkArgument( delay >= 0, DELAY_MUST_NOT_BE_NEGATIVE );

    this.display = Display.getCurrent();
    this.itemViewer = itemViewer;
    this.header = header;
    this.delay = delay;
  }

  @Override
  public void start() {
    started = true;
    display.timerExec( delay, () -> triggerUpdate() );
  }

  @Override
  public void stop() {
    started = false;
  }
  
  private void triggerUpdate() {
    if( started ) {
      itemViewer.update();
      header.update();
      start();
    }
  }
}