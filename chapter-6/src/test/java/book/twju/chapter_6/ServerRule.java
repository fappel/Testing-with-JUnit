package book.twju.chapter_6;

import org.junit.rules.ExternalResource;

public class ServerRule extends ExternalResource {

  private final int port;

  public ServerRule( int port ) {
    this.port = port;
  }

  @Override
  protected void before() throws Throwable {
    System.out.println( "start server on port: " + port );
  }
  
  @Override
  protected void after() {
    System.out.println( "stop server on port: " + port );
  }
}