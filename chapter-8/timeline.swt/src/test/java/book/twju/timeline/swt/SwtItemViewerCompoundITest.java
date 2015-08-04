package book.twju.timeline.swt;

import static book.twju.timeline.swt.SwtItemViewerCompound.ITEM_UI_FACTORY_MUST_NOT_BE_NULL;
import static book.twju.timeline.swt.SwtItemViewerCompound.TIMELINE_MUST_NOT_BE_NULL;
import static book.twju.timeline.test.util.ThrowableCaptor.thrownBy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.eclipse.swt.widgets.Composite;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import book.twju.timeline.model.Item;
import book.twju.timeline.model.Timeline;
import book.twju.timeline.swt.test.util.DisplayHelper;
import book.twju.timeline.ui.ItemUiFactory;

public class SwtItemViewerCompoundITest {

  @Rule
  public final DisplayHelper displayHelper = new DisplayHelper();
  
  private ItemUiFactory<Item, Composite> itemUiFactory;
  private SwtItemViewerCompound<Item> compound;
  private Timeline<Item> timeline;

  @Before
  @SuppressWarnings("unchecked")
  public void setUp() {
    displayHelper.ensureDisplay();
    timeline = mock( Timeline.class );
    itemUiFactory = mock( ItemUiFactory.class );
    compound = new SwtItemViewerCompound<>( timeline, itemUiFactory );
  }
  
  @Test
  public void getTopItemUpdater() {
    assertThat( compound.getTopItemUpdater() ).isNotNull();
  }

  @Test
  public void getScroller() {
    assertThat( compound.getScroller() ).isNotNull();
  }

  @Test
  public void getItemUiList() {
    assertThat( compound.getItemUiList() ).isNotNull();
  }
  
  @Test
  public void constructWithNullAsTimeline() {
    Throwable actual = thrownBy( () -> new SwtItemViewerCompound<>( null, itemUiFactory ) );
    
    assertThat( actual )
      .hasMessage( TIMELINE_MUST_NOT_BE_NULL )
      .isInstanceOf( IllegalArgumentException.class );
  }
  
  @Test
  public void constructWithNullAsItemUiFactory() {
    Throwable actual = thrownBy( () -> new SwtItemViewerCompound<>( timeline, null ) );
    
    assertThat( actual )
      .hasMessage( ITEM_UI_FACTORY_MUST_NOT_BE_NULL )
      .isInstanceOf( IllegalArgumentException.class );
  }
}