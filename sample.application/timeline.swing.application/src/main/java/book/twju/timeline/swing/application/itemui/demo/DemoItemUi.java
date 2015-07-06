package book.twju.timeline.swing.application.itemui.demo;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.ocpsoft.prettytime.PrettyTime;

import book.twju.timeline.provider.demo.DemoItem;
import book.twju.timeline.swing.SwingItemUi;
import book.twju.timeline.swing.SwingTimelines;

class DemoItemUi implements SwingItemUi<DemoItem> {

  private final PrettyTime prettyTime;
  private final JTextArea content;
  private final JPanel component;
  private final JLabel author;
  private final DemoItem item;
  private final JLabel time;

  DemoItemUi( Container uiContext, DemoItem item, int index ) {
    this.item = item;
    this.prettyTime = new PrettyTime();
    this.component = new JPanel();
    this.author = new JLabel( item.getAuthor() );
    this.time = new JLabel( getPrettyTime() );
    this.content = new JTextArea( item.getContent() );
    component.setBackground( SwingTimelines.WHITE );
    author.setOpaque( true );
    author.setBackground( SwingTimelines.WHITE );
    time.setOpaque( true );
    time.setBackground( SwingTimelines.WHITE );
    content.setWrapStyleWord( true );
    content.setLineWrap( true );
    content.setEditable( false );
    component.setLayout( new BorderLayout( 5, 5 ) );
    component.add( author, BorderLayout.WEST );
    component.add( time, BorderLayout.EAST );
    component.add( content, BorderLayout.SOUTH );
    uiContext.add( component, SwingTimelines.createUiItemConstraints(), index );
  }
  
  @Override
  public Component getComponent() {
    return component;
  }

  @Override
  public void update() {
    time.setText( getPrettyTime() );
  }
  
  private String getPrettyTime() {
    return prettyTime.format( new Date( item.getTimeStamp() ) );
  }
}