package book.twju.chapter_6;

import org.junit.Rule;
import org.junit.Test;

public class MyConfigurableRuleTest {
 
  @Rule
  public MyConfigurableRule myConfigurableRule = new MyConfigurableRule();
     
  @Test
  @MyRuleConfiguration( "myConfigurationValue" )
  public void testRun() {
    System.out.println( "during" );
  }
}