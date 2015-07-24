package book.twju.chapter_6;

import org.junit.Rule;
import org.junit.Test;

public class MyRuleBasedOnExternalResourceTest {
 
  @Rule
  public MyRuleBasedOnExternalResource myRule = new MyRuleBasedOnExternalResource();
     
  @Test
  public void testRun() {
    System.out.println( "during" );
  }
}