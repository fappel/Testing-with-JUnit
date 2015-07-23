package book.twju.chapter_5;

import org.junit.runner.RunWith;

@RunWith( Executor.class )
public class ExecutorSample {
  
  @Execute 
  public void doIt() {}
  
  @Execute
  public void doItWithProblem() {
    throw new RuntimeException( "Bad" );
  }
  
  @Execute
  public void doItWithFailure() { 
    throw new AssertionError( "Invalid" ); 
  }
}