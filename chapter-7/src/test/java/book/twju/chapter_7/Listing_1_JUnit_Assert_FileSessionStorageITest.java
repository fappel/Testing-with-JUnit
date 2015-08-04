package book.twju.chapter_7;

import static book.twju.chapter_7.FakeItems.ALL_ITEMS;
import static book.twju.chapter_7.FakeItems.FIRST_ITEM;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class Listing_1_JUnit_Assert_FileSessionStorageITest {
  
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
    
    assertEquals( expected.getTopItem(),
                  actual.getTopItem() );
    assertEquals( expected.getItems(), 
                  actual.getItems() );
    assertNotSame( expected, actual );
    assertTrue( storedMemento().length > 0 );
  }
  
  private byte[] storedMemento() throws IOException {
    return Files.readAllBytes( storageLocation.toPath() );
  }
}