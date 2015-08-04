package book.twju.chapter_7;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public class Listing_2_JUnit_MementoAssert {
  
  public static void assertEqualsButNotSame( Memento expected, Memento actual ) {
    assertTopItemEquals( expected, actual );
    assertItemsEquals( expected, actual );
    assertNotSame( "Mementos must not be the same.", expected, actual );
  }

  public static void assertItemsEquals( Memento expected, Memento actual ) {
    assertEquals( "Memento items do not match\n", expected.getItems(), actual.getItems() );
  }

  public static void assertTopItemEquals( Memento expected, Memento actual ) {
    assertEquals( "Memento top item does not match\n", expected.getTopItem(), actual.getTopItem() );
  }
}