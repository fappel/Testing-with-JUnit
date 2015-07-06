package book.twju.timeline.provider.git;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.write;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.GitCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;

public class GitRepository {

  private File location;
  private Git git;

  GitRepository( Git git, File location ) {
    this.location = location;
    this.git = git;
  }
  
  void close() {
    git.close();
  }
  
  public RevCommit commitFile( String fileName, String content, String message ) throws IOException {
    createFile( fileName, content );
    addFiles();
    return commit( message );
  }

  public RevCommit commit( String message ) {
    CommitCommand commitCommand = git.commit();
    commitCommand.setMessage( message );
    return callCommand( commitCommand );
  }
  
  public File createFile( String name, String content ) throws IOException {
    File result = new File( location, name );
    write( result.toPath(), content.getBytes( UTF_8 ) );
    return result;
  }

  private void addFiles() {
    AddCommand addCommand = git.add();
    addCommand.addFilepattern( "." );
    callCommand( addCommand );
  }

  private static <T> T callCommand( GitCommand<T> command ) {
    try {
      return command.call();
    } catch( GitAPIException gae ) {
      throw new GitOperationException( gae );
    }
  }
}