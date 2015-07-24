package book.twju.chapter_6;

import org.junit.Assume;
import org.junit.Test;

public class AssumptionTest {
  
  @Test
  public void ignored() {
    Assume.assumeFalse( true );
    // statements below this line are skipped
  }
  
  @Test
  public void executed() {
    Assume.assumeTrue( true );
    // statements below this line are executed
  }
}