package book.twju.timeline.model;

import org.assertj.core.api.AbstractAssert;

public class MementoAssert
  extends AbstractAssert<MementoAssert, Memento<? extends Item>>
{

  private static final String ITEM_PATTERN
    = "\nExpected items to be\n  <%s>,\nbut were\n  <%s>.";
  private static final String TOP_ITEM_PATTERN
    = "\nExpected top item to be\n  <%s>,\nbut was\n  <%s>.";

  public static MementoAssert assertThat( Memento<? extends Item> actual ) {
    return new MementoAssert( actual );
  }
  
  public MementoAssert( Memento<? extends Item> actual ) {
    super( actual, MementoAssert.class );
  }
  
  @Override
  public MementoAssert isEqualTo( Object expected ) {
    hasEqualItems( ( Memento<?> )expected );
    hasEqualTopItem( ( Memento<?> )expected );
    return this;
  }
  
  public MementoAssert hasEqualItems( Memento<? extends Item> expected ) {
    isNotNull();
    if( !actual.getItems().equals( expected.getItems() ) ) {
      failWithMessage( ITEM_PATTERN, expected.getItems(), actual.getItems() );
    }
    return this;
  }
  
  public MementoAssert hasEqualTopItem( Memento<? extends Item> expected ) {
    isNotNull();
    if( !actual.getTopItem().equals( expected.getTopItem() ) ) {
      failWithMessage( TOP_ITEM_PATTERN, expected.getTopItem(), actual.getTopItem() );
    }
    return this;
  }
}