package book.twju.chapter_6;

import org.junit.rules.ExternalResource;


public class MyRuleBasedOnExternalResource extends ExternalResource {
    
  @Override
  protected void before() {
    System.out.println( "before" );
  }
  
  @Override
  protected void after() {
    System.out.println( "after" );
  }
}
