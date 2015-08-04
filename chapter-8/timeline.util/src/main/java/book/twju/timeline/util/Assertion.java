package book.twju.timeline.util;

import static java.lang.String.format;

public class Assertion {

  static final String MESSAGE_PATTERN_MUST_NOT_BE_NULL = "Argument 'messagePattern' must not be null.";
  static final String ARGUMENTS_MUST_NOT_BE_NULL = "Argument 'arguments' must not be null.";

  public static void checkArgument( boolean condition, String messagePattern, Object ... arguments ) {
    if( !condition ) {
      throw new IllegalArgumentException( formatErrorMessage( messagePattern, arguments ) );
    }
  }
  
  public static void checkState( boolean condition, String messagePattern, Object ... arguments) {
    if( !condition ) {
      throw new IllegalStateException( formatErrorMessage( messagePattern, arguments ) );
    }
  }

  public static String formatErrorMessage( String messagePattern, Object... arguments ) {
    checkArgument( messagePattern != null, MESSAGE_PATTERN_MUST_NOT_BE_NULL );
    checkArgument( arguments != null, ARGUMENTS_MUST_NOT_BE_NULL );
    return format( messagePattern, arguments );
  }
}