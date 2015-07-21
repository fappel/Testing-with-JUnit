package book.twju.chapter_4;

import static book.twju.chapter_4.ThrowableCaptor.thrownBy;
import static book.twju.chapter_4.Timeline.ERROR_EXCEEDS_LOWER_BOUND;
import static book.twju.chapter_4.Timeline.ERROR_EXCEEDS_UPPER_BOUND;
import static book.twju.chapter_4.Timeline.FETCH_COUNT_LOWER_BOUND;
import static book.twju.chapter_4.Timeline.FETCH_COUNT_UPPER_BOUND;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

public class Listing_4_Closure_TimelineTest {

  private Timeline timeline;

  @Before
  public void setUp() {
    timeline = new Timeline( mock( ItemProvider.class ),
                             mock( SessionStorage.class ) );
  }
  
  @Test
  public void setFetchCountExceedsUpperBound() {
    int tooLarge = FETCH_COUNT_UPPER_BOUND + 1;
      
    Throwable actual 
      = thrownBy( ()-> timeline.setFetchCount( tooLarge ) );

    assertNotNull( actual );  
    assertTrue( actual instanceof IllegalArgumentException );
    assertTrue( actual.getMessage().contains( valueOf( tooLarge ) ) );
    assertEquals( format( ERROR_EXCEEDS_UPPER_BOUND, tooLarge ), 
                  actual.getMessage() );
  }
  
  @Test
  public void setFetchCountExceedsLowerBound() {
    int tooSmall = FETCH_COUNT_LOWER_BOUND - 1;
    
    Throwable actual 
      = thrownBy( ()-> timeline.setFetchCount( tooSmall ) );
    
    assertNotNull( actual );  
    assertTrue( actual instanceof IllegalArgumentException );
    assertTrue( actual.getMessage().contains( valueOf( tooSmall ) ) );
    assertEquals( format( ERROR_EXCEEDS_LOWER_BOUND, tooSmall ), 
                  actual.getMessage() );
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