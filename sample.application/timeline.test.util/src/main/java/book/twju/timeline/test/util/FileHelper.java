package book.twju.timeline.test.util;

import java.io.File;

public final class FileHelper {

  public static void delete( File file ) {
    if( file.exists() ) {
      deleteDirectory( file );
      deleteFile( file );
    }
  }

  private static void deleteDirectory( File file ) {
    if( file.isDirectory() ) {
      File[] children = file.listFiles();
      for( File child : children ) {
        delete( child );
      }
    }
  }

  private static void deleteFile( File file ) {
    if( !file.delete() ) {
      throw new IllegalStateException( "Unable to delete file: " + file );
    }
  }

  private FileHelper() {}
}