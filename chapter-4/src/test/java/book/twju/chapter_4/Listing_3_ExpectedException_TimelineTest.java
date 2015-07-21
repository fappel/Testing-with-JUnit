package book.twju.chapter_4;

import static book.twju.chapter_4.Timeline.FETCH_COUNT_LOWER_BOUND;
import static book.twju.chapter_4.Timeline.FETCH_COUNT_UPPER_BOUND;
import static java.lang.String.valueOf;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class Listing_3_ExpectedException_TimelineTest {
  
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private Timeline timeline;

  @Before
  public void setUp() {
    timeline = new Timeline( mock( ItemProvider.class ),
                             mock( SessionStorage.class ) );
  }
  
  @Test
  public void setFetchCountExceedsUpperBound() {
    int tooLargeValue = FETCH_COUNT_UPPER_BOUND + 1;
    thrown.expect( IllegalArgumentException.class );
    thrown.expectMessage( valueOf( tooLargeValue ) );
    
    timeline.setFetchCount( tooLargeValue );
  }
  
  @Test
  public void setFetchCountExceedsLowerBound() {
    int tooSmallValue = FETCH_COUNT_LOWER_BOUND - 1;
    thrown.expect( IllegalArgumentException.class );
    thrown.expectMessage( valueOf( tooSmallValue ) );
    
    timeline.setFetchCount( tooSmallValue );
  }
  
  @Test( expected = IllegalArgumentException.class )
  public void constructWithNullAsItemProvider() {
    new Timeline( null, mock( SessionStorage.class ) );
  }
  
  @Test( expected = IllegalArgumentException.class )
  public void constructWithNullAsSessionStorage() {
    new Timeline( mock( ItemProvider.class ), null );
  }
}