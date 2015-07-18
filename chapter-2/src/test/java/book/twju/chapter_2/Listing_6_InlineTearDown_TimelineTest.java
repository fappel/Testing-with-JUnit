package book.twju.chapter_2;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Listing_6_InlineTearDown_TimelineTest {
  
  private final static int NEW_FETCH_COUNT
    = new Timeline().getFetchCount() + 1;

  @Test
  public void setFetchCount() {
    Timeline timeline = new Timeline();

    try {
      timeline.setFetchCount( NEW_FETCH_COUNT );
      
      assertEquals( NEW_FETCH_COUNT, timeline.getFetchCount() );
    } finally {
      timeline.dispose();
    }
  }
}