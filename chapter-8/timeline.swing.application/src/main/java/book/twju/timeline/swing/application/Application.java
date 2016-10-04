package book.twju.timeline.swing.application;

import static javax.swing.JFrame.EXIT_ON_CLOSE;
import static javax.swing.SwingUtilities.invokeLater;

import java.awt.Component;
import java.io.File;
import java.util.Locale;

import javax.swing.JFrame;

import book.twju.timeline.swing.SwingTimeline;
import book.twju.timeline.swing.application.itemui.git.GitTimelineFactory;
import book.twju.timeline.util.FileStorageStructure;

public class Application {

  private static final File BASE_DIRECTORY = new File( System.getProperty( "user.home" ) );
  private static final String URI = "https://github.com/junit-team/junit4.git";
  private static final String REPOSITORY_NAME = "junit4";
  
  public static void main( String[] args ) {
    Locale.setDefault( Locale.ENGLISH );
    invokeLater( () -> createAndShowUi() );
  }

  private static void createAndShowUi() {
    JFrame frame = createFrame();
    SwingTimeline<?> timeline = createTimelineFactory().create( URI, REPOSITORY_NAME );
    timeline.setTitle( "JUnit" );
    layout( frame, timeline.getComponent() );
    timeline.startAutoRefresh();
  }
  
  private static JFrame createFrame() {
    JFrame result = new JFrame( "Timeline" );
    result.setDefaultCloseOperation( EXIT_ON_CLOSE );
    return result;
  }
  
  private static GitTimelineFactory createTimelineFactory() {
    return new GitTimelineFactory( new FileStorageStructure( BASE_DIRECTORY ) );
  }
  
  private static void layout( JFrame frame, Component timeline ) {
    frame.add( timeline );
    frame.setBounds( 100, 100, 350, 700 );
    frame.setVisible( true );
  }
}