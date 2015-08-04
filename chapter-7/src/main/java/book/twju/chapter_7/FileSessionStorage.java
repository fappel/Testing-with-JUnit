package book.twju.chapter_7;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Files.write;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toSet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FileSessionStorage implements SessionStorage {

  private static final String ITEM_SEPARATOR = "@";
  
  private final ItemSerialization itemSerialization;
  private final File storageLocation;

  public FileSessionStorage( File storageLocation, ItemSerialization itemSerialization ) {
    this.storageLocation = storageLocation;
    this.itemSerialization = itemSerialization;
  }

  @Override
  public void store( Memento memento ) {
    writeStorageContent( serialize( memento ) );
  }

  @Override
  public Memento read() {
    return deserialize( readStorageContent() );
  }
  
  private String serialize( Memento memento ) {
    StringBuilder result = new StringBuilder();
    result.append( serializeItems( memento ) );
    result.append( itemSerialization.serialize( memento.getTopItem() ) );
    return result.toString();
  }

  private StringBuilder serializeItems( Memento memento ) {
    StringBuilder result = new StringBuilder();
    memento.getItems()
      .forEach( item -> result.append( itemSerialization.serialize( item ) ).append( ITEM_SEPARATOR ) );
    return result;
  }

  private Memento deserialize( String content ) {
    List<String> elements = new ArrayList<String>( asList( content.split( ITEM_SEPARATOR ) ) );
    String topItemElement = elements.remove( elements.size() -1 );
    Set<? extends Item> items = deserializeItems( elements );
    Item topItem = itemSerialization.deserialize( topItemElement );
    return new Memento( items, topItem );
  }

  private Set<? extends Item> deserializeItems( List<String> elements ) {
    return elements.stream().map( element -> itemSerialization.deserialize( element ) ).collect( toSet() );
  }
  
  private Path writeStorageContent( String content ) {
    try {
      return write( storageLocation.toPath(), content.getBytes( UTF_8 ), WRITE, TRUNCATE_EXISTING );
    } catch( IOException cause ) {
      throw new IllegalStateException( cause );
    }
  }
  
  private String readStorageContent() {
    try {
      return new String( readAllBytes( storageLocation.toPath() ), UTF_8 );
    } catch( IOException cause ) {
      throw new IllegalStateException( cause );
    }
  }
}