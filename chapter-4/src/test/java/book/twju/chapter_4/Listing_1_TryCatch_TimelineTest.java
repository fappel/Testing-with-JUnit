package book.twju.chapter_4;

import static java.lang.String.format;
import static java.lang.String.valueOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

public class Listing_1_TryCatch_TimelineTest {
  
  private Timeline timeline;

  @Before
  public void setUp() {
    timeline = new Timeline( mock( ItemProvider.class ),
                             mock( SessionStorage.class ) );
  }
  
  @Test
  public void setFetchCountExceedsLowerBound() {
    int tooSmall = Timeline.FETCH_COUNT_LOWER_BOUND - 1;

    try {
      timeline.setFetchCount( tooSmall );
      fail();
    } catch( IllegalArgumentException actual ) {
      String message = actual.getMessage();
      String expected = format( Timeline.ERROR_EXCEEDS_LOWER_BOUND,
                                tooSmall );
      assertEquals( expected, message );
      assertTrue( message.contains( valueOf( tooSmall ) ) );
    }
  }
  
  @Test
  public void setFetchCountExceedsUpperBound() {
    int tooLarge = Timeline.FETCH_COUNT_UPPER_BOUND + 1;
    
    try {
      timeline.setFetchCount( tooLarge );
      fail();
    } catch( IllegalArgumentException actual ) {
      String message = actual.getMessage();
      String expected = format( Timeline.ERROR_EXCEEDS_UPPER_BOUND,
                                tooLarge );
      assertEquals( expected, message );
      assertTrue( message.contains( valueOf( tooLarge ) ) );
    }
  }
}