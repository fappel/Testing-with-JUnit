package book.twju.timeline.swing;

import static java.awt.GridBagConstraints.HORIZONTAL;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class SwingTimelines {

  public static final Color WHITE = new Color( 255, 255, 255 );

  public static GridBagConstraints createUiItemConstraints() {
    GridBagConstraints result = new GridBagConstraints();
    result.gridx = 0;
    result.fill = HORIZONTAL;
    result.weightx = 1;
    result.insets = new Insets( 15, 10, 5, 10 );
    return result;
  }
}