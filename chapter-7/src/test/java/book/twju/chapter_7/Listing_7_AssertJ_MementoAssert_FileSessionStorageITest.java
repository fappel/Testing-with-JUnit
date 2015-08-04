package book.twju.chapter_7;

import static book.twju.chapter_7.FakeItems.ALL_ITEMS;
import static book.twju.chapter_7.FakeItems.FIRST_ITEM;
import static book.twju.chapter_7.Listing_7_AssertJ_MementoAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class Listing_7_AssertJ_MementoAssert_FileSessionStorageITest {
  
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
    
    assertThat( storedMemento() ).isNotEmpty();
    assertThat( actual )
      .isEqualTo( expected )
      .isNotSameAs( expected );
  }
  
  private byte[] storedMemento() throws IOException {
    return Files.readAllBytes( storageLocation.toPath() );
  }
}