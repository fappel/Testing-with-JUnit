package book.twju.chapter_6;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.util.Scanner;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

public class ProvideSystemInputExample {
  
  private static final String INPUT = "input";
  
  @Rule
  public final TextFromStandardInputStream systemInRule
    = TextFromStandardInputStream.emptyStandardInputStream();
  
  @Test
  public void stubInput() {
    systemInRule.provideLines( INPUT );
  
    assertEquals( INPUT, readLine( System.in ) );
  }

  @SuppressWarnings("resource")
  private String readLine( InputStream inputstream ) {
    return new Scanner( inputstream ).nextLine();
  }
}