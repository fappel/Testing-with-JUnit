package book.twju.timeline.swing.application.itemui.git;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;

import org.assertj.core.util.Files;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import book.twju.timeline.provider.git.GitItem;
import book.twju.timeline.swing.SwingTimeline;

public class GitTimelineFactoryITest {
  
  @Rule
  public final TemporaryFolder temporaryFolder = new TemporaryFolder();
  
  private File baseDirectory;
  
  @Before
  public void setUp() throws IOException {
    baseDirectory = temporaryFolder.newFolder();
  }
  
  @After
  public void tearDown() {
    Files.delete( baseDirectory );
  }
  
  @Test
  public void createTimeline() throws IOException {
    File timelineFolder = new File( baseDirectory, GitTimelineFactory.TIMELINE_FOLDER );
    File sessionStore = new File( timelineFolder, GitTimelineFactory.SESSION_STORE );
    File repository = new File( timelineFolder, GitTimelineFactory.REPOSITORY_NAME );

    SwingTimeline<GitItem> actual = GitTimelineFactory.createTimeline( baseDirectory.getPath()  );
   
    assertThat( actual ).isNotNull();
    assertThat( timelineFolder ).exists();
    assertThat( sessionStore ).exists();
    assertThat( repository ).exists();
  }
}