package book.twju.timeline.util;

import static java.lang.String.format;

import java.util.concurrent.Callable;

public class Exceptions<V> {
  
  private Callable<V> callable;

  public static <V> Exceptions<V> guard( Callable<V> callable ) {
    return new Exceptions<>( callable );
  }
  
  public <T extends RuntimeException> V with( Class<T> targetType ) {
    try {
      return callable.call();
    } catch( RuntimeException rte ) {
      throw rte;
    } catch( Exception cause ) {
      throw createExceptionEnvelope( targetType, cause );
    }
  }

  private static <T extends RuntimeException> T createExceptionEnvelope( Class<T> targetType, Exception cause ) {
    try {
      return targetType.getConstructor( Throwable.class ).newInstance( cause );
    } catch( Exception e ) {
      throw new IllegalArgumentException( createProblemMessage( targetType, cause ), e );
    }
  }

  private static <T extends RuntimeException> String createProblemMessage( Class<T> targetType, Exception cause ) {
    return format( "Target exception type <%s> cannot be instanciated to defuse checked exception <%s[%s]>.",
                   targetType.getName(),
                   cause.getClass().getName(), 
                   cause.getMessage() );
  }
  
  private Exceptions( Callable<V> callable ) {
    this.callable = callable;
  }
}