package book.twju.chapter_5;

import static book.twju.chapter_5.FakeItems.FIRST_ITEM;
import static book.twju.chapter_5.FakeItems.SECOND_ITEM;
import static book.twju.chapter_5.FakeItems.THIRD_ITEM;

public enum FetchItemsEnum {

  ON_LOWER_FETCH_COUNT_BOUND {
    @Override
    void init() {
      withInput( FIRST_ITEM, SECOND_ITEM, THIRD_ITEM );
      withFetchCount( Timeline.FETCH_COUNT_LOWER_BOUND );
      withOutput( THIRD_ITEM, SECOND_ITEM );
    }
  },
  
  ON_FETCH_COUNT_EXCEEDS_ITEM_COUNT {
    @Override
    void init() {
      withInput( FIRST_ITEM, SECOND_ITEM, THIRD_ITEM );
      withFetchCount( 2 );
      withOutput( THIRD_ITEM, SECOND_ITEM, FIRST_ITEM );
    }
  };

  private int fetchCount;
  private Item[] input;
  private Item[] output;

  FetchItemsEnum() {
    init();  
  }
  
  abstract void init();
  
  public int getFetchCount() {
    return fetchCount;
  }
  
  public Item[] getInput() {
    return input;
  }
  
  public Item[] getOutput() {
    return output;
  }

  FetchItemsEnum withInput( Item ... input ) {
    this.input = input;
    return this;
  }

  FetchItemsEnum withFetchCount( int fetchCount ) {
    this.fetchCount = fetchCount;
    return this;
  }

  FetchItemsEnum withOutput( Item ... output ) {
    this.output = output;
    return this;
  }
}