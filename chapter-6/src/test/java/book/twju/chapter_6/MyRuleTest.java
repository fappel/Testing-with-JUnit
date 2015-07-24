package book.twju.chapter_6;

import org.junit.Rule;
import org.junit.Test;

public class MyRuleTest {
 
  @Rule
  public MyRule myRule = new MyRule();
     
  @Test
  public void testRun() {
    System.out.println( "during" );
  }
}