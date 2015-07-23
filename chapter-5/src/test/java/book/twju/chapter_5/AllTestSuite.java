package book.twju.chapter_5;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith( Suite.class )
@SuiteClasses( {
  TimelineTest.class,
  UiITest.class
} )
public class AllTestSuite {}