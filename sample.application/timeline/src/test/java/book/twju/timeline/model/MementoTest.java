package book.twju.timeline.model;

import static book.twju.timeline.model.FakeItems.ALL_ITEMS;
import static book.twju.timeline.model.FakeItems.FIRST_ITEM;
import static book.twju.timeline.model.FakeItems.SECOND_ITEM;
import static book.twju.timeline.model.Memento.ARGUMENT_ITEMS_MUST_NOT_BE_NULL;
import static book.twju.timeline.model.Memento.ARGUMENT_TOP_ITEM_MUST_NOT_BE_NULL;
import static book.twju.timeline.model.Memento.TOP_ITEM_IS_MISSING;
import static book.twju.timeline.model.Memento.TOP_ITEM_IS_UNRELATED;
import static book.twju.timeline.model.MementoAssert.assertThat;
import static book.twju.timeline.test.util.ThrowableCaptor.thrownBy;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class MementoTest {

  private static final Optional<FakeItem> UNRELATED_ITEM = Optional.of( new FakeItem( "id", 2 ) );
  
  private Memento<FakeItem> memento;
  private HashSet<FakeItem> givenItems;

  @Before
  public void setup() {
    givenItems = new HashSet<>( ALL_ITEMS );
    memento = new Memento<>( givenItems, Optional.of( FIRST_ITEM ) );
  }

  @Test
  public void getItems() {
    Set<FakeItem> actual = memento.getItems();
    
    assertThat( actual ).isEqualTo( ALL_ITEMS );
  }

  @Test
  public void getTopItem()  {
    Optional<FakeItem> actual = memento.getTopItem();
    
    assertThat( actual ).contains( FIRST_ITEM );
  }

  @Test
  public void getItemsIfGivenItemSetChanges() {
    givenItems.remove( SECOND_ITEM );

    Set<FakeItem> actual = memento.getItems();
    
    assertThat( actual ).isEqualTo( ALL_ITEMS );
  }
  
  @Test
  public void getItemsIfPreviouslyReturnedItemSetChanges() {
    Set<FakeItem> resultSet = memento.getItems();
    
    resultSet.remove( SECOND_ITEM );
    Set<FakeItem> actual = memento.getItems();
    
    assertThat( actual ).isEqualTo( ALL_ITEMS );
  }
  
  @Test
  public void constructWithEmptyItemsAndWithoutTopItem() {
    Memento<Item> actual = Memento.empty();
    
    assertThat( actual.getItems() ).isEmpty();
    assertThat( actual.getTopItem() ).isEmpty();
  }
  
  @Test
  public void constructWithEmptyItemsButWitTopItem() {
    Throwable actual = thrownBy( () -> new Memento<>( new HashSet<>(), UNRELATED_ITEM ) );
    
    assertThat( actual )
      .hasMessage( TOP_ITEM_IS_UNRELATED )
      .isInstanceOf( IllegalArgumentException.class );
  }
  
  @Test
  public void constructWithItemsButWithoutTopItem() {
    Throwable actual = thrownBy( () -> new Memento<>( ALL_ITEMS, Optional.empty() ) );
    
    assertThat( actual )
      .hasMessage( TOP_ITEM_IS_MISSING )
      .isInstanceOf( IllegalArgumentException.class );
  }
  
  @Test
  public void constructWithItemsAndUnrelatedTopItem() {
    Throwable actual = thrownBy( () -> new Memento<>( ALL_ITEMS, UNRELATED_ITEM ) );
   
    assertThat( actual )
      .hasMessage( TOP_ITEM_IS_UNRELATED )
      .isInstanceOf( IllegalArgumentException.class );
  }
  
  @Test
  public void constructWithNullAsItems() {
    Throwable actual = thrownBy( () -> new Memento<>( null, Optional.empty() ) );
    
    assertThat( actual )
      .hasMessage( ARGUMENT_ITEMS_MUST_NOT_BE_NULL )
      .isInstanceOf( IllegalArgumentException.class );
  }
  
  @Test
  public void constructWithNullAsTopItem() {
    Throwable actual = thrownBy( () -> new Memento<>( new HashSet<>(), null ) );
    
    assertThat( actual )
      .hasMessage( ARGUMENT_TOP_ITEM_MUST_NOT_BE_NULL )
      .isInstanceOf( IllegalArgumentException.class );
  }
  
  @Test
  public void empty() {
    Memento<Item> expected = new Memento<>( new HashSet<>(), Optional.empty() );
    
    Memento<Item> actual = Memento.empty();
    
    assertThat( actual ).isEqualTo( expected );
  }
}