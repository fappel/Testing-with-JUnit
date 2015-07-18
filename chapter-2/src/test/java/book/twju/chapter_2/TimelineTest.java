package book.twju.chapter_2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TimelineTest {
  
  private final static int NEW_FETCH_COUNT
    = new Timeline().getFetchCount() + 1;

  private Timeline timeline;

  @Before
  public void setUp() {
    timeline = new Timeline();
  }
  
  @After
  public void tearDown() {
    timeline.dispose();
  }
  
  @Test
  public void setFetchCount() {
    timeline.setFetchCount( NEW_FETCH_COUNT );
    
    assertEquals( NEW_FETCH_COUNT, timeline.getFetchCount() );
  }
  
  @Test
  public void initialState() {
    assertTrue( timeline.getFetchCount() > 0 ); 
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