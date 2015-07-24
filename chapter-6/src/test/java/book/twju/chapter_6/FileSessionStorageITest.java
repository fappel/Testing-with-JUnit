package book.twju.chapter_6;

import static book.twju.chapter_6.ThrowableCaptor.thrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class FileSessionStorageITest {
  
  private static final String CONTENT = "content";

  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();
  
  private FileSessionStorage storage;
  private File storageLocation;
  
  @Before
  public void setUp() throws IOException {
    storageLocation = temporaryFolder.newFile();
    storage = new FileSessionStorage( storageLocation );
  }
  
  @Test
  public void store() throws IOException {
    Memento memento = new Memento( CONTENT );
    
    storage.store( memento );
    
    assertEquals( CONTENT, readStoredContent() );
  }
  
  @Test
  public void storeIfStorageLocationDoesNotExist() throws IOException {
    storageLocation.delete();
    
    Throwable actual = thrownBy( () -> storage.store( new Memento( CONTENT ) ) );
    
    assertTrue( actual instanceof IllegalStateException );
    assertTrue( actual.getCause() instanceof IOException );
  }

  @Test
  public void read() throws IOException {
    writeContentToStore( CONTENT );
    
    Memento memento = storage.read();
    
    assertEquals( CONTENT, memento.toString() );
  }

  @Test
  public void readIfStorageLocationDoesNotExist() {
    storageLocation.delete();

    Throwable actual = thrownBy( () -> storage.read() );

    assertTrue( actual instanceof IllegalStateException );
    assertTrue( actual.getCause() instanceof IOException );
  }
  
  @Test( expected = IllegalArgumentException.class )
  public void constructWithNullAsStorageLocation() {
    new FileSessionStorage( null ); 
  }
  
  private String readStoredContent() throws IOException {
    byte[] bytes = Files.readAllBytes( storageLocation.toPath() );
    return new String( bytes, StandardCharsets.UTF_8 );
  }

  private Path writeContentToStore( String content )
	 throws IOException
  {
    byte[] bytes = content.getBytes( StandardCharsets.UTF_8 );
    return Files.write( storageLocation.toPath(), bytes );
  }
}