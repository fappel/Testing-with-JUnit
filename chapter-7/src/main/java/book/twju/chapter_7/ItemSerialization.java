package book.twju.chapter_7;

public interface ItemSerialization {
  String serialize( Item item );
  Item deserialize( String input );
}