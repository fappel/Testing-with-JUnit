package book.twju.timeline.swt;

import static book.twju.timeline.swt.test.util.SwtEventHelper.trigger;
import static book.twju.timeline.ui.FetchOperation.MORE;
import static book.twju.timeline.util.test.util.BackgroundThreadHelper.directBackgroundProcessor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InOrder;

import book.twju.timeline.model.Item;
import book.twju.timeline.swt.test.util.DisplayHelper;
import book.twju.timeline.ui.ItemUiMap;
import book.twju.timeline.util.BackgroundProcessor;

public class SwtItemUiListITest {
  
  @Rule
  public final DisplayHelper displayHelper = new DisplayHelper();
  
  private BackgroundProcessor backgroundProcessor;
  private ItemUiMap<Item, Composite> itemUiMap;
  private SwtItemUiList<Item> itemUiList;

  @Before
  public void setUp() {
    itemUiMap = stubItemUiMap();
    backgroundProcessor = directBackgroundProcessor();
    itemUiList = new SwtItemUiList<>( itemUiMap, backgroundProcessor );
  }

  @Test
  public void createUi() {
    itemUiList.createUi( displayHelper.createShell() );
    
    assertThat( itemUiList.getUiRoot() ).isNotNull();
    assertThat( itemUiList.getContent() ).isNotNull();
  }

  @Test
  public void fetchMoreOnClick() {
    itemUiList.createUi( displayHelper.createShell() );
    
    trigger( SWT.Selection ).on( itemUiList.fetchMore );
    
    InOrder order = inOrder( itemUiMap, backgroundProcessor );
    order.verify( itemUiMap ).fetch( MORE );
    order.verify( itemUiMap ).update( itemUiList.content );
  }
  
  @SuppressWarnings("unchecked")
  private ItemUiMap<Item, Composite> stubItemUiMap() {
    return mock( ItemUiMap.class );
  }
}