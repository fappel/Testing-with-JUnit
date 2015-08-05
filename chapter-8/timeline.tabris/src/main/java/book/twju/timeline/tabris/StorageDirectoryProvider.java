package book.twju.timeline.tabris;

import static book.twju.timeline.util.Assertion.checkArgument;
import static java.nio.file.Files.createTempDirectory;
import static java.util.Arrays.asList;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class StorageDirectoryProvider {

  static final String ROOT_MUST_NOT_BE_NULL = "Argument 'root' must not be null.";
  static final String ROOT_DOES_NOT_EXIST = "Storage root directory '%s' does not exist.";
  static final String ROOT_IS_NOT_A_DIRECTORY = "Storage root '%s' is not a directory.";

  private static final String COOKIE_NAME = "timeline";
  
  private final File root;
  
  static Cookie createCookie( String fileName ) {
    return new Cookie( COOKIE_NAME, fileName );
  }

  StorageDirectoryProvider( File root ) {
    checkArgument( root != null, ROOT_MUST_NOT_BE_NULL );
    checkArgument( root.exists(), ROOT_DOES_NOT_EXIST, root );
    checkArgument( root.isDirectory(), ROOT_IS_NOT_A_DIRECTORY, root );
    
    this.root = root;
  }

  File getDirectory( HttpServletRequest request, HttpServletResponse response ) {
    File result;
    Optional<Cookie> cookie = readCookie( request );
    if( cookie.isPresent() ) {
      result = findStorageDirectory( cookie );
    } else {
      result = createStorageDirectory();
      response.addCookie( createCookie( result.getName() ) );
    }
    return result;
  }

  private Optional<Cookie> readCookie( HttpServletRequest request ) {
    Cookie[] cookies = request.getCookies();
    if( cookies != null ) {
      return asList( cookies )
        .stream()
        .filter( cookie -> cookie.getName().equals( COOKIE_NAME ) )
        .findFirst();
    }
    return Optional.empty();
  }
  
  private File findStorageDirectory( Optional<Cookie> cookie ) {
    File result = new File( root, cookie.get().getValue() );
    if( !result.exists() ) {
      result.mkdirs();
    }
    return result;
  }
  
  private File createStorageDirectory() {
    try {
      return createTempDirectory( root.toPath(), ".timeline_" ).toFile();
    } catch( IOException cause ) {
      throw new IllegalStateException( cause );
    }
  }
}