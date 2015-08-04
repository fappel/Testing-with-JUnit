package book.twju.timeline.util;

import static book.twju.timeline.test.util.ThrowableCaptor.thrownBy;
import static book.twju.timeline.util.Assertion.ARGUMENTS_MUST_NOT_BE_NULL;
import static book.twju.timeline.util.Assertion.MESSAGE_PATTERN_MUST_NOT_BE_NULL;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class AssertionTest {
  
  private static final String MESSAGE_PATTERN = "%s %s message";
  private static final String ARGUMENT_ONE = "one";
  private static final String ARGUMENT_TWO = "two";
  private static final String EXPECTED_MESSAGE = ARGUMENT_ONE + " " + ARGUMENT_TWO + " message";

  @Test
  public void formatErrorMessage() {
    String actual = Assertion.formatErrorMessage( MESSAGE_PATTERN, ARGUMENT_ONE, ARGUMENT_TWO );
    
    assertThat( actual ).isEqualTo( EXPECTED_MESSAGE );
  }
  
  @Test
  public void formatErrorMessageWithoutPatternArguments() {
    String expected = "expected";
    
    String actual = Assertion.formatErrorMessage( expected );
    
    assertThat( actual ).isEqualTo( expected );
  }

  @Test
  public void formatErrorMessageWithNullAsPattern() {
    Throwable actual = thrownBy( () -> Assertion.formatErrorMessage( null, ARGUMENT_ONE, ARGUMENT_TWO ) );

    assertThat( actual )
      .isInstanceOf( IllegalArgumentException.class )
      .hasMessage( MESSAGE_PATTERN_MUST_NOT_BE_NULL );
  }
  
  @Test
  public void formatErrorMessageWithNullAsArguments() {
    Throwable actual = thrownBy( () -> Assertion.formatErrorMessage( MESSAGE_PATTERN, ( Object[] )null ) );
    
    assertThat( actual )
      .isInstanceOf( IllegalArgumentException.class )
      .hasMessage( ARGUMENTS_MUST_NOT_BE_NULL );
  }
  
  @Test
  public void checkArgumentWithValidCondition() {
    Throwable actual = thrownBy( () -> Assertion.checkArgument( true, MESSAGE_PATTERN, ARGUMENT_ONE, ARGUMENT_TWO ) );
    
    assertThat( actual ).isNull();
  }
  
  @Test
  public void checkArgumentWithInvalidCondition() {
    Throwable actual = thrownBy( () -> Assertion.checkArgument( false, MESSAGE_PATTERN, ARGUMENT_ONE, ARGUMENT_TWO ) );
    
    assertThat( actual )
      .isInstanceOf( IllegalArgumentException.class )
      .hasMessage( EXPECTED_MESSAGE );
  }
  
  @Test
  public void checkStateWithValidCondition() {
    Throwable actual = thrownBy( () -> Assertion.checkState( true, MESSAGE_PATTERN, ARGUMENT_ONE, ARGUMENT_TWO ) );
    
    assertThat( actual ).isNull();
  }
  
  @Test
  public void checkStateWithInvalidCondition() {
    Throwable actual = thrownBy( () -> Assertion.checkState( false, MESSAGE_PATTERN, ARGUMENT_ONE, ARGUMENT_TWO ) );
    
    assertThat( actual )
      .isInstanceOf( IllegalStateException.class )
      .hasMessage( EXPECTED_MESSAGE );
  }
}