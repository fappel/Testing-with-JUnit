package book.twju.timeline.swing;

import static book.twju.timeline.swing.ThreadHelper.directBackgroundProcessor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import book.twju.timeline.model.Item;
import book.twju.timeline.model.Timeline;

@SuppressWarnings("unchecked")
public class HeaderTest {
  
  private Timeline<Item> timeline;
  private Header<Item> header;

  @Before
  public void setUp() {
    timeline = mock( Timeline.class );
    header = createHeader( timeline, directBackgroundProcessor() );
  }

  @Test
  public void updateIfNewItemIsAvailable() {
    when( timeline.getNewCount() ).thenReturn( 1 );
    
    header.update();
    
    assertThat( header.fetchNew.isVisible() ).isTrue();
  }
  
  @Test
  public void updateIfNoNewItemIsAvailable() {
    when( timeline.getNewCount() ).thenReturn( 0 );

    header.update();
    
    assertThat( header.fetchNew.isVisible() ).isFalse();
  }
  
  @Test
  public void onFetchNewNotification() throws Throwable {
    ArgumentCaptor<ActionEvent> captor = forClass( ActionEvent.class );
    ActionListener listener = mock( ActionListener.class );
    header.onFetchNew( listener );
    
    header.fetchNew.doClick();
    
    verify( listener ).actionPerformed( captor.capture() );
    assertThat( captor.getValue().getSource() ).isSameAs( header.getComponent() );
  }

  private Header<Item> createHeader( Timeline<Item> timeline, BackgroundProcessor backgroundProcessor ) {
    Header<Item> result = new Header<Item>( timeline, backgroundProcessor );
    result.createUi();
    return result;
  }
}