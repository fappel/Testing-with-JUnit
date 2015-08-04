package book.twju.timeline.swt;

import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import book.twju.timeline.model.Item;
import book.twju.timeline.model.Timeline;
import book.twju.timeline.ui.ItemUiMap;
import book.twju.timeline.ui.TopItemScroller;
import book.twju.timeline.util.UiThreadDispatcher;

public class SwtTopItemScroller<T extends Item> implements TopItemScroller<T> {
 
  static final int TOP_POSITION = 5;
  
  private final UiThreadDispatcher uiThreadDispatcher;
  private final ItemUiMap<T, Composite> itemUiMap;
  private final SwtItemUiList<T> itemUiList;
  private final Timeline<T> timeline;

  SwtTopItemScroller( Timeline<T> timeline, ItemUiMap<T, Composite> itemUiMap, SwtItemUiList<T> itemUiList ) {
    this( timeline, itemUiMap, itemUiList, new SwtUiThreadDispatcher() );
  }
  
  SwtTopItemScroller( 
    Timeline<T> timeline, ItemUiMap<T, Composite> map, SwtItemUiList<T> list, UiThreadDispatcher dispatcher )
  {
    this.uiThreadDispatcher = dispatcher;
    this.itemUiList = list;
    this.itemUiMap = map;
    this.timeline = timeline;
  }

  public void scrollIntoView() {
    uiThreadDispatcher.dispatch( () -> doScrollIntoView() );
  }
  
  public void doScrollIntoView() {
    if( timeline.getTopItem().isPresent() ) {
      SwtItemUi<T> itemUi = getItemUi();
      if( itemUi != null ) {
        Control control = itemUi.getControl();
        if( control.isVisible() ) {
          updateVerticalScrollbarSelection( control );
        }
      }
    }
  }
  
  void setScrollbarSelection( int newValue ) {
    getContent().getVerticalBar().setSelection( newValue );
    ( ( ScrolledComposite )getContent() ).setOrigin( 0, newValue );
  }

  private SwtItemUi<T> getItemUi() {
    T item = timeline.getTopItem().get();
    return ( SwtItemUi<T> )itemUiMap.findByItemId( item.getId() );
  }

  private void updateVerticalScrollbarSelection( Control control ) {
    int verticalPosition = computeVerticalPosition( control );
    if( verticalPosition > TOP_POSITION ) {
      updateVerticalScrollbarSelection( verticalPosition );
    }
  }

  private void updateVerticalScrollbarSelection( int verticalPosition ) {
    int oldValue = getContent().getVerticalBar().getSelection();
    setScrollbarSelection( oldValue - TOP_POSITION + verticalPosition );
  }

  private int computeVerticalPosition( Control control ) {
    Composite root = itemUiList.getUiRoot();
    return control.getDisplay().map( control.getParent(), root, control.getLocation() ).y;
  }

  private Composite getContent() {
    return ( Composite )itemUiList.getUiRoot();
  }
}