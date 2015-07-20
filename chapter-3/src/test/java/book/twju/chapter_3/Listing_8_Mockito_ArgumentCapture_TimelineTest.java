package book.twju.chapter_3;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class Listing_8_Mockito_ArgumentCapture_TimelineTest {
  
  private static final FakeItem FIRST_ITEM = new FakeItem( 10 );
  private static final FakeItem SECOND_ITEM = new FakeItem( 20 );

  private SessionStorage sessionStorage;
  private ItemProviderStub itemProvider;
  private Timeline timeline;

  @Before
  public void setUp() {
    itemProvider = new ItemProviderStub();
    sessionStorage = mock( SessionStorage.class );
    timeline = new Timeline( itemProvider, sessionStorage );
  }
  
  @Test
  public void fetchFirstItemsWithArgumentCaptureExample() {
    itemProvider.addItems( FIRST_ITEM, SECOND_ITEM );
    timeline.setFetchCount( 1 );
      
    timeline.fetchItems();

    // Note that we go with item here since Memento has not been defined yet
    ArgumentCaptor<Item> captor = forClass( Item.class );
    verify( sessionStorage ).storeTop( captor.capture() );
    assertEquals( SECOND_ITEM, captor.getValue() );
  }
}