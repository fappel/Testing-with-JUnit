package book.twju.timeline.swt;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Shell;
import org.junit.Rule;
import org.junit.Test;

import book.twju.timeline.swt.test.util.DisplayHelper;

public class ResourcesTest {
  
  private static final int FONT_SIZE_INCREMENT = 2;
  
  @Rule
  public final DisplayHelper displayHelper = new DisplayHelper();
  
  @Test
  public void getColorWhite() {
    Color expected = getSystemColor( SWT.COLOR_WHITE );
    
    Color actual = Resources.getColorWhite();
    
    assertThat( actual ).isEqualTo( expected );
  }
  
  @Test
  public void getColorRed() {
    Color expected = getSystemColor( SWT.COLOR_RED );
     
    Color actual = Resources.getColorRed();

    assertThat( actual ).isEqualTo( expected );
  }
  
  @Test
  public void changeFontHeight() {
    Shell control = displayHelper.createShell();
    List<Integer> expected = calculateExpectedFontDataHeights( control );
    
    Resources.changeFontHeight( control, FONT_SIZE_INCREMENT );
    
    assertThat( getFontDataHeights( control ) ).isEqualTo( expected );
  }

  private Color getSystemColor( int colorCode ) {
    return displayHelper.getDisplay().getSystemColor( colorCode );
  }
  
  private List<Integer> calculateExpectedFontDataHeights( Shell control ) {
    List<Integer> fontHeights = getFontDataHeights( control );
    return incrementFontDataHeights( fontHeights );
  }
  
  private List<Integer> incrementFontDataHeights( List<Integer> fontHeights ) {
    return fontHeights.stream().map( height -> height + FONT_SIZE_INCREMENT ).collect( Collectors.toList() );
  }
  
  private List<Integer> getFontDataHeights( Shell control ) {
    FontData[] fontDatas = control.getFont().getFontData();
    return asList( fontDatas ).stream().map( fontData -> fontData.getHeight() ).collect( Collectors.toList() );
  }
}