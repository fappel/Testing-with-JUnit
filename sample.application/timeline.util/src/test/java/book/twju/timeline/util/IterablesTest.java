package book.twju.timeline.util;

import static book.twju.timeline.test.util.ThrowableCaptor.thrownBy;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import book.twju.timeline.util.Iterables;

public class IterablesTest {
  
  @Test
  public void asList() {
    String[] expected = new String[] { "A", "B", "C" };
    Iterable<String> iterable = Arrays.asList( expected );
    
    List<String> actual = Iterables.asList( iterable );
    
    assertThat( actual )
      .isNotSameAs( iterable )
      .containsExactly( expected );
  }
  
  @Test
  public void asListWithNullAsIterable() {
    Throwable actual = thrownBy( () -> Iterables.asList( null ) );
    
    assertThat( actual )
      .hasMessage( Iterables.ITERABLE_MUST_NOT_BE_NULL )
      .isInstanceOf( IllegalArgumentException.class );
  }
}