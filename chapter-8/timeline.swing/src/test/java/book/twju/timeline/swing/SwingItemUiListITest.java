package book.twju.timeline.swing;

import static book.twju.timeline.ui.FetchOperation.MORE;
import static book.twju.timeline.util.test.util.BackgroundThreadHelper.directBackgroundProcessor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

import java.awt.Container;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import book.twju.timeline.model.Item;
import book.twju.timeline.ui.ItemUiMap;
import book.twju.timeline.util.BackgroundProcessor;

public class SwingItemUiListITest {
  
  private BackgroundProcessor backgroundProcessor;
  private ItemUiMap<Item, Container> itemUiMap;
  private SwingItemUiList<Item> itemUiList;

  @Before
  public void setUp() {
    itemUiMap = stubItemUiMap();
    backgroundProcessor = directBackgroundProcessor();
    itemUiList = new SwingItemUiList<>( itemUiMap, backgroundProcessor );
  }

  @Test
  public void createUi() {
    itemUiList.createUi( null );
    
    assertThat( itemUiList.getUiRoot() ).isNotNull();
    assertThat( itemUiList.getContent() ).isNotNull();
  }

  @Test
  public void fetchMoreOnClick() {
    itemUiList.createUi( null );
    
    itemUiList.fetchMore.doClick();
    
    InOrder order = inOrder( itemUiMap, backgroundProcessor );
    order.verify( itemUiMap ).fetch( MORE );
    order.verify( itemUiMap ).update( itemUiList.content );
  }
  
  @SuppressWarnings("unchecked")
  private ItemUiMap<Item, Container> stubItemUiMap() {
    return mock( ItemUiMap.class );
  }
}