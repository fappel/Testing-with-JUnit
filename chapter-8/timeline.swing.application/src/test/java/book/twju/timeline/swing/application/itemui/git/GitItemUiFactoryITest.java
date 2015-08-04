package book.twju.timeline.swing.application.itemui.git;

import static book.twju.timeline.swing.application.itemui.git.GitItemUiFactory.INDEX_MUST_NOT_BE_NEGATIVE;
import static book.twju.timeline.swing.application.itemui.git.GitItemUiFactory.ITEM_MUST_NOT_BE_NULL;
import static book.twju.timeline.swing.application.itemui.git.GitItemUiFactory.UI_CONTEXT_MUST_NOT_BE_NULL;
import static book.twju.timeline.test.util.ThrowableCaptor.thrownBy;
import static java.lang.System.currentTimeMillis;
import static org.assertj.core.api.Assertions.assertThat;

import javax.swing.JPanel;

import org.junit.Before;
import org.junit.Test;

import book.twju.timeline.provider.git.GitItem;
import book.twju.timeline.swing.SwingItemUi;

public class GitItemUiFactoryITest {

  private static final int INDEX = 0;
  
  private GitItemUiFactory factory;
  private JPanel uiContext;
  private GitItem gitItem;

  @Before
  public void setUp() {
    factory = new GitItemUiFactory();
    uiContext = new JPanel();
    gitItem = new GitItem( "id", currentTimeMillis(), "author", "content" );
  }
  
  @Test
  public void create() {
    SwingItemUi<GitItem> actual = ( SwingItemUi<GitItem> )factory.create( uiContext, gitItem, INDEX );
    
    assertThat( actual.getComponent() ).isSameAs( uiContext.getComponent( INDEX ) );
  }
  
  @Test
  public void createWithNullAsUiContext() {
    Throwable actual = thrownBy( () -> factory.create( null, gitItem, INDEX ) );
    
    assertThat( actual )
      .hasMessage( UI_CONTEXT_MUST_NOT_BE_NULL )
      .isInstanceOf( IllegalArgumentException.class );
  }
  
  @Test
  public void createWithNullAsItem() {
    Throwable actual = thrownBy( () -> factory.create( uiContext, null, INDEX ) );
    
    assertThat( actual )
      .hasMessage( ITEM_MUST_NOT_BE_NULL )
      .isInstanceOf( IllegalArgumentException.class );
  }
  
  @Test
  public void createWithNegativeIndex() {
    Throwable actual = thrownBy( () -> factory.create( uiContext, gitItem, -1 ) );
    
    assertThat( actual )
      .hasMessage( INDEX_MUST_NOT_BE_NEGATIVE )
      .isInstanceOf( IllegalArgumentException.class );
  }
}