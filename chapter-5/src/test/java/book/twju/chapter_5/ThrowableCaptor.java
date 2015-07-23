package book.twju.chapter_5;

public class ThrowableCaptor {
  
  @FunctionalInterface
  public interface Actor {
    void act() throws Throwable;
  }
 
  public static Throwable thrownBy( Actor actor ) {
    try {
      actor.act();
    } catch( Throwable throwable ) {
      return throwable;
    }
    return null;
  }
}