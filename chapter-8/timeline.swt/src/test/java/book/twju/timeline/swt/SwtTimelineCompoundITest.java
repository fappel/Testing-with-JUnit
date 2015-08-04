package book.twju.timeline.swt;

import static book.twju.timeline.swt.SwtTimelineCompound.ITEM_PROVIDER_MUST_NOT_BE_NULL;
import static book.twju.timeline.swt.SwtTimelineCompound.ITEM_UI_FACTORY_MUST_NOT_BE_NULL;
import static book.twju.timeline.swt.SwtTimelineCompound.SESSION_STORAGE_MUST_NOT_BE_NULL;
import static book.twju.timeline.test.util.ThrowableCaptor.thrownBy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.swt.widgets.Composite;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import book.twju.timeline.model.Item;
import book.twju.timeline.model.ItemProvider;
import book.twju.timeline.model.Memento;
import book.twju.timeline.model.SessionStorage;
import book.twju.timeline.swt.test.util.DisplayHelper;
import book.twju.timeline.ui.ItemUiFactory;
import book.twju.timeline.util.BackgroundProcessor;

public class SwtTimelineCompoundITest {
  
  @Rule
  public final DisplayHelper displayHelper = new DisplayHelper();

  private ItemUiFactory<Item, Composite> itemUiFactory;
  private SwtTimelineCompound<Item> compound;
  private SessionStorage<Item> sessionStorage;
  private ItemProvider<Item> itemProvider;
  
  @Before
  @SuppressWarnings("unchecked")
  public void setUp() {
    displayHelper.ensureDisplay();
    itemProvider = mock( ItemProvider.class );
    itemUiFactory = mock( ItemUiFactory.class );
    sessionStorage = stubSessionStorage();
    compound = new SwtTimelineCompound<>( itemProvider, itemUiFactory, sessionStorage );
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
    Throwable actual = thrownBy( () -> new SwtTimelineCompound<>( null, itemUiFactory, sessionStorage ) );
    
    assertThat( actual )
      .hasMessage( ITEM_PROVIDER_MUST_NOT_BE_NULL )
      .isInstanceOf( IllegalArgumentException.class );
  }
  
  @Test
  public void constructWithNullAsItemUiFactory() {
    Throwable actual = thrownBy( () -> new SwtTimelineCompound<>( itemProvider, null, sessionStorage ) );
    
    assertThat( actual )
      .hasMessage( ITEM_UI_FACTORY_MUST_NOT_BE_NULL )
      .isInstanceOf( IllegalArgumentException.class );
  }
  
  @Test
  public void constructWithNullAsSessionStorage() {
    Throwable actual = thrownBy( () -> new SwtTimelineCompound<>( itemProvider, itemUiFactory, null ) );
    
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
    BackgroundProcessor actual = SwtTimelineCompound.createBackgroundProcessor();
    
    assertThat( actual ).isNotNull();
  }
}