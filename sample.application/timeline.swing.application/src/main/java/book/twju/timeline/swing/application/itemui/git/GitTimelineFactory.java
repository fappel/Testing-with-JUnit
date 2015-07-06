package book.twju.timeline.swing.application.itemui.git;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import book.twju.timeline.model.FileSessionStorage;
import book.twju.timeline.provider.git.GitItem;
import book.twju.timeline.provider.git.GitItemProvider;
import book.twju.timeline.provider.git.GitItemSerialization;
import book.twju.timeline.swing.SwingTimeline;

public class GitTimelineFactory {
  
  static final String SESSION_STORE = "junit.storage";
  static final String TIMELINE_FOLDER = ".timeline";

  public static SwingTimeline<GitItem> createTimeline( String baseDirectory, String uri, String name ) {
    File repositoryLocation = ensureTimelineDirectory( baseDirectory ).toFile();
    GitItemProvider itemProvider = new GitItemProvider( uri, repositoryLocation, name );
    GitItemUiFactory itemUiFactory = new GitItemUiFactory();
    File storageLocation = ensureStorageLocationInUserDir( baseDirectory );
    FileSessionStorage<GitItem> storage = new FileSessionStorage<>( storageLocation, new GitItemSerialization() );
    return new SwingTimeline<GitItem>( itemProvider, itemUiFactory, storage );
  }

  private static File ensureStorageLocationInUserDir( String baseDirectory ) {
    return ensureStorageFile( ensureTimelineDirectory( baseDirectory ) );
  }

  private static Path ensureTimelineDirectory( String baseDirectory ) {
    Path result = Paths.get( baseDirectory, TIMELINE_FOLDER );
    File timelineDirectory = result.toFile();
    if( !timelineDirectory.exists() ) {
      timelineDirectory.mkdirs();
    }
    return result;
  }
  
  private static File ensureStorageFile( Path timelineDirectoryPath ) {
    Path path = Paths.get( timelineDirectoryPath.toString(), SESSION_STORE );
    File result = path.toFile();
    if( !result.exists() ) {
      try {
        result.createNewFile();
      } catch( IOException e ) {
        throw new IllegalStateException( e );
      }
    }
    return result;
  }
}