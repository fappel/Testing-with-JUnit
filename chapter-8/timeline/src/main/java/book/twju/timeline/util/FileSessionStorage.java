package book.twju.timeline.util;

import static book.twju.timeline.util.Assertion.checkArgument;
import static book.twju.timeline.util.Exceptions.guard;
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
import java.util.Optional;
import java.util.Set;

import book.twju.timeline.model.Item;
import book.twju.timeline.model.ItemSerialization;
import book.twju.timeline.model.Memento;
import book.twju.timeline.model.SessionStorage;

public class FileSessionStorage<T extends Item> implements SessionStorage<T> {

  static final String STORAGE_LOCATION_MUST_NOT_BE_NULL = "Argument 'storageLocation' must not be null.";
  static final String ITEM_SERIALIZATION_NUST_NOT_BE_NULL = "Argument 'itemSerialization' nust not be null.";
  static final String STORAGE_LOCATION_IS_NOT_ACCESSIBLE
    = "File <%s> is not accessible, meaning it may not exist or isn't read or writable.";

  private static final String ITEM_SEPARATOR = "@;@@@;@";
  
  private final ItemSerialization<T> itemSerialization;
  private final File storageLocation;
  
  public FileSessionStorage( File storageLocation, ItemSerialization<T> itemSerialization  ) {
    checkArgument( storageLocation != null, STORAGE_LOCATION_MUST_NOT_BE_NULL );
    checkArgument( itemSerialization != null, ITEM_SERIALIZATION_NUST_NOT_BE_NULL, itemSerialization );
    checkArgument( isAccessibleFile( storageLocation ), STORAGE_LOCATION_IS_NOT_ACCESSIBLE, storageLocation );
    
    this.storageLocation = storageLocation;
    this.itemSerialization = itemSerialization;
  }

  @Override
  public void store( Memento<T> memento ) {
    String storageContent = serialize( memento );
    guard( () -> writeStorageContent( storageContent ) ).with( IllegalStateException.class );
  }

  @Override
  public Memento<T> read() {
    String storageContent = guard( () -> readStorageContent() ).with( IllegalStateException.class );
    return deserialize( storageContent );
  }
  
  private String serialize( Memento<T> memento ) {
    StringBuilder result = new StringBuilder();
    if( !memento.getItems().isEmpty() ) {
      result.append( serializeItems( memento ) );
      result.append( itemSerialization.serialize( memento.getTopItem().get() ) );
    }
    return result.toString();
  }

  private StringBuilder serializeItems( Memento<T> memento ) {
    StringBuilder result = new StringBuilder();
    memento.getItems().forEach( item -> result.append( itemSerialization.serialize( item ) ).append( ITEM_SEPARATOR ) );
    return result;
  }

  private Memento<T> deserialize( String content ) {
    if( content.isEmpty() ) {
      return Memento.empty();
    }
    List<String> elements = new ArrayList<String>( asList( content.split( ITEM_SEPARATOR ) ) );
    String topItemElement = elements.remove( elements.size() -1 );
    Set<T> items = deserializeItems( elements );
    T topItem = itemSerialization.deserialize( topItemElement );
    return new Memento<>( items, Optional.of( topItem ) );
  }

  private Set<T> deserializeItems( List<String> elements ) {
    return elements.stream().map( element -> itemSerialization.deserialize( element ) ).collect( toSet() );
  }
  
  private Path writeStorageContent( String content ) throws IOException {
    return write( storageLocation.toPath(), content.getBytes( UTF_8 ), WRITE, TRUNCATE_EXISTING );
  }
  
  private String readStorageContent() throws IOException {
    return new String( readAllBytes( storageLocation.toPath() ), UTF_8 );
  }
  
  private static boolean isAccessibleFile( File file ) {
    return file.exists() && file.canRead() && file.canWrite() && file.isFile();
  }
}