package book.twju.timeline.util;

import static book.twju.timeline.util.Assertion.checkArgument;

import java.util.ArrayList;
import java.util.List;

public class Iterables {

  static final String ITERABLE_MUST_NOT_BE_NULL = "Argument 'iterable' must not be null.";
  
  public static <T> List<T> asList( Iterable<T> iterable ) {
    checkArgument( iterable != null, ITERABLE_MUST_NOT_BE_NULL );
    
    List<T> result = new ArrayList<>();
    for( T element : iterable ) {
      result.add( element );
    }
    return result;
  }

  private Iterables() {}
}