package book.twju.chapter_5;

public class Listing_3_JUnitParams_FetchItemsDataProvider {

  private static final FakeItem FIRST_ITEM = new FakeItem( 10 );
  private static final FakeItem SECOND_ITEM = new FakeItem( 20 );
  private static final FakeItem THIRD_ITEM = new FakeItem( 30 );
  
  public static Object[] provideData() {
    return new Object[] {
      fetchItemsOnLowerFetchCountBound(),
      fetchItemsIfFetchCountExceedsItemCount()
    };
  }

  static FetchItemsData fetchItemsOnLowerFetchCountBound() {
    return FetchItemsData.newFetchItemsData()
      .withInput( FIRST_ITEM, SECOND_ITEM, THIRD_ITEM )
      .withFetchCount( Timeline.FETCH_COUNT_LOWER_BOUND )
      .withOutput( THIRD_ITEM, SECOND_ITEM );
  }
  
  static FetchItemsData fetchItemsIfFetchCountExceedsItemCount() {
    return FetchItemsData.newFetchItemsData()
      .withInput( FIRST_ITEM, SECOND_ITEM, THIRD_ITEM )
      .withFetchCount( 2 )
      .withOutput( THIRD_ITEM, SECOND_ITEM, FIRST_ITEM );
  }
}