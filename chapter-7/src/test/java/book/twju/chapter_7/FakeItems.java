package book.twju.chapter_7;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableSet;

import java.util.HashSet;
import java.util.Set;

public class FakeItems {

  static final FakeItem FIRST_ITEM = new FakeItem( 10 );
  static final FakeItem SECOND_ITEM = new FakeItem( 20 );
  static final FakeItem THIRD_ITEM = new FakeItem( 30 );
  
  public static final Set<FakeItem> ALL_ITEMS 
    = unmodifiableSet( new HashSet<FakeItem>( asList( FIRST_ITEM, SECOND_ITEM, THIRD_ITEM ) ) );
}