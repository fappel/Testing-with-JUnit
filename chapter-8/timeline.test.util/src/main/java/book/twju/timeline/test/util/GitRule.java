package book.twju.timeline.test.util;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.InitCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.rules.ExternalResource;

public class GitRule extends ExternalResource {

  private final Set<GitRepository> repositories;

  public GitRule() {
    repositories = new HashSet<>();
  }
  
  @Override
  protected void after() {
    repositories.forEach( repository -> repository.dispose() );
  }
  
  public GitRepository create( File location ) {
    createRepositoryOnDisk( location );
    GitRepository result = new GitRepository( location );
    repositories.add( result );
    return result;
  }

  private void createRepositoryOnDisk( File location ) {
    InitCommand init = Git.init();
    init.setDirectory( location );
    init.setBare( false );
    callInit( init );
  }

  private static void callInit( InitCommand init ) {
    try {
      init.call().close();
    } catch( GitAPIException exception ) {
      throw new GitOperationException( exception );
    }
  }
}