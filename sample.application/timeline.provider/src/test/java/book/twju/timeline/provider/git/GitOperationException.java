package book.twju.timeline.provider.git;

public class GitOperationException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  
  GitOperationException( Throwable cause ) {
    super( cause );
  }
}
