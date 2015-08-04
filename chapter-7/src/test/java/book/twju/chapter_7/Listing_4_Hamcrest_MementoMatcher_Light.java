package book.twju.chapter_7;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;

public class Listing_4_Hamcrest_MementoMatcher_Light {
  
  public static Matcher<Memento> equalTo( Memento expected ) {
    return allOf(
      hasProperty( "items", CoreMatchers.equalTo( expected.getItems() ) ),
      hasProperty( "topItem", CoreMatchers.equalTo( expected.getTopItem() ) ) );
  }
}