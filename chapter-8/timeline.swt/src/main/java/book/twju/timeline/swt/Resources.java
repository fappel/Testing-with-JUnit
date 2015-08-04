package book.twju.timeline.swt;

import static java.util.Arrays.asList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

public class Resources {

  public static final int MARGIN = 5;

  public static Color getColorWhite() {
    return getSystemColor( SWT.COLOR_WHITE );
  }

  public static Color getColorRed() {
    return getSystemColor( SWT.COLOR_RED );
  }
  
  private static Color getSystemColor( int colorCode ) {
    return Display.getCurrent().getSystemColor( colorCode );
  }

  public static void changeFontHeight( Control control, int increment ) {
    FontData[] fontData = control.getFont().getFontData();
    asList( fontData ).forEach( data -> data.setHeight( data.getHeight() + increment ) );
    Font newFont = new Font( control.getDisplay(), fontData );
    control.setFont( newFont );
    control.addListener( SWT.Dispose, evt -> newFont.dispose() );
  }
}