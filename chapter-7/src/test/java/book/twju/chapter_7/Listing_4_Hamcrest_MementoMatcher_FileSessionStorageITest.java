package book.twju.chapter_7;

import static book.twju.chapter_7.FakeItems.ALL_ITEMS;
import static book.twju.chapter_7.FakeItems.FIRST_ITEM;
import static book.twju.chapter_7.Listing_4_Hamcrest_MementoMatcher.equalTo;
import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class Listing_4_Hamcrest_MementoMatcher_FileSessionStorageITest {
  
  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();
  
  private ItemSerialization itemSerialization;
  private FileSessionStorage storage;
  private File storageLocation;

  @Before
  public void setUp() throws IOException {
    storageLocation = temporaryFolder.newFile();
    itemSerialization = new FakeItemSerialization();
    storage = new FileSessionStorage( storageLocation, itemSerialization );
  }
  
  @Test
  public void storage() throws IOException {
    Memento expected = new Memento( ALL_ITEMS, FIRST_ITEM );
    
    storage.store( expected );
    Memento actual = storage.read();
    
    assertThat( "Memento has not been written to disk.",
                storedMemento().length > 0 );
    assertThat( actual, is( both( equalTo( expected ) ).and( not( sameInstance( expected ) ) ) ) );
  }
  
  private byte[] storedMemento() throws IOException {
    return Files.readAllBytes( storageLocation.toPath() );
  }
}