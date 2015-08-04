package book.twju.timeline.ui;

import static book.twju.timeline.util.Assertion.checkArgument;
import book.twju.timeline.model.Item;
import book.twju.timeline.model.Timeline;

public enum FetchOperation {
  
  NEW {
    @Override
    public <T extends Item> void fetch( Timeline<T> timeline ) {
      checkArgument( timeline != null, TIMELINE_MUST_NOT_BE_NULL );
      
      timeline.fetchNew();
    }
  },
  
  MORE {
    @Override
    public <T extends Item> void fetch( Timeline<T> timeline ) {
      checkArgument( timeline != null, TIMELINE_MUST_NOT_BE_NULL );

      timeline.fetchItems();
    }
  };
  
  static final String TIMELINE_MUST_NOT_BE_NULL = "Argument 'timeline' must not be null.";

  public abstract <T extends Item> void fetch( Timeline<T> timeline );
}