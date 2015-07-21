package book.twju.chapter_4;

import static book.twju.chapter_4.ThrowableCaptor.thrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class Listing_5_Collaborator_TimelineTest {

  private Timeline timeline;
  private SessionStorage storage;

  @Before
  public void setUp() {
    storage = mock( SessionStorage.class );
    ItemProvider itemProvider = mock( ItemProvider.class );
    timeline = new Timeline( itemProvider, storage );
  }
  
  @Test
  public void fetchItemWithExceptionOnStoreTop()
    throws IOException
  {
    IOException cause = new IOException();
    doThrow( cause ).when( storage ).storeTop( any( Item.class ) );
       
    Throwable actual = thrownBy( () -> timeline.fetchItems() );

    assertNotNull( actual );
    assertTrue( actual instanceof IllegalStateException );
    assertSame( cause, actual.getCause() );
    assertEquals( Timeline.ERROR_STORE_TOP, actual.getMessage() );
  }
  
  @Test
  public void fetchItemWithRuntimeExceptionOnStoreTop()
      throws IOException
  {
    RuntimeException cause = new RuntimeException();
    doThrow( cause ).when( storage ).storeTop( any( Item.class ) );
    
    Throwable actual = thrownBy( () -> timeline.fetchItems() );
    
    assertNotNull( actual );
    assertSame( cause, actual );
  }
  
  @Test
  public void fetchItemWithExceptionOnReadTop()
      throws IOException
  {
    IOException cause = new IOException();
    doThrow( cause ).when( storage ).readTop();
    
    Throwable actual = thrownBy( () -> timeline.fetchItems() );
    
    assertNotNull( actual );
    assertTrue( actual instanceof IllegalStateException );
    assertSame( cause, actual.getCause() );
    assertEquals( Timeline.ERROR_READ_TOP, actual.getMessage() );
  }
  
  @Test
  public void fetchItemWithRuntimeExceptionOnReadTop()
      throws IOException
  {
    RuntimeException cause = new RuntimeException();
    doThrow( cause ).when( storage ).readTop();
    
    Throwable actual = thrownBy( () -> timeline.fetchItems() );
    
    assertNotNull( actual );
    assertSame( cause, actual );
  }
}