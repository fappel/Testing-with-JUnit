package book.twju.chapter_5;

public class FetchItemsData {
  
  private int fetchCount;
  private Item[] input;
  private Item[] output;

  public static FetchItemsData newFetchItemsData() {
    return new FetchItemsData();
  }

  public FetchItemsData withInput( Item ... input ) {
    this.input = input;
    return this;
  }

  public FetchItemsData withFetchCount( int fetchCount ) {
    this.fetchCount = fetchCount;
    return this;
  }

  public FetchItemsData withOutput( Item ... output ) {
    this.output = output;
    return this;
  }
  
  public int getFetchCount() {
    return fetchCount;
  }
  
  public Item[] getInput() {
    return input;
  }
  
  public Item[] getOutput() {
    return output;
  }
}