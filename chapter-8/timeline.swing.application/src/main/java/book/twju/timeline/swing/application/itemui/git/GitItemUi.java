package book.twju.timeline.swing.application.itemui.git;

import static book.twju.timeline.swing.Resources.WHITE;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import book.twju.timeline.provider.git.GitItem;
import book.twju.timeline.swing.SwingItemUi;
import book.twju.timeline.util.NiceTime;

class GitItemUi implements SwingItemUi<GitItem> {

  private final NiceTime niceTime;
  private final JTextArea content;
  private final JPanel component;
  private final JLabel author;
  private final GitItem item;
  private final JLabel time;

  GitItemUi( GitItem item ) {
    this( item, new NiceTime() );
  }
  
  GitItemUi( GitItem item, NiceTime niceTime ) {
    this.niceTime = niceTime;
    this.item = item;
    this.component = createComponent();
    this.author = createAuthor( item );
    this.time = createTime();
    this.content = createContent( item );
    layout();
  }

  @Override
  public Component getComponent() {
    return component;
  }
  
  @Override
  public void update() {
    time.setText( getPrettyTime() );
  }
  
  String getTime() {
    return time.getText();
  }
  
  private JPanel createComponent() {
    JPanel result = new JPanel();
    result.setBackground( WHITE );
    return result;
  }
  
  private JLabel createAuthor( GitItem item ) {
    JLabel result = new JLabel( item.getAuthor() );
    result.setOpaque( true );
    result.setBackground( WHITE );
    return result;
  }
  
  private JLabel createTime() {
    JLabel result = new JLabel( getPrettyTime() );
    result.setOpaque( true );
    result.setBackground( WHITE );
    return result;
  }
  
  private JTextArea createContent( GitItem item ) {
    JTextArea result = new JTextArea( item.getContent() );
    result.setWrapStyleWord( true );
    result.setLineWrap( true );
    result.setEditable( false );
    return result;
  }
  
  private void layout() {
    component.setLayout( new BorderLayout( 5, 5 ) );
    component.add( author, BorderLayout.WEST );
    component.add( time, BorderLayout.EAST );
    component.add( content, BorderLayout.SOUTH );
  }
  
  private String getPrettyTime() {
    return niceTime.format( new Date( item.getTimeStamp() ) );
  }
}