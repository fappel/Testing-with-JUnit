package book.twju.timeline.swt.test.util;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Touch;
import org.eclipse.swt.widgets.Widget;

public class SwtEventHelper {

  final Event event;

  private SwtEventHelper( int eventType ) {
    event = new Event();
    event.type = eventType;
  }

  public static SwtEventHelper trigger( int eventType ) {
    return new SwtEventHelper( eventType );
  }

  public SwtEventHelper atX( int x ) {
    event.x = x;
    return this;
  }

  public SwtEventHelper atY( int y ) {
    event.y = y;
    return this;
  }

  public SwtEventHelper at( int x, int y ) {
    return atX( x ).atY( y );
  }

  public SwtEventHelper withCount( int count ) {
    event.count = count;
    return this;
  }

  public SwtEventHelper withStateMask( int stateMask ) {
    event.stateMask = stateMask;
    return this;
  }

  public SwtEventHelper withButton( int button ) {
    event.button = button;
    return this;
  }

  public SwtEventHelper withCharacter( char character ) {
    event.character = character;
    return this;
  }

  public SwtEventHelper withData( Object data ) {
    event.data = data;
    return this;
  }

  public SwtEventHelper withDetail( int detail ) {
    event.detail = detail;
    return this;
  }

  public SwtEventHelper withEnd( int end ) {
    event.end = end;
    return this;
  }

  public SwtEventHelper withStart( int start ) {
    event.start = start;
    return this;
  }

  public SwtEventHelper withRange( int start, int end ) {
    return withStart( start ).withEnd( end );
  }

  public SwtEventHelper withGC( GC gc ) {
    event.gc = gc;
    return this;
  }

  public SwtEventHelper withWidth( int width ) {
    event.width = width;
    return this;
  }

  public SwtEventHelper withHeight( int height ) {
    event.height = height;
    return this;
  }

  public SwtEventHelper withSize( int width, int height ) {
    return withWidth( width ).withHeight( height );
  }

  public SwtEventHelper withIndex( int index ) {
    event.index = index;
    return this;
  }

  public SwtEventHelper withItem( Widget item ) {
    event.item = item;
    return this;
  }

  public SwtEventHelper withKeyCode( int keyCode ) {
    event.keyCode = keyCode;
    return this;
  }

  public SwtEventHelper withKeyLocation( int keyLocation ) {
    event.keyLocation = keyLocation;
    return this;
  }

  public SwtEventHelper withMagnification( double magnification ) {
    event.magnification = magnification;
    return this;
  }

  public SwtEventHelper withRotation( double rotation ) {
    event.rotation = rotation;
    return this;
  }

  public SwtEventHelper withText( String text ) {
    event.text = text;
    return this;
  }

  public SwtEventHelper withTime( int time ) {
    event.time = time;
    return this;
  }

  public SwtEventHelper withTouches( Touch[] touches ) {
    event.touches = touches;
    return this;
  }

  public SwtEventHelper withXDirection( int xDirection ) {
    event.xDirection = xDirection;
    return this;
  }

  public SwtEventHelper withYDirection( int yDirection ) {
    event.yDirection = yDirection;
    return this;
  }

  public void on( Widget widget ) {
    event.widget = widget;
    event.display = widget.getDisplay();
    widget.notifyListeners( event.type, event );
  }
}