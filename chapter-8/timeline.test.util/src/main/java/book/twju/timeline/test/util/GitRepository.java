package book.twju.timeline.test.util;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.write;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.GitCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;

public class GitRepository {

  private final File location;

  GitRepository( File location ) {
    this.location = location;
  }
  
  public RevCommit commitFile( String fileName, String content, String message ) throws IOException {
    createFile( fileName, content );
    addFiles();
    return commit( message );
  }
  
  public File createFile( String name, String content ) throws IOException {
    File result = new File( location, name );
    write( result.toPath(), content.getBytes( UTF_8 ) );
    return result;
  }

  public RevCommit commit( String message ) {
    return apply( git -> doCommit( message, git ) );
  }

  public List<RevCommit> logAll() {
    return apply( git -> doLogAll( git ) );
  }
  
  private RevCommit doCommit( String message, Git git ) {
    CommitCommand commitCommand = git.commit();
    commitCommand.setMessage( message );
    return callCommand( commitCommand );
  }
  
  private List<RevCommit> doLogAll( Git git ) {
    List<RevCommit> result = new ArrayList<>();
    callCommand( git.log() ).forEach( commit -> result.add( commit ) );
    return result;
  }
  
  private void addFiles() {
    apply( git -> doAddFiles( ".", git ) );
  }
  
  private Object doAddFiles( String pattern, Git git ) {
    AddCommand addCommand = git.add();
    addCommand.addFilepattern( pattern );
    callCommand( addCommand );
    return null;
  }

  private <T> T apply( Function<Git, T> function ) {
    Git git = null;
    try {
      git = Git.open( location );
      return function.apply( git );
    } catch( IOException ioe ) {
      throw new GitOperationException( ioe );
    } finally {
      if( git != null ) {
        git.close();
      }
    }
  }
  
  private static <T> T callCommand( GitCommand<T> command ) {
    try {
      return command.call();
    } catch( GitAPIException gae ) {
      throw new GitOperationException( gae );
    }
  }
}