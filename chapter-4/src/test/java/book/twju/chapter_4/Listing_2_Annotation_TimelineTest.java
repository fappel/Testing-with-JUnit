package book.twju.chapter_4;

import static org.mockito.Mockito.mock;

import org.junit.Test;

public class Listing_2_Annotation_TimelineTest {
  
  @Test( expected = IllegalArgumentException.class )
  public void constructWithNullAsItemProvider() {
    new Timeline( null, mock( SessionStorage.class ) );
  }
  
  @Test( expected = IllegalArgumentException.class )
  public void constructWithNullAsSessionStorage() {
    new Timeline( mock( ItemProvider.class ), null );
  }

  @Test( expected = IllegalArgumentException.class )
  public void setFetchCountExceedsLowerBound() {
    Timeline timeline = createTimeline();

    timeline.setFetchCount( Timeline.FETCH_COUNT_LOWER_BOUND - 1 );
  }
  
  @Test( expected = IllegalArgumentException.class )
  public void setFetchCountExceedsUpperBound() {
    Timeline timeline = createTimeline();
    
    timeline.setFetchCount( Timeline.FETCH_COUNT_LOWER_BOUND - 1 );
  }

  private Timeline createTimeline() {
    return new Timeline( mock( ItemProvider.class ),
                         mock( SessionStorage.class ) );
  }
}