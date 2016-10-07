package book.twju.timeline.tabris;

import java.io.File;
import java.util.Locale;

import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.EntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import book.twju.timeline.swt.SwtTimeline;
import book.twju.timeline.swt.application.itemui.git.GitTimelineFactory;
import book.twju.timeline.util.FileStorageStructure;

public class TimelineEntryPoint implements EntryPoint {

  private static final File BASE_DIRECTORY = new File( System.getProperty( "user.home" ) );
  private static final String URI = "https://github.com/junit-team/junit4.git";
  private static final String REPOSITORY_NAME = "junit4";
  
  private final StorageDirectoryProvider storageDirectoryProvider;

  public TimelineEntryPoint() {
    storageDirectoryProvider = new StorageDirectoryProvider( BASE_DIRECTORY );
  }
  @Override
  public int createUI() {
    Locale.setDefault( Locale.ENGLISH );
    Shell shell = new Shell( new Display(), SWT.NO_TRIM );
    shell.setMaximized( true );
    shell.setLayout( new FillLayout() );
    SwtTimeline<?> timeline = createTimelineFactory().create( shell, URI, REPOSITORY_NAME );
    timeline.setTitle( "JUnit" );
    timeline.startAutoRefresh();
    RWT.getUISession().addUISessionListener( evt -> timeline.stopAutoRefresh() );
    shell.open();
    return 0;
  }

  private GitTimelineFactory createTimelineFactory() {
    File directory = storageDirectoryProvider.getDirectory( RWT.getRequest(), RWT.getResponse() );
    return new GitTimelineFactory( new FileStorageStructure( directory ) );
  }
}