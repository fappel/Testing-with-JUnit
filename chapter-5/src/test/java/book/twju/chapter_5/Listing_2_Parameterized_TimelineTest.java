package book.twju.chapter_5;

import static book.twju.chapter_5.Listing_2_Parameterized_FetchItemsDataProvider.fetchItemsIfFetchCountExceedsItemCount;
import static book.twju.chapter_5.Listing_2_Parameterized_FetchItemsDataProvider.fetchItemsOnLowerFetchCountBound;
import static book.twju.chapter_5.ThrowableCaptor.thrownBy;
import static book.twju.chapter_5.Timeline.ERROR_EXCEEDS_LOWER_BOUND;
import static book.twju.chapter_5.Timeline.ERROR_EXCEEDS_UPPER_BOUND;
import static book.twju.chapter_5.Timeline.FETCH_COUNT_LOWER_BOUND;
import static book.twju.chapter_5.Timeline.FETCH_COUNT_UPPER_BOUND;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith( Parameterized.class )
public class Listing_2_Parameterized_TimelineTest {
  
  @Parameter
  public FetchItemsData data;
  
  private SessionStorage sessionStorage;
  private ItemProviderStub itemProvider;
  private Timeline timeline;
  
  @Parameters
  public static Object[] data() {
    return new Object[] {
      fetchItemsOnLowerFetchCountBound(),
      fetchItemsIfFetchCountExceedsItemCount()
    };
  }
  
  @Before
  public void setUp() {
    itemProvider = new ItemProviderStub();
    sessionStorage = mock( SessionStorage.class );
    timeline = new Timeline( itemProvider, sessionStorage );
  }
  
  @Test
  public void fetchItems() {
    itemProvider.addItems( data.getInput() );
    timeline.setFetchCount( data.getFetchCount() );    
    timeline.fetchItems();

    timeline.fetchItems();
    List<Item> actual = timeline.getItems();
      
    assertArrayEquals( data.getOutput(), 
                       actual.toArray() );
  }
  
  @Test
  public void setFetchCountExceedsUpperBound() {
    int tooLarge = FETCH_COUNT_UPPER_BOUND + 1;
      
    Throwable actual = thrownBy( () -> timeline.setFetchCount( tooLarge ) );

    assertNotNull( actual );  
    assertTrue( actual instanceof IllegalArgumentException );
    assertTrue( actual.getMessage().contains( valueOf( tooLarge ) ) );
    assertEquals( format( ERROR_EXCEEDS_UPPER_BOUND, tooLarge ), 
                  actual.getMessage() );
  }
  
  @Test
  public void setFetchCountExceedsLowerBound() {
    int tooSmall = FETCH_COUNT_LOWER_BOUND - 1;
    
    Throwable actual = thrownBy( () -> timeline.setFetchCount( tooSmall ) );
    
    assertNotNull( actual );  
    assertTrue( actual instanceof IllegalArgumentException );
    assertTrue( actual.getMessage().contains( valueOf( tooSmall ) ) );
    assertEquals( format( ERROR_EXCEEDS_LOWER_BOUND, tooSmall ), 
                  actual.getMessage() );
  }
}