package book.twju.timeline.test.util;

public class ThreadHelper {

  public static void sleep( int delay ) {
    try {
      Thread.sleep( delay + 100 );
    } catch( InterruptedException shouldNotHappen ) {
      throw new IllegalStateException( shouldNotHappen );
    }
  }
}