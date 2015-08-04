package book.twju.timeline.model;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableSet;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FakeItems {
  
  public static final FakeItem FIRST_ITEM = new FakeItem( "1", 10 );
  public static final FakeItem SECOND_ITEM = new FakeItem( "2", 20 );
  public static final FakeItem THIRD_ITEM = new FakeItem( "3", 30 );

  public static final Set<FakeItem> ALL_ITEMS 
     = unmodifiableSet(
         new HashSet<FakeItem>(
           asList( FIRST_ITEM, SECOND_ITEM, THIRD_ITEM ) ) );
  
  public static FakeItem[] createItems( int itemCount ) {
    FakeItem[] result = new FakeItem[ itemCount ];
    for( int i = 0; i < result.length; i++ ) {
      result[ i ] = new FakeItem( String.valueOf( i ), i * 10L );
    }
    return result;
  }

  public static FakeItem[] reverse( FakeItem[] items ) {
    List<FakeItem> itemList = asList( items );
    Collections.reverse( itemList );
    return itemList.toArray( new FakeItem[ items.length ] );
  }

  public static FakeItem[] subArray( FakeItem[] items, int fromIndex, int toIndex  ) {
    return asList( items )
      .subList( fromIndex, toIndex )
      .toArray( new FakeItem[ toIndex ] );
  }
}