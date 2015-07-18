package book.twju.chapter_2;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Listing_1_PhasesComments_TimelineTest {
  
  private final static int NEW_FETCH_COUNT
    = Timeline.DEFAULT_FETCH_COUNT + 1;
  
  @Test
  public void setFetchCount() {
    // (1) setup (arrange, build)
    Timeline timeline = new Timeline();

    // (2) exercise (act, operate)
    timeline.setFetchCount( NEW_FETCH_COUNT );
    
    // (3) verify (assert, check)
    assertEquals( NEW_FETCH_COUNT, timeline.getFetchCount() );
  }
}