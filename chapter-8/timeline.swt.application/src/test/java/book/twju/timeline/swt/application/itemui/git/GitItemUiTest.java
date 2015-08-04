package book.twju.timeline.swt.application.itemui.git;

import static java.lang.System.currentTimeMillis;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import book.twju.timeline.provider.git.GitItem;
import book.twju.timeline.swt.test.util.DisplayHelper;
import book.twju.timeline.util.NiceTime;

public class GitItemUiTest {
  
  private static final int DRAWING_ORDER_TOP = 0;
  private static final int DRAWING_ORDER_SECOND = 1;
  private static final int DRAWING_ORDER_BOTTOM = Integer.MAX_VALUE;
  private static final String SOME_TIME_AGO = "Some time ago";
  private static final String NOW = "now";
  
  @Rule
  public final DisplayHelper displayHelper = new DisplayHelper();
  
  private GitItemUi gitItemUi;
  private NiceTime niceTime;
  private Composite parent;
  private GitItem item;

  @Before
  public void setUp() {
    niceTime = createNiceTime();
    item = new GitItem( "id", currentTimeMillis(), "author", "content" );
    parent = displayHelper.createShell();
    gitItemUi = new GitItemUi( parent, item, 0, niceTime );
  }
  
  @Test
  public void getControl() {
    assertThat( gitItemUi.getControl() ).isNotNull();
  }
  
  @Test
  public void getTime() {
    String actual = gitItemUi.getTime();
    
    assertThat( actual ).isEqualTo( NOW );
  }

  @Test
  public void update() {
    equipNiceTimeFormat( niceTime, SOME_TIME_AGO );
    
    gitItemUi.update();
    String actual = gitItemUi.getTime();
    
    assertThat( actual ).isEqualTo( SOME_TIME_AGO );
  }
  
  @Test
  public void drawingOrderPlacement() {
    Control first = new GitItemUi( parent, item, DRAWING_ORDER_TOP, niceTime ).getControl();
    Control second = new GitItemUi( parent, item, DRAWING_ORDER_SECOND, niceTime ).getControl();
    Control third = gitItemUi.getControl();
    Control fourth = new GitItemUi( parent, item, DRAWING_ORDER_BOTTOM, niceTime ).getControl();
    
    assertThat( parent.getChildren() ).containsExactly( first, second, third, fourth );
  }

  private static NiceTime createNiceTime() {
    NiceTime result = mock( NiceTime.class );
    equipNiceTimeFormat( result, NOW );
    return result;
  }

  private static void equipNiceTimeFormat( NiceTime niceTime, String niceTimeFormat ) {
    when( niceTime.format( any( Date.class ) ) ).thenReturn( niceTimeFormat );
  }
}