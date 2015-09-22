package book.twju.timeline.provider.git;

import static book.twju.timeline.test.util.FileHelper.delete;
import static book.twju.timeline.test.util.ThrowableCaptor.thrownBy;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import book.twju.timeline.test.util.GitRepository;
import book.twju.timeline.test.util.GitRule;

public class GitOperatorITest {

  private static final int COMMIT_COUNTS = 10;
  
  @Rule public final TemporaryFolder temporaryFolder = new TemporaryFolder();
  @Rule public final GitRule gitRule = new GitRule();

  @Test
  public void execute() throws IOException {
    GitOperator operator = new GitOperator( createRepository() );
    
    Iterable<RevCommit> actual = operator.execute( git -> git.log().call() );

    assertThat( actual ).hasSize( COMMIT_COUNTS );
  }
  
  @Test
  public void executeWithProblem() throws IOException {
    GitOperator operator = new GitOperator( createRepository() );
    Exception cause = new Exception();
    
    Throwable actual = thrownBy( () -> operator.execute( git -> { throw new Exception(); } ) );
    
    assertThat( actual )
      .hasCause( cause )
      .isInstanceOf( IllegalStateException.class );
  }
  
  @Test
  public void executeWithEmptyRepositoryLocation() throws IOException {
    File repositoryLocation = createRepository();
    GitOperator operator = new GitOperator( repositoryLocation );
    asList( repositoryLocation.listFiles() ).forEach( file -> delete( file ) );
     
    Throwable actual = thrownBy( () -> operator.execute( git -> git.log().call() ) );
    
    assertThat( actual )
      .hasCauseInstanceOf( RepositoryNotFoundException.class )
      .hasMessageContaining( repositoryLocation.toString() )
      .isInstanceOf( IllegalArgumentException.class );
  }
  
  @Test
  public void constructWithEmptyRepositoryLocation() throws IOException {
    File repositoryLocation = temporaryFolder.newFolder();
    
    Throwable actual = thrownBy( () -> new GitOperator( repositoryLocation ) );
    
    assertThat( actual )
      .hasCauseInstanceOf( RepositoryNotFoundException.class )
      .hasMessageContaining( repositoryLocation.toString() )
      .isInstanceOf( IllegalArgumentException.class );
  }

  @Test
  public void guarded() {
    Exception cause = new Exception();
    
    Throwable actual = thrownBy( () -> GitOperator.guarded( () -> { throw new Exception(); } ) );
    
    assertThat( actual )
      .hasCause( cause )
      .isInstanceOf( IllegalStateException.class );
  }

  private File createRepository() throws IOException {
    File result = temporaryFolder.newFolder();
    GitRepository repository = gitRule.create( result );
    for (int i = 0; i < COMMIT_COUNTS; i++) {
      repository.commitFile( "f" + i, "c" + i, "m" + i );
    }
    return result;
  }
}