package book.twju.chapter_6;

import book.twju.chapter_6.ConditionalIgnoreRule.IgnoreCondition;

class NotRunningOnWindows implements IgnoreCondition {
  public boolean isSatisfied() {
    return !System.getProperty( "os.name" ).startsWith( "Windows" );
  }
}