package book.twju.chapter_2;

public class Timeline {

  public static final int FETCH_COUNT_LOWER_BOUND = 1;
  public static final int FETCH_COUNT_UPPER_BOUND = 20;

  static final int DEFAULT_FETCH_COUNT = 10;

  private int fetchCount;
  
  public Timeline() {
    fetchCount = DEFAULT_FETCH_COUNT;
  }

  public void setFetchCount( int fetchCount ) {
    if(    fetchCount >= FETCH_COUNT_LOWER_BOUND 
        && fetchCount <= FETCH_COUNT_UPPER_BOUND )
    {
      this.fetchCount = fetchCount;
    }
  }

  public int getFetchCount() {
    return fetchCount;
  }
  
  public void dispose() {
    // Note: this method only for teardown handling demonstration and will be removed
  }
}