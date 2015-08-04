package book.twju.timeline.model;

import org.assertj.core.api.AbstractAssert;

public class ItemAssert extends AbstractAssert<ItemAssert, Item> {

  public static ItemAssert assertThat( Item actual ) {
    return new ItemAssert( actual );
  }
  
  public ItemAssert( Item actual ) {
    super( actual, ItemAssert.class );
  }
  
  public ItemAssert hasId( String expected ) {
    isNotNull();
    if( actual.getId() != expected ) {
      failWithMessage( "Expected id to be <%s>, but was <%s>.", expected, actual.getId() );
    }
    return this;
  }
  
  public ItemAssert hasTimeStamp( long expected ) {
    isNotNull();
    if( actual.getTimeStamp() != expected ) {
      failWithMessage( "Expected timeStamp to be <%s>, but was <%s>.", expected, actual.getTimeStamp() );
    }
    return this;
  }
  
  public ItemAssert isCoincidentTo( Item expected ) {
    isNotNull();
    if( actual.compareTo( expected ) != 0 ) {
      failWithMessage( "Expected item <%s> to be conincident to <%s>, but was not.", actual, expected );
    }
    return this;
  }
  
  public ItemAssert isSubsequentTo( Item expected ) {
    isNotNull();
    if( actual.compareTo( expected ) <= 0 ) {
      failWithMessage( "Expected item <%s> to be subsequent to <%s>, but was not.", actual, expected );
    }
    return this;
  }
  
  public ItemAssert isPrecedentTo( Item expected ) {
    isNotNull();
    if( actual.compareTo( expected ) >= 0 ) {
      failWithMessage( "Expected item <%s> to be precedent to <%s>, but was not.", actual, expected );
    }
    return this;
  }
}