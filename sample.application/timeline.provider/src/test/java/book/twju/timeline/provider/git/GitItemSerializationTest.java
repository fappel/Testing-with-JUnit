package book.twju.timeline.provider.git;

import static book.twju.timeline.provider.git.GitItemSerialization.INPUT_MUST_NOT_BE_NULL;
import static book.twju.timeline.provider.git.GitItemSerialization.ITEM_MUST_NOT_BE_NULL;
import static book.twju.timeline.test.util.ThrowableCaptor.thrownBy;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

public class GitItemSerializationTest {
  
  private static final String CONTENT = "content";
  private static final String AUTHOR = "author";
  
  private GitItemSerialization serialization;
  
  @Before
  public void setUp() {
    serialization = new GitItemSerialization();
  }

  @Test
  public void serialization() {
    GitItem expected = new GitItem( "id", 2L, AUTHOR, CONTENT );
    
    String serialized = serialization.serialize( expected );
    GitItem actual = serialization.deserialize( serialized );
    
    assertThat( actual )
      .isEqualTo( expected )
      .isNotSameAs( expected );
  }
  
  @Test
  public void serializeWithNullAsItem() {
    Throwable actual = thrownBy( () -> serialization.serialize( null ) );
    
    assertThat( actual )
      .hasMessage( ITEM_MUST_NOT_BE_NULL )
      .isInstanceOf( IllegalArgumentException.class );
  }
  
  @Test
  public void deserializeWithNullAsInput() {
    Throwable actual = thrownBy( () -> serialization.deserialize( null ) );
    
    assertThat( actual )
      .hasMessage( INPUT_MUST_NOT_BE_NULL )
      .isInstanceOf( IllegalArgumentException.class );
  }
}