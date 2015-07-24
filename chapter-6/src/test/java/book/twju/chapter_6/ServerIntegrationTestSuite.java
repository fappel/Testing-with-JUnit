package book.twju.chapter_6;

import org.junit.ClassRule;
import org.junit.extensions.cpsuite.ClasspathSuite;
import org.junit.extensions.cpsuite.ClasspathSuite.ClassnameFilters;
import org.junit.runner.RunWith;

@RunWith( ClasspathSuite.class )
@ClassnameFilters( { ".*ServerTest" } )
public class ServerIntegrationTestSuite {
  
  @ClassRule
  public static ServerRule serverRule = new ServerRule( 4711 );
}