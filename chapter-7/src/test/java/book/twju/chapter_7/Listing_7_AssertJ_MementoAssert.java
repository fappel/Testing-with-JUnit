package book.twju.chapter_7;

import org.assertj.core.api.AbstractAssert;

public class Listing_7_AssertJ_MementoAssert extends AbstractAssert<Listing_7_AssertJ_MementoAssert, Memento> {

  private static final String ITEM_PATTERN
    = "\nExpected items to be\n  <%s>,\nbut were\n  <%s>.";
  private static final String TOP_ITEM_PATTERN
    = "\nExpected top item to be\n  <%s>,\nbut was\n  <%s>.";

  public static Listing_7_AssertJ_MementoAssert assertThat( Memento actual ) {
    return new Listing_7_AssertJ_MementoAssert( actual );
  }
  
  public Listing_7_AssertJ_MementoAssert( Memento actual ) {
    super( actual, Listing_7_AssertJ_MementoAssert.class );
  }
  
  @Override
  public Listing_7_AssertJ_MementoAssert isEqualTo( Object expected ) {
    hasEqualItems( ( Memento )expected );
    hasEqualTopItem( ( Memento )expected );
    return this;
  }
  
  public Listing_7_AssertJ_MementoAssert hasEqualItems( Memento expected ) {
    isNotNull();
    if( !actual.getItems().equals( expected.getItems() ) ) {
      failWithMessage( ITEM_PATTERN, expected.getItems(), actual.getItems() );
    }
    return this;
  }
  
  public Listing_7_AssertJ_MementoAssert hasEqualTopItem( Memento expected ) {
    isNotNull();
    if( !actual.getTopItem().equals( expected.getTopItem() ) ) {
      failWithMessage( TOP_ITEM_PATTERN, expected.getTopItem(), actual.getTopItem() );
    }
    return this;
  }
}