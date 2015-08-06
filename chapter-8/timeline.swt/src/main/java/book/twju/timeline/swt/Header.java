package book.twju.timeline.swt;

import static book.twju.timeline.swt.Resources.MARGIN;
import static book.twju.timeline.swt.Resources.changeFontHeight;
import static book.twju.timeline.swt.SwtTimelineCompound.createBackgroundProcessor;
import static book.twju.timeline.swt.util.FormDatas.attach;
import static book.twju.timeline.util.Assertion.checkArgument;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import book.twju.timeline.model.Item;
import book.twju.timeline.model.Timeline;
import book.twju.timeline.util.BackgroundProcessor;

class Header<T extends Item> {
  
  static final String TITLE = "Timeline";
  static final String TITLE_MUST_NOT_BE_NULL = "Argument 'title' must not be null.";

  private final BackgroundProcessor backgroundProcessor;
  private final Timeline<T> timeline;

  Composite control;
  Button fetchNew;
  Label title;

  Header( Timeline<T> timeline ) {
    this( timeline, createBackgroundProcessor() );
  }

  Header( Timeline<T> timeline, BackgroundProcessor backgroundProcessor ) {
    this.backgroundProcessor = backgroundProcessor;
    this.timeline = timeline;
  }

  void createUi( Composite parent ) {
    createControl( parent );
    createTitle();
    createFetchNew();
    layout();
  }
  
  Control getControl() {
    return control;
  }

  void update() {
    backgroundProcessor.process( () -> {
      int count = timeline.getNewCount();
      backgroundProcessor.dispatchToUiThread( () -> update( count ) );
    } );
  }
  
  void onFetchNew( Listener listener ) {
    fetchNew.addListener( SWT.Selection, evt -> notifyAboutFetchRequest( listener, evt ) );
  }
  
  void setTitle( String title ) {
    checkArgument( title != null, TITLE_MUST_NOT_BE_NULL );
    
    this.title.setText( title );
  }
  
  String getTitle() {
    return title.getText();
  }
  
  private void update( int count ) {
    fetchNew.setText( count + " new" );
    fetchNew.setVisible( count > 0 );
  }
  
  private void notifyAboutFetchRequest( Listener listener, Event evt ) {
    Event event = new Event();
    event.widget = control;
    listener.handleEvent( event );
    fetchNew.setVisible( false );
  }
  
  private void createControl( Composite parent ) {
    control = new Composite( parent, SWT.NONE );
    control.setBackgroundMode( SWT.INHERIT_DEFAULT );
    control.setBackground( control.getDisplay().getSystemColor( SWT.COLOR_RED ) );
  }
  
  private void createTitle() {
    title = new Label( control, SWT.NONE );
    title.setText( TITLE );
    title.setForeground( control.getDisplay().getSystemColor( SWT.COLOR_WHITE ) );
    changeFontHeight( title, 14 );
  }

  private void createFetchNew() {
    fetchNew = new Button( control, SWT.FLAT );
    fetchNew.setVisible( false );
  }
  
  private void layout() {
    control.setLayout( new FormLayout() );
    attach( title ).toLeft( MARGIN * 2 ).toTop( MARGIN ).fromRight( 50, MARGIN ).toBottom( MARGIN );
    attach( fetchNew ).fromLeft( 60, MARGIN * 2 ).toTop( MARGIN * 2 ).toRight( MARGIN * 2 ).toBottom( MARGIN * 2 );
  }
}