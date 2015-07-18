package book.twju.chapter_2;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class Listing_8_BDD_Comments_TimelineTest {
  
  private Timeline timeline;
  
  @Before
  public void setUp() {
    timeline = new Timeline();
  }
  
  @Test
  public void setFetchCountExceedsLowerBound() {
    // given
    int originalFetchCount = timeline.getFetchCount();

    // when
    timeline.setFetchCount( Timeline.FETCH_COUNT_LOWER_BOUND - 1 );

    // then
    assertEquals( originalFetchCount, timeline.getFetchCount() );  
  }
    
  @Test
  public void setFetchCountExceedsUpperBound() {
    // given
    int originalFetchCount = timeline.getFetchCount();

    // when
    timeline.setFetchCount( Timeline.FETCH_COUNT_UPPER_BOUND + 1 );

    // then
    assertEquals( originalFetchCount, timeline.getFetchCount() );  
  }
}