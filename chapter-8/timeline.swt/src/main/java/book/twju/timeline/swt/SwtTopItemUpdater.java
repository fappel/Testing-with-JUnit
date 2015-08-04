package book.twju.timeline.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ScrollBar;

import book.twju.timeline.model.Item;
import book.twju.timeline.model.Timeline;
import book.twju.timeline.ui.ItemUi;
import book.twju.timeline.ui.ItemUiMap;
import book.twju.timeline.ui.TopItemUpdater;
import book.twju.timeline.util.UiThreadDispatcher;

public class SwtTopItemUpdater<T extends Item> extends TopItemUpdater<T, Composite> {

  static final int TOP_OFF_SET = 5;
  
  private final UiThreadDispatcher uiThreadDispatcher;
  private final SwtItemUiList<T> itemUiList;

  SwtTopItemUpdater( Timeline<T> timeline, ItemUiMap<T, Composite> itemUiMap, SwtItemUiList<T> itemUiList ) {
    this( timeline, itemUiMap, itemUiList, new SwtUiThreadDispatcher() );
  }
  
  SwtTopItemUpdater( 
    Timeline<T> timeline, ItemUiMap<T, Composite> map, SwtItemUiList<T> list, UiThreadDispatcher dispatcher )
  {
    super( timeline, map );
    this.uiThreadDispatcher = dispatcher;
    this.itemUiList = list;
  }

  @Override
  public void register() {
    ScrollBar verticalBar = itemUiList.getUiRoot().getVerticalBar();
    uiThreadDispatcher.dispatch( () -> verticalBar.addListener( SWT.Selection, evt -> update() ) );
  }

  @Override
  protected boolean isBelowTop( ItemUi<T> itemUi ) {
    Control control = ( ( SwtItemUi<T> )itemUi ).getControl();
    if( control.isVisible() ) {
      return isBelowTop( control );
    }
    return false;
  }

  private boolean isBelowTop( Control control ) {
    Composite root = itemUiList.getUiRoot();
    int y = control.getDisplay().map( control.getParent(), root, control.getLocation() ).y;
    return y + TOP_OFF_SET >= 0;
  }
}