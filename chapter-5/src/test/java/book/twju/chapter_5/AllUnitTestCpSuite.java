package book.twju.chapter_5;

import org.junit.extensions.cpsuite.ClasspathSuite;
import org.junit.extensions.cpsuite.ClasspathSuite.ClassnameFilters;
import org.junit.runner.RunWith;

@RunWith(ClasspathSuite.class)
@ClassnameFilters( {
  ".*Test", 
  "!.*ITest",
} )
public class AllUnitTestCpSuite {}
