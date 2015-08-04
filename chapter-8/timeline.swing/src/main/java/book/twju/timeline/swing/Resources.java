package book.twju.timeline.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

public class Resources {

  public static final Color WHITE = new Color( 255, 255, 255 );

  public static void changeFontSize( Component component, int increment ) {
    Font baseFont = component.getFont();
    Font font = createFrom( baseFont, increment );
    component.setFont( font );
  }

  static Font createFrom( Font baseFont, int increment ) {
    return new Font( baseFont.getName(), baseFont.getStyle(), baseFont.getSize() + increment );
  }
}