package book.twju.chapter_6;

import static org.junit.Assert.assertNull;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ClearSystemProperties;

public class ClearPropertiesExample {
  
  private static final String JAVA_IO_TMPDIR = "java.io.tmpdir";
  
  @Rule
  public final ClearSystemProperties clearTempDirRule
    = new ClearSystemProperties( JAVA_IO_TMPDIR );
  
  @Test
  public void checkTempDir() {
    assertNull( System.getProperty( JAVA_IO_TMPDIR ) );
  }
}