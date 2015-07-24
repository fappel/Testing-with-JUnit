package book.twju.chapter_6;

public class Memento {

  private String content;

  public Memento( String content ) {
    this.content = content;
  }
  
  @Override
  public String toString() {
    return content;
  }
}