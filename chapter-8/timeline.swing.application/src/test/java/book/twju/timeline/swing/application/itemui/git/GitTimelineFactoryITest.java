package book.twju.timeline.swing.application.itemui.git;

import static java.nio.file.Files.readAllBytes;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import book.twju.timeline.provider.git.GitItem;
import book.twju.timeline.swing.SwingTimeline;
import book.twju.timeline.test.util.GitRepository;
import book.twju.timeline.test.util.GitRule;
import book.twju.timeline.util.FileStorageStructure;

public class GitTimelineFactoryITest {
  
  private static final String CLONE_NAME = "clone";
  
  @Rule public final TemporaryFolder temporaryFolder = new TemporaryFolder();
  @Rule public final GitRule gitRule = new GitRule();
  
  private FileStorageStructure storageStructure;
  private String remoteRepositoryUri;
  
  @Before
  public void setUp() throws IOException {
    File baseDirectory = temporaryFolder.newFolder();
    storageStructure = new FileStorageStructure( baseDirectory );
    remoteRepositoryUri = createRepository( baseDirectory );
  }
  
  @Test
  public void create() throws IOException {
    GitTimelineFactory factory = new GitTimelineFactory( storageStructure );

    SwingTimeline<GitItem> actual = factory.create( remoteRepositoryUri, CLONE_NAME );
   
    assertThat( actual ).isNotNull();
    assertThat( cloneLocation() ).exists();
    assertThat( storedMemento() ).isNotEmpty();
  }

  private File cloneLocation() {
    return new File( storageStructure.getTimelineDirectory(), CLONE_NAME );
  }

  private String storedMemento() throws IOException {
    byte[] bytes = readAllBytes( storageStructure.getStorageFile().toPath() );
    return new String( bytes, StandardCharsets.UTF_8 );
  }

  private String createRepository( File baseDirectory ) throws IOException {
    File remoteRepositoryLocation = new File( baseDirectory, "repository.git" );
    GitRepository remote = gitRule.create( remoteRepositoryLocation );
    remote.commitFile( "file", "content", "message" );
    return remoteRepositoryLocation.toURI().toString();
  }
}