package book.twju.timeline.swing;

import static book.twju.timeline.swing.ItemViewerCompoundHelper.stubItemList;
import static book.twju.timeline.swing.ItemViewerCompoundHelper.stubItemViewer;
import static book.twju.timeline.swing.ItemViewerCompoundHelper.stubScroller;
import static book.twju.timeline.swing.ItemViewerCompoundHelper.stubTopItemUpdater;
import static book.twju.timeline.ui.FetchOperation.MORE;
import static book.twju.timeline.ui.FetchOperation.NEW;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Component;

import javax.swing.JScrollPane;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import book.twju.timeline.model.Item;

public class ItemViewerTest {
  
  private TopItemUpdater<Item> topItemUpdater;
  private TopItemScroller<Item> scroller;
  private ItemUiList<Item> itemUiList;
  private ItemViewer<Item> itemViewer;

  @Before
  public void setUp() {
    itemUiList = stubItemList();
    scroller = stubScroller();
    topItemUpdater = stubTopItemUpdater();
    itemViewer = new ItemViewer<>( stubItemViewer( itemUiList, scroller, topItemUpdater ) );
  }

  @Test
  public void createUi() {
    itemViewer.createUi();
    
    verify( itemUiList ).createUi();
  }

  @Test
  public void getComponent() {
    JScrollPane expected = new JScrollPane();
    when( itemUiList.getComponent() ).thenReturn( expected );
    
    Component actual = itemViewer.getComponent();
    
    assertThat( actual ).isSameAs( expected );
  }

  @Test
  public void initializeWithEmptyTimeline() {
    when( itemUiList.isTimelineEmpty() ).thenReturn( true );
    
    itemViewer.initialize();

    InOrder order = inOrder( itemUiList, scroller, topItemUpdater );
    order.verify( itemUiList ).isTimelineEmpty();
    order.verify( itemUiList ).fetch( MORE );
    order.verify( itemUiList ).update();
    order.verify( scroller ).scrollIntoView();
    order.verify( topItemUpdater ).register();
    order.verifyNoMoreInteractions();
  }
  
  @Test
  public void initializeWithNonEmptyTimeline() {
    when( itemUiList.isTimelineEmpty() ).thenReturn( false);
    
    itemViewer.initialize();
    
    InOrder order = inOrder( itemUiList, scroller, topItemUpdater );
    order.verify( itemUiList ).isTimelineEmpty();
    order.verify( itemUiList ).update();
    order.verify( scroller ).scrollIntoView();
    order.verify( topItemUpdater ).register();
    order.verifyNoMoreInteractions();
  }

  @Test
  public void fetchNew() {
    itemViewer.fetchNew();
    
    verify( itemUiList ).fetch( NEW );
  }

  @Test
  public void update() {
    itemViewer.update();
    
    verify( itemUiList ).update();
  }
}
