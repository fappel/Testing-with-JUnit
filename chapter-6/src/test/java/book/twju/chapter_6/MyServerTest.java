package book.twju.chapter_6;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class MyServerTest {
  
  @Rule
  public TestName name = new TestName();
  
  @Test
  public void runFirstServerTest() {
    System.out.println( name.getMethodName() );
  }
  
  @Test
  public void runSecondServerTest() {
    System.out.println( name.getMethodName() );
  }
}