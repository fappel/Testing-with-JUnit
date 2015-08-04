package book.twju.timeline.model;

public enum CompareItemData {
  
  WITH_DIFFERENT_TIME_STAMP_X_TO_Y( new FakeItem( "1", 10L ), new FakeItem( "2", 20L ) ) {},
  WITH_DIFFERENT_TIME_STAMP_Y_TO_Z( new FakeItem( "2", 20L ), new FakeItem( "3", 30L ) ) {},
  WITH_DIFFERENT_TIME_STAMP_X_TO_Z( new FakeItem( "1", 10L ), new FakeItem( "3", 30L ) ) {},
  WITH_SAME_TIME_STAMP_X_TO_Y( new FakeItem( "1", 10L ), new FakeItem( "2", 10L ) ) {},
  WITH_SAME_TIME_STAMP_Y_TO_Z( new FakeItem( "2", 10L ), new FakeItem( "3", 10L ) ) {},
  WITH_SAME_TIME_STAMP_X_TO_Z( new FakeItem( "1", 10L ), new FakeItem( "3", 10L ) ) {};
  
  private final Item x;
  private final Item y;

  CompareItemData( Item x, Item y ) {
    this.x = x;
    this.y = y;
  }
  
  Item getX() {
    return x;
  }
  
  Item getY() {
    return y;
  }
}