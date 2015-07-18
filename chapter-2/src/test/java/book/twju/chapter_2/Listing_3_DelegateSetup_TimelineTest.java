package book.twju.chapter_2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class Listing_3_DelegateSetup_TimelineTest {
  
  private final static int NEW_FETCH_COUNT
    = new Timeline().getFetchCount() + 1;
  
  @Test
  public void setFetchCount() {
    Timeline timeline = createTimeline();

    timeline.setFetchCount( NEW_FETCH_COUNT );
    
    assertEquals( NEW_FETCH_COUNT, timeline.getFetchCount() );
  }
  
  @Test
  public void initialState() {
    Timeline timeline = createTimeline();

    assertTrue( timeline.getFetchCount() > 0 ); 
  }

  private static Timeline createTimeline() {
    return new Timeline();
  }
}