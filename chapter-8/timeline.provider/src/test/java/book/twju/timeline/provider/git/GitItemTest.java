package book.twju.timeline.provider.git;

import static book.twju.timeline.provider.git.GitItemAssert.assertThat;
import static book.twju.timeline.test.util.ThrowableCaptor.thrownBy;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import book.twju.timeline.test.util.GitRepository;
import book.twju.timeline.test.util.GitRule;

public class GitItemTest {

  private static final String ID = "id";
  private static final long TIME_STAMP = 1L;
  private static final String AUTHOR = "author";
  private static final String CONTENT = "content";
  
  @Rule public final TemporaryFolder temporaryFolder = new TemporaryFolder();
  @Rule public final GitRule gitRule = new GitRule();
  
  @Test
  public void construct() {
    GitItem actual = new GitItem( ID, TIME_STAMP, AUTHOR, CONTENT );
    
    assertThat( actual )
      .hasId( ID )
      .hasTimeStamp( TIME_STAMP )
      .hasAuthor( AUTHOR )
      .hasContent( CONTENT );
  }
  
  @Test
  public void toStringRepresentation() {
    String actual = new GitItem( ID, TIME_STAMP, AUTHOR, CONTENT ).toString();
    
    assertThat( actual )
      .contains( ID )
      .contains( String.valueOf( TIME_STAMP ) )
      .contains( AUTHOR )
      .contains( CONTENT );
  }
  
  @Test
  public void ofCommit() throws IOException {
    GitRepository repository = gitRule.create( temporaryFolder.newFolder() );
    RevCommit commit = repository.commitFile( "file", "content", "message"  );
   
    GitItem actual = GitItem.ofCommit( commit );
    
    assertThat( actual )
      .hasId( GitItem.getId( commit ) )
      .hasTimeStamp( GitItem.getTimeStamp( commit ) )
      .hasContent(  GitItem.getContent( commit ) )
      .hasAuthor( GitItem.getAuthor( commit ) );
  }
  
  @Test
  public void constructWithNullAsAuthor() {
    Throwable actual = thrownBy( () -> new GitItem( ID, TIME_STAMP, null, CONTENT ) );
    
    assertThat( actual )
      .hasMessage( GitItem.AUTHOR_MUST_NOT_BE_NULL )
      .isInstanceOf( IllegalArgumentException.class );
  }
  
  @Test
  public void constructWithNullAsContent() {
    Throwable actual = thrownBy( () -> new GitItem( ID, TIME_STAMP, AUTHOR, null ) );
    
    assertThat( actual )
      .hasMessage( GitItem.CONTENT_MUST_NOT_BE_NULL )
      .isInstanceOf( IllegalArgumentException.class );
  }
}