package book.twju.timeline.test.util;

import static book.twju.timeline.test.util.FileHelper.delete;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.InitCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.rules.ExternalResource;

public class GitRule extends ExternalResource {

  private final Set<File> repositories;

  public GitRule() {
    repositories = new HashSet<>();
  }
  
  @Override
  protected void after() {
    repositories.forEach( repository -> delete( repository ) );
  }
  
  public GitRepository create( File location ) {
    createRepositoryOnDisk( location );
    GitRepository result = new GitRepository( location );
    repositories.add( location );
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