package book.twju.timeline.swt.application;

import java.io.File;
import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import book.twju.timeline.swt.SwtTimeline;
import book.twju.timeline.swt.application.itemui.git.GitTimelineFactory;
import book.twju.timeline.util.FileStorageStructure;

public class Application {
  
  private static final File BASE_DIRECTORY = new File( System.getProperty( "user.home" ) );
  private static final String URI = "https://github.com/junit-team/junit4.git";
  private static final String REPOSITORY_NAME = "junit4";

  public static void main( String[] args ) {
    Locale.setDefault( Locale.ENGLISH );
    Display display = Display.getDefault();
    Shell shell = createShell( display );
    SwtTimeline<?> timeline = createTimelineFactory().create( shell, URI, REPOSITORY_NAME );
    timeline.setTitle( "JUnit" );
    timeline.startAutoRefresh();
    spinUiLoop( display, shell );
  }

  private static Shell createShell( Display display ) {
    Shell result = new Shell( display, SWT.SHELL_TRIM );
    result.setLayout( new FillLayout() );
    result.setBounds( 100, 100, 350, 700 );
    return result;
  }

  private static void spinUiLoop( Display display, Shell shell ) {
    shell.open();
    while( !shell.isDisposed() ) {
      if( !display.readAndDispatch() ) {
        display.sleep();
      }
    }
  }
  
  private static GitTimelineFactory createTimelineFactory() {
    return new GitTimelineFactory( new FileStorageStructure( BASE_DIRECTORY ) );
  }
}