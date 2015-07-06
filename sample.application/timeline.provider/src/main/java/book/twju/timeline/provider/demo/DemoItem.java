package book.twju.timeline.provider.demo;

import java.util.UUID;

import book.twju.timeline.model.Item;

public class DemoItem extends Item {

  private final String author;
  private final String content;
  
  DemoItem( long timeStamp, String author, String content ) {
    this( UUID.randomUUID().toString(), timeStamp, author, content );
  }
  
  public DemoItem( String id, long timeStamp, String author, String content ) {
    super( id, timeStamp );
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
    return "DemoItem [id="+ id +", timeStamp=" + timeStamp + ", author=" + author + ", content=" + content + "]";
  }
}