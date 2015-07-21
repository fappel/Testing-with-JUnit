package book.twju.chapter_3;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class Listing_2_Stub_TimelineTest {
  
  private static final FakeItem FIRST_ITEM = new FakeItem( 10 );
  private static final FakeItem SECOND_ITEM = new FakeItem( 20 );
  private static final FakeItem THIRD_ITEM = new FakeItem( 30 );
  
  private final static int NEW_FETCH_COUNT
    = new Timeline( new ItemProviderDummy() ).getFetchCount() + 1;

  private ItemProviderStub itemProvider;
  private Timeline timeline;

  @Before
  public void setUp() {
    itemProvider = new ItemProviderStub();
    timeline = new Timeline( itemProvider );
  }
  
  @Test
  public void fetchItems() {
    itemProvider.addItems( FIRST_ITEM, SECOND_ITEM, THIRD_ITEM );
    timeline.setFetchCount( 1 );    
    timeline.fetchItems();

    timeline.fetchItems();
    List<Item> actual = timeline.getItems();
      
    assertArrayEquals( new Item[] { THIRD_ITEM, SECOND_ITEM }, 
                       actual.toArray( new Item[ 2 ] ) );
  }
  
  @Test
  public void fetchFirstItems() {
    itemProvider.addItems( FIRST_ITEM, SECOND_ITEM );
      
    timeline.fetchItems();
    List<Item> actual = timeline.getItems();
      
    assertArrayEquals( new Item[] { SECOND_ITEM, FIRST_ITEM }, 
                       actual.toArray( new Item[ 2 ] ) );
  }
  
  @Test
  public void setFetchCount() {
    timeline.setFetchCount( NEW_FETCH_COUNT );
    
    assertEquals( NEW_FETCH_COUNT, timeline.getFetchCount() );
  }
  
  @Test
  public void initialState() {
    assertTrue( timeline.getFetchCount() > 0 ); 
  }
  
  @Test
  public void setFetchCountExceedsLowerBound() {
    int originalFetchCount = timeline.getFetchCount();
      
    timeline.setFetchCount( Timeline.FETCH_COUNT_LOWER_BOUND - 1 );
      
    assertEquals( originalFetchCount, timeline.getFetchCount() );  
  }
    
  @Test
  public void setFetchCountExceedsUpperBound() {
    int originalFetchCount = timeline.getFetchCount();
      
    timeline.setFetchCount( Timeline.FETCH_COUNT_UPPER_BOUND + 1 );
      
    assertEquals( originalFetchCount, timeline.getFetchCount() );  
  }
}