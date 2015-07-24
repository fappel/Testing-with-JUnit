package book.twju.chapter_6;

import org.junit.runner.Description;
import org.junit.runners.model.Statement;

class MyConfigurableStatement extends Statement {
  
  private final Description description;
  private final Statement base;
  
  MyConfigurableStatement( Statement base, Description description ) {
    this.description = description;
    this.base = base;
  }
  
  @Override
  public void evaluate() throws Throwable {
    String configuration = getConfiguration();
    System.out.println( "before [" + configuration + "]" );
    try {
      base.evaluate();
    } finally {
      System.out.println( "after [" + configuration + "]" );
    }
  }

  private String getConfiguration() {
    return description
      .getAnnotation( MyRuleConfiguration.class )
      .value();
  }
}