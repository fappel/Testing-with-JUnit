package book.twju.timeline.swing.application.itemui.git;

import static java.lang.System.currentTimeMillis;
import static java.util.Locale.ENGLISH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import book.twju.timeline.provider.git.GitItem;
import book.twju.timeline.swing.application.itemui.git.GitItemUi;

public class GitItemUiITest {
  
  private GitItemUi gitItemUi;
  private GitItem item;

  @Before
  public void setUp() {
    Locale.setDefault( ENGLISH );
    item = spy( new GitItem( "id", currentTimeMillis(), "author", "content" ) );
    gitItemUi = new GitItemUi( item );
  }
  
  @Test
  public void getTime() {
    String actual = gitItemUi.getTime();
    
    assertThat( actual ).contains( "moments" );
  }

  @Test
  public void update() {
    when( item.getTimeStamp() ).thenReturn( fiveMinutesAgo() );
    
    gitItemUi.update();
    String time = gitItemUi.getTime();
    
    assertThat( time ).isEqualTo( "5 minutes ago" );
  }
  
  @Test
  public void getComponent() {
    assertThat( gitItemUi.getComponent() ).isNotNull();
  }

  private long fiveMinutesAgo() {
    return System.currentTimeMillis() - 5 * 1_000 * 60;
  }
}
