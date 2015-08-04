package book.twju.timeline.provider.git;

import static book.twju.timeline.util.Assertion.checkArgument;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;

import book.twju.timeline.model.Item;

public class GitItem extends Item {

  static final String AUTHOR_MUST_NOT_BE_NULL = "Argument 'author' must not be null.";
  static final String CONTENT_MUST_NOT_BE_NULL = "Argument 'content' must not be null.";

  private final String content;
  private final String author;

  public static GitItem ofCommit( RevCommit commit ) {
    return new GitItem( getId( commit ), getTimeStamp( commit ), getAuthor( commit ), getContent( commit ) );
  }
  
  public GitItem( String id, long timeStamp, String author, String content ) {
    super( id, timeStamp );
    checkArgument( author != null, AUTHOR_MUST_NOT_BE_NULL );
    checkArgument( content != null, CONTENT_MUST_NOT_BE_NULL );
    
    this.author = author;
    this.content = content;
  }

  public String getAuthor() {
    return author;
  }

  public String getContent() {
    return content;
  }
  
  @Override
  public String toString() {
    return "GitItem [content=" + content + ", author=" + author + ", timeStamp=" + timeStamp + ", id=" + id + "]";
  }

  static String getContent( RevCommit commit ) {
    return commit.getShortMessage();
  }
  
  static String getAuthor( RevCommit commit ) {
    return commit.getAuthorIdent().getName();
  }
  
  static long getTimeStamp( RevCommit commit ) {
    return commit.getCommitterIdent().getWhen().getTime();
  }
  
  static String getId( RevCommit commit ) {
    return ObjectId.toString( commit.getId() );
  }
}