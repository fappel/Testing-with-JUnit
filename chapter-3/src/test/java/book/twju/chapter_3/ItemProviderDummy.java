package book.twju.chapter_3;

import java.util.List;

public class ItemProviderDummy implements ItemProvider {

  private static String MESSAGE
    = "Dummy method must never be called.";

  @Override
  public List<Item> fetchItems( Item ancestor, int fetchCount ) {
    throw new UnsupportedOperationException( MESSAGE );
  }

  @Override
  public int getNewCount( Item predecessor ) {
    throw new UnsupportedOperationException( MESSAGE );
  }

  @Override
  public List<Item> fetchNew( Item predecessor ) {
    throw new UnsupportedOperationException( MESSAGE );
  }
}