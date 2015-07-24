package book.twju.chapter_6;

import org.junit.runners.model.Statement;

class MyStatement extends Statement {
  
  private final Statement base;
  
  MyStatement( Statement base ) {
    this.base = base;
  }
  
  @Override
  public void evaluate() throws Throwable {
    System.out.println( "before" );
    try {
      base.evaluate();
    } finally {
      System.out.println( "after" );
    }
  }
}