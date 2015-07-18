package book.twju.chapter_2;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class Listing_7_BoundaryConditions_TimelineTest {
  
  private Timeline timeline;
  
  @Before
  public void setUp() {
    timeline = new Timeline();
  }
  
  @Test
  public void setFetchCountExceedsLowerBound() {
    int originalFetchCount = timeline.getFetchCount();
      
    timeline.setFetchCount( Timeline.FETCH_COUNT_LOWER_BOUND - 1 );
      
    assertEquals( originalFetchCount, timeline.getFetchCount() );  
  }
    
  @Test
  public void setFetchCountExceedsUpperBound() {
    int originalFetchCount = timeline.getFetchCount();
      
    timeline.setFetchCount( Timeline.FETCH_COUNT_UPPER_BOUND + 1 );
      
    assertEquals( originalFetchCount, timeline.getFetchCount() );  
  }
}