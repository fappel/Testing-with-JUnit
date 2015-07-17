package book.twju.chapter_1;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TimelineTest {
  
  @Test
  public void setFetchCount() {
    Timeline timeline = new Timeline();
    int expected = 5;

    timeline.setFetchCount( expected );
    int actual = timeline.getFetchCount();

    assertEquals( expected, actual );
  }
}