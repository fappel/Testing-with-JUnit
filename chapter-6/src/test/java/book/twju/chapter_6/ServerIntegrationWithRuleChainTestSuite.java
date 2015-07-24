package book.twju.chapter_6;

import org.junit.ClassRule;
import org.junit.extensions.cpsuite.ClasspathSuite;
import org.junit.extensions.cpsuite.ClasspathSuite.ClassnameFilters;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

@RunWith( ClasspathSuite.class )
@ClassnameFilters( { ".*ServerTest" } )
public class ServerIntegrationWithRuleChainTestSuite {
  
  @ClassRule
  public static TestRule chain = RuleChain
    .outerRule( new ServerRule( 4711 ) )
    .around( new MyRule() );
}