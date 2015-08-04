package book.twju.timeline.model;

import static book.twju.timeline.util.Assertion.checkArgument;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Memento<T extends Item> {

  static final String TOP_ITEM_IS_MISSING = "Top item is missing.";
  static final String TOP_ITEM_IS_UNRELATED = "Top item is unrelated.";
  static final String ARGUMENT_ITEMS_MUST_NOT_BE_NULL = "Argument 'items' must not be null.";
  static final String ARGUMENT_TOP_ITEM_MUST_NOT_BE_NULL = "Argument 'topItem' must not be null.";

  private static final Memento<?> EMPTY_MEMENTO = new Memento<>( new HashSet<>(), Optional.empty() );

  private final Optional<T> topItem;
  private final Set<T> items;

  @SuppressWarnings( "unchecked" )
  public static <T extends Item> Memento<T> empty() {
    return ( Memento<T> )EMPTY_MEMENTO;
  }

  public Memento( Set<T> items, Optional<T> topItem ) {
    checkArgument( items != null, ARGUMENT_ITEMS_MUST_NOT_BE_NULL );
    checkArgument( topItem != null, ARGUMENT_TOP_ITEM_MUST_NOT_BE_NULL );
    checkArgument( topItemExistsIfItemsNotEmpty( items, topItem ), TOP_ITEM_IS_MISSING );
    checkArgument( topItemIsElementOfItems( items, topItem ), TOP_ITEM_IS_UNRELATED );
    
    this.items = new HashSet<>( items );
    this.topItem = topItem;
  }
  
  public Set<T> getItems() {
    return new HashSet<>( items );
  }

  public Optional<T> getTopItem() {
    return topItem;
  }

  private static <T> boolean topItemExistsIfItemsNotEmpty( Set<T> items, Optional<T> topItem ) {
    return    !topItem.isPresent() && items.isEmpty()
           || topItem.isPresent() && !items.isEmpty()
           || items.isEmpty();
  }

  private static <T> boolean topItemIsElementOfItems( Set<T> items, Optional<T> topItem ) {
    return    items.isEmpty() && !topItem.isPresent()
           || !items.isEmpty() && items.contains( topItem.get() );
  }
}