package book.twju.timeline.swing;

import static book.twju.timeline.swing.SwingTimelineCompound.ITEM_PROVIDER_MUST_NOT_BE_NULL;
import static book.twju.timeline.swing.SwingTimelineCompound.ITEM_UI_FACTORY_MUST_NOT_BE_NULL;
import static book.twju.timeline.swing.SwingTimelineCompound.SESSION_STORAGE_MUST_NOT_BE_NULL;
import static book.twju.timeline.test.util.ThrowableCaptor.thrownBy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Container;

import org.junit.Before;
import org.junit.Test;

import book.twju.timeline.model.Item;
import book.twju.timeline.model.ItemProvider;
import book.twju.timeline.model.Memento;
import book.twju.timeline.model.SessionStorage;
import book.twju.timeline.ui.ItemUiFactory;
import book.twju.timeline.util.BackgroundProcessor;

public class SwingTimelineCompoundTest {

  private ItemUiFactory<Item, Container> itemUiFactory;
  private SwingTimelineCompound<Item> compound;
  private SessionStorage<Item> sessionStorage;
  private ItemProvider<Item> itemProvider;
  
  @Before
  @SuppressWarnings("unchecked")
  public void setUp() {
    itemProvider = mock( ItemProvider.class );
    itemUiFactory = mock( ItemUiFactory.class );
    sessionStorage = stubSessionStorage();
    compound = new SwingTimelineCompound<>( itemProvider, itemUiFactory, sessionStorage );
  }

  @Test
  public void getItemViewer() {
    assertThat( compound.getItemViewer() ).isNotNull();
  }

  @Test
  public void getHeader() {
    assertThat( compound.getHeader() ).isNotNull();
  }

  @Test
  public void getAutoUpdate() {
    assertThat( compound.getAutoUpdate() ).isNotNull();
  }
  
  @Test
  public void constructWithNullAsItemProvider() {
    Throwable actual = thrownBy( () -> new SwingTimelineCompound<>( null, itemUiFactory, sessionStorage ) );
    
    assertThat( actual )
      .hasMessage( ITEM_PROVIDER_MUST_NOT_BE_NULL )
      .isInstanceOf( IllegalArgumentException.class );
  }
  
  @Test
  public void constructWithNullAsItemUiFactory() {
    Throwable actual = thrownBy( () -> new SwingTimelineCompound<>( itemProvider, null, sessionStorage ) );
    
    assertThat( actual )
      .hasMessage( ITEM_UI_FACTORY_MUST_NOT_BE_NULL )
      .isInstanceOf( IllegalArgumentException.class );
  }
  
  @Test
  public void constructWithNullAsSessionStorage() {
    Throwable actual = thrownBy( () -> new SwingTimelineCompound<>( itemProvider, itemUiFactory, null ) );
    
    assertThat( actual )
      .hasMessage( SESSION_STORAGE_MUST_NOT_BE_NULL )
      .isInstanceOf( IllegalArgumentException.class );
  }

  @SuppressWarnings("rawtypes")
  private static SessionStorage stubSessionStorage() {
    SessionStorage result = mock( SessionStorage.class );
    when( result.read() ).thenReturn( Memento.empty() );
    return result;
  }

  @Test
  public void createBackgroundProcessor() {
    BackgroundProcessor actual = SwingTimelineCompound.createBackgroundProcessor();
    
    assertThat( actual ).isNotNull();
  }
}