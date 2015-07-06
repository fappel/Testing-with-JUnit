package book.twju.timeline.test.util;

import static book.twju.timeline.test.util.FileHelper.delete;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.InitCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.rules.ExternalResource;


public class GitRule extends ExternalResource {

  private final Map<File, GitRepository> repositories;

  public GitRule() {
    repositories = new HashMap<>();
  }
  
  @Override
  protected void after() {
    repositories.values().forEach( repository -> repository.close() );
    repositories.keySet().forEach( file -> delete( file ) );
  }
  
  public GitRepository create( File location ) {
    InitCommand init = Git.init();
    init.setDirectory( location );
    init.setBare( false );
    Git git = callInit( init );
    GitRepository result = new GitRepository( git, location );
    repositories.put( location, result );
    return result;
  }

  private static Git callInit( InitCommand init ) {
    try {
      return init.call();
    } catch( GitAPIException exception ) {
      throw new GitOperationException( exception );
    }
  }
}