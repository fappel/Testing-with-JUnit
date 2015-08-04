package book.twju.timeline.util;

import java.util.Date;
import java.util.Locale;

import org.ocpsoft.prettytime.PrettyTime;

public class NiceTime {
  
  private PrettyTime prettyTime;

  public NiceTime() {
    prettyTime = new PrettyTime( Locale.ENGLISH );
  }

  public String format( Date then ) {
    return prettyTime.format( then );
  }
}