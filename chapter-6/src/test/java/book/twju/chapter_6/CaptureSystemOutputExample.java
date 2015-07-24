package book.twju.chapter_6;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

public class CaptureSystemOutputExample {

  private static final String OUTPUT = "output";
  
  @Rule
  public final SystemOutRule systemOutRule 
    = new SystemOutRule().enableLog().muteForSuccessfulTests();

  @Test
  public void captureSystemOutput() {
    System.out.print( OUTPUT );
    
    assertEquals( OUTPUT, systemOutRule.getLog() );
  }
}