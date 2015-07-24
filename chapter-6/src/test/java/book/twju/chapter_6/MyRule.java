package book.twju.chapter_6;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class MyRule implements TestRule {

  @Override
  public Statement apply( Statement base, Description description ) {
    return new MyStatement( base );
  }
}