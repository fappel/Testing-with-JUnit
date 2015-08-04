package book.twju.timeline.swt.application.itemui.git;

import static book.twju.timeline.swt.Resources.MARGIN;
import static book.twju.timeline.swt.Resources.changeFontHeight;
import static book.twju.timeline.swt.Resources.getColorWhite;
import static book.twju.timeline.swt.util.FormDatas.attach;

import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import book.twju.timeline.provider.git.GitItem;
import book.twju.timeline.swt.SwtItemUi;
import book.twju.timeline.util.NiceTime;

class GitItemUi implements SwtItemUi<GitItem> {

  private final NiceTime niceTime;
  private final Composite control;
  private final Label content;
  private final GitItem item;
  private final Label author;
  private final Label time;

  GitItemUi( Composite parent, GitItem item, int index ) {
    this( parent, item, index, new NiceTime() );
  }
  
  GitItemUi( Composite parent, GitItem item, int index, NiceTime niceTime ) {
    this.niceTime = niceTime;
    this.item = item;
    this.control = createControl( parent );
    this.author = createAutor();
    this.time = createTime();
    this.content = createContent();
    layout();
    adjustDrawingOrderPlacement( parent, index );
  }
  
  @Override
  public Control getControl() {
    return control;
  }

  @Override
  public void update() {
    time.setText( getPrettyTime() );
  }
  
  String getTime() {
    return time.getText();
  }
  
  private static Composite createControl( Composite parent ) {
    Composite result = new Composite( parent, SWT.NONE );
    result.setBackground( getColorWhite() );
    result.setBackgroundMode( SWT.INHERIT_DEFAULT );
    return result;
  }
  
  private Label createAutor( ) {
    Label result = new Label( control, SWT.NONE );
    result.setText( item.getAuthor() );
    changeFontHeight( result, 2 );
    return result;
  }

  private Label createTime( ) {
    Label time = new Label( control, SWT.NONE );
    time.setText( getPrettyTime() );
    changeFontHeight( time, 2 );
    return time;
  }
  
  private Label createContent(  ) {
    Label content = new Label( control, SWT.WRAP );
    content.setText( item.getContent() );
    changeFontHeight( content, 1 );
    return content;
  }
  
  private void layout() {
    control.setLayout( new FormLayout() );
    attach( author ).toLeft( MARGIN ).toTop( MARGIN );
    attach( time ).toTop( MARGIN ).toRight( MARGIN );
    attach( content ).toLeft( MARGIN ).atTopTo( author, MARGIN ).toRight( MARGIN ).toBottom( MARGIN );
  }
  
  private void adjustDrawingOrderPlacement( Composite parent, int index ) {
    if( parent.getChildren().length > index ) {
      Control child = parent.getChildren()[ index ];
      control.moveAbove( child );
    }
  }
  
  private String getPrettyTime() {
    return niceTime.format( new Date( item.getTimeStamp() ) );
  }
}