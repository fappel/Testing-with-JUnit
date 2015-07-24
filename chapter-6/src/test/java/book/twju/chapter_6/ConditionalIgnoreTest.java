package book.twju.chapter_6;

import org.junit.Rule;
import org.junit.Test;

import book.twju.chapter_6.ConditionalIgnoreRule.ConditionalIgnore;

public class ConditionalIgnoreTest {

  @Rule
  public ConditionalIgnoreRule rule = new ConditionalIgnoreRule();
 
  @Test
  @ConditionalIgnore( condition = NotRunningOnWindows.class )
  public void focus() {
    // ...
  }
}