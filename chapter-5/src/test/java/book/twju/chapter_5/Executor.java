package book.twju.chapter_5;

import static org.junit.runner.Description.createSuiteDescription;
import static org.junit.runner.Description.createTestDescription;

import java.util.List;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

public class Executor extends Runner {
  
  private final List<FrameworkMethod> methods;
  private final TestClass meta;

  public Executor( Class<?> testType ) {
    meta = new TestClass( testType );
    methods = meta.getAnnotatedMethods( Execute.class );
  }

  @Override
  public Description getDescription() {
    Description result = createClassDescription();
    methods.forEach( method 
      -> result.addChild( createMethodDescription( method )
    ) );
    return result;
  }

  @Override
  public void run( RunNotifier notifier ) {
    methods.forEach( method -> run( notifier, method ) );
  }

  private void run( RunNotifier notifier, FrameworkMethod method ) {
    Description description = createMethodDescription( method );
    notifier.fireTestStarted( description );
    try {
      Object target = meta.getJavaClass().newInstance();
      method.invokeExplosively( target );
    } catch( Throwable problem ) {
      Failure failure = new Failure( description, problem );
      notifier.fireTestFailure( failure );
    }
    notifier.fireTestFinished( description );
  }
  
  private Description createClassDescription() {
    String name = meta.getName();
    Execute annotations = meta.getAnnotation( Execute.class );
    return createSuiteDescription( name, annotations );
  }
  
  private Description createMethodDescription(
    FrameworkMethod method )
  {
    return createTestDescription( meta.getClass(),
                                  method.getName() );
  }
}