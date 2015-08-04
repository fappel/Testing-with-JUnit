package book.twju.timeline.swing;

import java.awt.Component;
import java.awt.Container;

import javax.swing.BoundedRangeModel;
import javax.swing.JScrollPane;

import book.twju.timeline.model.Item;
import book.twju.timeline.model.Timeline;
import book.twju.timeline.ui.ItemUi;
import book.twju.timeline.ui.ItemUiMap;
import book.twju.timeline.ui.TopItemUpdater;
import book.twju.timeline.util.UiThreadDispatcher;

class SwingTopItemUpdater<T extends Item> extends TopItemUpdater<T, Container> {
  
  private final UiThreadDispatcher uiThreadDispatcher;
  private final SwingItemUiList<T> itemUiList;

  SwingTopItemUpdater( Timeline<T> timeline, ItemUiMap<T, Container> itemUiMap, SwingItemUiList<T> itemUiList ) {
    this( timeline, itemUiMap, itemUiList, new SwingUiThreadDispatcher() );
  }
  
  SwingTopItemUpdater( 
    Timeline<T> timeline, ItemUiMap<T, Container> map, SwingItemUiList<T> list, UiThreadDispatcher dispatcher )
  {
    super( timeline, map );
    this.uiThreadDispatcher = dispatcher;
    this.itemUiList = list;
  }

  @Override
  public void register() {
    JScrollPane jScrollPane = ( JScrollPane )itemUiList.getUiRoot();
    BoundedRangeModel model = jScrollPane.getVerticalScrollBar().getModel();
    uiThreadDispatcher.dispatch( () -> model.addChangeListener( evt -> update() ) );
  }
    
  @Override
  protected boolean isBelowTop( ItemUi<T> itemUi ) {
    Component component = ( ( SwingItemUi<T> )itemUi ).getComponent();
    if( component.isShowing() ) {
      return isBelowTop( component );
    }
    return false;
  }

  private boolean isBelowTop( Component component ) {
    double y1 = itemUiList.getUiRoot().getLocationOnScreen().getY();
    double y2 = component.getLocationOnScreen().getY();
    return y2 - y1 >= 0;
  }
}