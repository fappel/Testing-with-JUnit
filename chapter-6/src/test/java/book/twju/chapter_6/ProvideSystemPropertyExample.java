package book.twju.chapter_6;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ProvideSystemProperty;

public class ProvideSystemPropertyExample {
  
  private static final String JAVA_IO_TMPDIR = "java.io.tmpdir";
  private static final String MY_TMPDIR = "/path/to/my/tmpdir";
  
  @Rule
  public final ProvideSystemProperty provideCustomTempDirRule
    = new ProvideSystemProperty( JAVA_IO_TMPDIR, MY_TMPDIR );
  
  @Test
  public void checkTempDir() {
    assertEquals( MY_TMPDIR, System.getProperty( JAVA_IO_TMPDIR ) );
  }
}