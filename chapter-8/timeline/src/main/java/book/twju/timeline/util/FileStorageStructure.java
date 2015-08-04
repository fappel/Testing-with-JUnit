package book.twju.timeline.util;

import static book.twju.timeline.util.Assertion.checkArgument;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileStorageStructure {
  
  static final String BASE_DIRECTORY_MUST_NOT_BE_NULL = "Argument 'baseDirectory' must not be null.";
  static final String STORAGE_FILE = "session.storage";
  static final String TIMELINE_DIRECTORY = ".timeline";
  
  private final File baseDirectory;
  
  public FileStorageStructure( File baseDirectory ) {
    checkArgument( baseDirectory != null, BASE_DIRECTORY_MUST_NOT_BE_NULL );
    try {
      this.baseDirectory = baseDirectory.getCanonicalFile();
    } catch( IOException cause ) {
      throw new IllegalArgumentException( cause );
    }
  }
  
  public File getTimelineDirectory() {
    return ensureTimelineDirectory( baseDirectory );
  }
  
  public File getStorageFile() {
    return ensureStorageFile( ensureTimelineDirectory( baseDirectory ) );
  }

  private File ensureTimelineDirectory( File baseDirectory ) {
    File result = new File( baseDirectory.toString(), TIMELINE_DIRECTORY );
    try {
      Files.createDirectories( result.toPath() );
    } catch( IOException cause ) {
      throw new IllegalStateException( cause );
    }
    return result;
  }
  
  private File ensureStorageFile( File parent ) {
    File result = new File( parent, STORAGE_FILE );
    if( !result.exists() ) {
      try {
        result.createNewFile();
      } catch( IOException cause ) {
        throw new IllegalStateException( cause );
      }
    }
    return result;
  }
}