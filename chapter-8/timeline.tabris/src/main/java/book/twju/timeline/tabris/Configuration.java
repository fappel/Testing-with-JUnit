package book.twju.timeline.tabris;

import static com.eclipsesource.tabris.TabrisClientInstaller.install;

import org.eclipse.rap.rwt.application.Application;
import org.eclipse.rap.rwt.application.ApplicationConfiguration;

public class Configuration implements ApplicationConfiguration {

  @Override
  public void configure( Application application ) {
    install( application );
    application.addEntryPoint( "/timeline", TimelineEntryPoint.class, null );
  }
}