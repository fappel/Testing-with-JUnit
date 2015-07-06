package book.twju.timeline.provider.demo;

import java.util.Timer;
import java.util.TimerTask;

public class DemoItemProviderHolder {
  

  private final static String LOREM_IPSUM_1 
    =   "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, "
      + "sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat";
  private final static String LOREM_IPSUM_2
    =   "Sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet "
      + "clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem "
      + "ipsum dolor sit amet, consetetur sadipscing elitr.";
  
  private final DemoItemProvider itemProvider;

  static class ItemCreator extends TimerTask {

    private final DemoItemProvider itemProvider;

    ItemCreator( DemoItemProvider itemProvider ) {
      this.itemProvider = itemProvider;
    }
    
    @Override
    public void run() {
      long now = System.currentTimeMillis();
      String author = "author " + now % 4;
      String content = now % 2 == 0 ? LOREM_IPSUM_1 : LOREM_IPSUM_2;  
      itemProvider.addItem( createDemoItem( author, now, content ) );
    }
  }

  public DemoItemProviderHolder() {
    itemProvider = new DemoItemProvider();
  }
  
  public DemoItemProvider getItemProvider() {
    return itemProvider;
  }
  
  public void initialize() {
    long now = System.currentTimeMillis();
    for( int i = 0; i < 100; i++ ) {
      String author = "author " + i % 4;
      String content = i % 2 == 0 ? LOREM_IPSUM_1 : LOREM_IPSUM_2;  
      itemProvider.addItem( createDemoItem( author, now - ( i * 300_000 ), content ) );
    }
    Timer timer = new Timer( true );
    timer.schedule( new ItemCreator( itemProvider ), 5_000, 20_000 );
//    timer.schedule( new ItemCreator( itemProvider ), 5_000, 120_000 );
  }

  private static DemoItem createDemoItem( String author, long timeStamp, String content ) {
    return new DemoItem( timeStamp, author, content );
  }
}