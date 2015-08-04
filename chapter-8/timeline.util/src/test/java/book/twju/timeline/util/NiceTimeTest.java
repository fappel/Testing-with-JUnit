package book.twju.timeline.util;

import static java.lang.System.currentTimeMillis;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class NiceTimeTest {
  
  private NiceTime niceTime;

  @Before
  public void setUp() {
    niceTime = new NiceTime();
  }
  
  @Test
  public void formatNow() {
    String actual = niceTime.format( now() );
    
    assertThat( actual ).contains( "moments" );
  }
  
  @Test
  public void formatFiveMinutesAgo() {
    String actual = niceTime.format( fiveMinutesAgo() );
    
    assertThat( actual ).contains( "5 minutes ago" );
  }

  private Date fiveMinutesAgo() {
    return new Date( currentTimeMillis() - 5 * 1_000 * 60 );
  }
  
  private Date now() {
    return new Date( currentTimeMillis() );
  }
}