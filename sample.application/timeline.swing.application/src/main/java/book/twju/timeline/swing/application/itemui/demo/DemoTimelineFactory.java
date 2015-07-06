package book.twju.timeline.swing.application.itemui.demo;

import book.twju.timeline.provider.demo.DemoItem;
import book.twju.timeline.provider.demo.DemoItemProvider;
import book.twju.timeline.provider.demo.DemoItemProviderHolder;
import book.twju.timeline.swing.SwingTimeline;

public class DemoTimelineFactory {
  
  public static SwingTimeline<DemoItem> createTimeline() {
    DemoItemProviderHolder itemProviderHolder = new DemoItemProviderHolder();
    itemProviderHolder.initialize();
    DemoItemProvider itemProvider = itemProviderHolder.getItemProvider();
    DemoItemUiFactory itemUiFactory = new DemoItemUiFactory();
    return new SwingTimeline<DemoItem>( itemProvider, itemUiFactory, new DemoItemSessionStorage() );
  }
}