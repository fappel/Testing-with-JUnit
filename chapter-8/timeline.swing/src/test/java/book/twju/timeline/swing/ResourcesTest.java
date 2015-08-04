package book.twju.timeline.swing;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;

import org.junit.Test;

public class ResourcesTest {
  
  private static final int BASE_SIZE = 10;
  private static final int INCREMENT = 10;
  private static final String FONT_NAME = "fontName";
  private static final int FONT_STYLE = Font.BOLD;

  @Test
  public void changeFontSize() {
    Component component = new JLabel();
    Font expected = Resources.createFrom( component.getFont(), INCREMENT );    
    
    Resources.changeFontSize( component, INCREMENT );
    
    assertThat( component.getFont() ).isEqualTo( expected );
  }
  
  @Test
  public void createFrom() {
    Font base = new Font( FONT_NAME, FONT_STYLE, BASE_SIZE );
    Font expected = new Font( FONT_NAME, FONT_STYLE, BASE_SIZE + INCREMENT );
     
    Font actual = Resources.createFrom( base, INCREMENT );
    
    assertThat( actual ).isEqualTo( expected );
  }
}