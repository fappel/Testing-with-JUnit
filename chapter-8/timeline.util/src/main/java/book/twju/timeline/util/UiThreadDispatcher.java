package book.twju.timeline.util;

public interface UiThreadDispatcher {
  void dispatch( Runnable runnable );
}