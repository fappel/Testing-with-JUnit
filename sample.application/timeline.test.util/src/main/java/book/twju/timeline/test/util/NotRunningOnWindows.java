package book.twju.timeline.test.util;

import book.twju.timeline.test.util.ConditionalIgnoreRule.IgnoreCondition;

public class NotRunningOnWindows implements IgnoreCondition {
  public boolean isSatisfied() {
    return !System.getProperty( "os.name" ).startsWith( "Windows" );
  }
}