package book.twju.chapter_6;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.write;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileSessionStorage implements SessionStorage {

  private File storageLocation;

  public FileSessionStorage( File storageLocation ) {
    checkStorageLocation( storageLocation );
    
    this.storageLocation = storageLocation;
  }

  @Override
  public void store( Memento memento ) {
    writeToFile( memento.toString().getBytes( UTF_8 ) );
  }

  @Override
  public Memento read() {
    return new Memento( new String( readFromFile(), UTF_8 ) );
  }
  
  private void writeToFile( byte[] bytes ) {
    try {
      write( storageLocation.toPath(), bytes, TRUNCATE_EXISTING, WRITE );
    } catch( IOException cause ) {
      throw new IllegalStateException( cause );
    }
  }
  
  private byte[] readFromFile() {
    try {
      return Files.readAllBytes( storageLocation.toPath() );
    } catch( IOException cause ) {
      throw new IllegalStateException( cause );
    }
  }
  
  private void checkStorageLocation( File storageLocation ) {
    if( storageLocation == null ) {
      throw new IllegalArgumentException( "Argument 'storageLocation' must not be null." );
    }
  }
}