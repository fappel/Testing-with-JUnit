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
import book.twju.timeline.provider.git.GitRepository;
import book.twju.timeline.provider.git.GitRule;
import book.twju.timeline.swing.SwingTimeline;

public class GitTimelineFactoryITest {
  
  private static final String CLONE_NAME = "clone";
  
  @Rule public final TemporaryFolder temporaryFolder = new TemporaryFolder();
  @Rule public final GitRule gitRule = new GitRule();
  
  private File baseDirectory;
  private File remoteLocation;
  
  @Before
  public void setUp() throws IOException {
    baseDirectory = temporaryFolder.newFolder();
    remoteLocation = createRepository();
  }
  
  @Test
  public void createTimeline() throws IOException {
    File timelineFolder = new File( baseDirectory, GitTimelineFactory.TIMELINE_FOLDER );
    File sessionStore = new File( timelineFolder, GitTimelineFactory.SESSION_STORE );
    File repository = new File( timelineFolder, CLONE_NAME );
    String uri = remoteLocation.toURI().toString();

    SwingTimeline<GitItem> actual = GitTimelineFactory.createTimeline( baseDirectory.getPath(), uri, CLONE_NAME );
   
    assertThat( actual ).isNotNull();
    assertThat( timelineFolder ).exists();
    assertThat( sessionStore ).exists();
    assertThat( repository ).exists();
    assertThat( storedMemento( sessionStore ) ).isNotEmpty();
  }

  private String storedMemento( File storageLocation ) throws IOException {
    byte[] bytes = readAllBytes( storageLocation.toPath() );
    return new String( bytes, StandardCharsets.UTF_8 );
  }

  private File createRepository() throws IOException {
    File result = new File( baseDirectory, "repository.git" );
    GitRepository remote = gitRule.create( result );
    remote.commitFile( "file", "content", "message" );
    return result;
  }
}