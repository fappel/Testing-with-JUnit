package book.twju.timeline.swing.application;

import static book.twju.timeline.swing.application.itemui.git.GitTimelineFactory.createTimeline;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import static javax.swing.SwingUtilities.invokeLater;

import java.awt.Component;
import java.util.Locale;

import javax.swing.JFrame;

import book.twju.timeline.swing.SwingTimeline;

public class Application {

  private static final String BASE_DIRECTORY = System.getProperty( "user.home" );
  private static final String URI = "git@github.com:junit-team/junit.git";
  private static final String REPOSITORY_NAME = "junit";
  
  public static void main( String[] args ) {
    Locale.setDefault( Locale.ENGLISH );
    invokeLater( () -> createAndShowUi() );
  }

  private static void createAndShowUi() {
    JFrame frame = createFrame();
    SwingTimeline<?> timeline = createTimeline( BASE_DIRECTORY, URI, REPOSITORY_NAME );
    layout( frame, timeline.getComponent() );
    timeline.startAutoRefresh();
  }
  
  private static JFrame createFrame() {
    JFrame result = new JFrame( "Timeline" );
    result.setDefaultCloseOperation( EXIT_ON_CLOSE );
    return result;
  }
  
  private static void layout( JFrame frame, Component timeline ) {
    frame.add( timeline );
    frame.setBounds( 100, 100, 350, 700 );
    frame.setVisible( true );
  }
}