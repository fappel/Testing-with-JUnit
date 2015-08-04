package book.twju.timeline.swing;

import static book.twju.timeline.util.Assertion.checkArgument;

import java.awt.Container;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import book.twju.timeline.model.Item;
import book.twju.timeline.ui.AutoUpdate;
import book.twju.timeline.ui.ItemViewer;

class SwingAutoUpdate<T extends Item> implements AutoUpdate<T, Container> {

  static final String HEADER_MUST_NOT_BE_NULL = "Argument 'header' must not be null.";
  static final String ITEM_VIEWER_MUST_NOT_BE_NULL = "Argument 'itemViewer' must not be null.";
  static final String DELAY_MUST_NOT_BE_NEGATIVE = "Argument 'delay' must not be negative.";

  private final ActionListener itemViewerNotifier;
  private final ActionListener headerNofifier;
  private final Timer timer;

  SwingAutoUpdate( Header<T> header, ItemViewer<T, Container> itemViewer, int delay ) {
    checkArgument( header != null, HEADER_MUST_NOT_BE_NULL );
    checkArgument( itemViewer != null, ITEM_VIEWER_MUST_NOT_BE_NULL );
    checkArgument( delay >= 0, DELAY_MUST_NOT_BE_NEGATIVE );
    
    this.timer = new Timer( delay, null );
    this.itemViewerNotifier = event -> itemViewer.update();
    this.headerNofifier = event -> header.update();
  }

  @Override
  public void start() {
    timer.addActionListener( itemViewerNotifier );
    timer.addActionListener( headerNofifier );
    timer.start();
  }

  @Override
  public void stop() {
    timer.stop();
    timer.removeActionListener( headerNofifier );
    timer.removeActionListener( itemViewerNotifier );
  }
}