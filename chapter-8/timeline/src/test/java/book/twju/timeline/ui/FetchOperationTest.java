package book.twju.timeline.ui;

import static book.twju.timeline.test.util.ThrowableCaptor.thrownBy;
import static book.twju.timeline.ui.FetchOperation.TIMELINE_MUST_NOT_BE_NULL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import book.twju.timeline.model.Item;
import book.twju.timeline.model.Timeline;


public class FetchOperationTest {

  private Timeline<Item> timeline;

  @Before
  @SuppressWarnings("unchecked")
  public void setUp() {
    timeline = mock( Timeline.class );
  }
  
  @Test
  public void fetchNew() {
    FetchOperation.NEW.fetch( timeline );
    
    verify( timeline ).fetchNew();
  }

  @Test
  public void fetchMore() {
    FetchOperation.MORE.fetch( timeline );
    
    verify( timeline ).fetchItems();
  }

  @Test
  public void fetchNewWithNull() {
    Throwable actual = thrownBy( () -> FetchOperation.NEW.fetch( null ) );
    
    assertThat( actual )
      .hasMessage( TIMELINE_MUST_NOT_BE_NULL )
      .isInstanceOf( IllegalArgumentException.class );
  }
  
  @Test
  public void fetchMoreWithNull() {
    Throwable actual = thrownBy( () -> FetchOperation.MORE.fetch( null ) );
    
    assertThat( actual )
      .hasMessage( TIMELINE_MUST_NOT_BE_NULL )
      .isInstanceOf( IllegalArgumentException.class );
  }
}