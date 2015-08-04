package book.twju.chapter_7;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class Listing_4_Hamcrest_MementoMatcher extends TypeSafeMatcher<Memento> {

  private Memento expected;
  
  @Factory
  public static Matcher<Memento> equalTo( Memento expected ) {
    return new Listing_4_Hamcrest_MementoMatcher( expected );
  }
  
  @Override
  protected boolean matchesSafely( Memento actual ) {
    return    actual.getItems().equals( expected.getItems() )
           && actual.getTopItem().equals( expected.getTopItem() );
  }
  
  @Override
  public void describeTo( Description description ) {
    String pattern = "\n  topItem: %s\n  items: %s";
    description.appendText( format( expected, pattern ) );
  }

  @Override
  protected void describeMismatchSafely( Memento actual, Description description ) {
    String pattern = "\n     was:\n  topItem: %s\n  items: %s";
    description.appendText( format( actual, pattern ) );
  }
  
  private Listing_4_Hamcrest_MementoMatcher( Memento expected ) {
    this.expected = expected;
  }
  
  private static String format( Memento memento, String pattern ) {
    return String.format( pattern, memento.getTopItem(), memento.getItems() );
  }
}