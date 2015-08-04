package book.twju.chapter_7;

import static java.lang.Long.parseLong;

public class FakeItemSerialization implements ItemSerialization {

  @Override
  public String serialize( Item item ) {
    return String.valueOf( item.getTimeStamp() );
  }

  @Override
  public Item deserialize( String input ) {
    return new FakeItem( parseLong( input ) );
  }
}