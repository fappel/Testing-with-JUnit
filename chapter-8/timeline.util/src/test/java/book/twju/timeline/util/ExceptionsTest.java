package book.twju.timeline.util;

import static book.twju.timeline.test.util.ThrowableCaptor.thrownBy;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import book.twju.timeline.util.Exceptions;

public class ExceptionsTest {

  @Test
  public void guard() {
    String expected = "expected";
    
    String actual = Exceptions.guard( () -> expected ).with( IllegalArgumentException.class );
    
    assertThat( actual ).isEqualTo( expected );
  }
  
  @Test
  public void guardIfLambdaThrowsException() {
    Exception cause = new Exception();
    Class<IllegalStateException> targetType = IllegalStateException.class;
    
    Throwable actual = thrownBy( () -> Exceptions.guard( () -> { throw cause; } ).with( targetType ) );
    
    assertThat( actual )
      .hasCause( cause )
      .isInstanceOf( targetType );
  }

  @Test
  public void guardIfLambdaThrowsRuntimeException() {
    Exception cause = new RuntimeException();
    
    Throwable actual = thrownBy( () -> Exceptions.guard( () -> { throw cause; } ).with( IllegalStateException.class ) );
    
    assertThat( actual )
      .hasNoCause()
      .isEqualTo( cause );
  }
  
  @Test
  public void guardIfTargetTypeCannotBeInstantiated() {
    Exception cause = new Exception();
    Class<? extends RuntimeException> targetType = createInvalidTargetType();
    
    Throwable actual = thrownBy( () -> Exceptions.guard( () -> { throw cause; } ).with( targetType ) );
    
    assertThat( actual )
      .hasMessageContaining( targetType.getName() )
      .hasMessageContaining( cause.getClass().getName() )
      .hasMessageContaining( String.valueOf( cause.getMessage() ) )
      .hasCauseInstanceOf( NoSuchMethodException.class )
      .isInstanceOf( IllegalArgumentException.class );
  }

  @SuppressWarnings("serial")
  private Class<? extends RuntimeException> createInvalidTargetType() {
    return new RuntimeException() {}.getClass();
  }
}