package book.twju.chapter_7;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class Listing_5_AssertJ_ThrowableAssert_Test {

  private static final String EXPECTED_ERROR_MESSAGE = "bad";

  @Test
  public void throwableAssertionDemo() {
    String description = "Expected exception does not match specification.";

    Throwable actual = new NullPointerException( "bad" );
        
    assertThat( actual )
      .describedAs( description )
      .hasMessage( EXPECTED_ERROR_MESSAGE )
      .isInstanceOf( NullPointerException.class ); 
  }
}