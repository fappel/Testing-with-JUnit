package book.twju.timeline.provider.git;

import static book.twju.timeline.util.Exceptions.guard;
import static java.lang.String.format;
import static org.eclipse.jgit.api.Git.open;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.errors.RepositoryNotFoundException;

class GitOperator {
  
  static final String DIRECTORY_CONTAINS_NO_GIT_REPOSITORY = "Directory <%s> contains no git repository.";
  
  private final File repositoryLocation;
  
  @FunctionalInterface
  interface GitOperation<T> {
    T execute( Git git ) throws Exception;
  }

  GitOperator( File repositoryLocation ) {
    this.repositoryLocation = repositoryLocation;
    openRepository().close();
  }
  
  <T> T execute( GitOperation<T> gitOperation ) {
     Git git = openRepository();
     try {
       return guarded( () -> gitOperation.execute( git ) );
     } finally {
       git.close();
     }
  }

  static <T> T guarded( Callable<T> callable ) {
    return guard( callable ).with( IllegalStateException.class );
  }

  private Git openRepository() {
    return guarded( () -> openRepository( repositoryLocation ) );
  }

  private static Git openRepository( File repositoryDir ) throws IOException {
    try {
      return open( repositoryDir ); 
    } catch ( RepositoryNotFoundException rnfe ) {
      throw new IllegalArgumentException( format( DIRECTORY_CONTAINS_NO_GIT_REPOSITORY, repositoryDir ), rnfe );
    }
  }
}