package book.twju.timeline.swing.application.itemui.git;

import java.io.File;

import book.twju.timeline.provider.git.GitItem;
import book.twju.timeline.provider.git.GitItemProvider;
import book.twju.timeline.provider.git.GitItemSerialization;
import book.twju.timeline.swing.SwingTimeline;
import book.twju.timeline.util.FileSessionStorage;
import book.twju.timeline.util.FileStorageStructure;

public class GitTimelineFactory {
  
  private final FileStorageStructure storageStructure;

  public GitTimelineFactory( FileStorageStructure storageStructure ) {
    this.storageStructure = storageStructure;
  }
  
  public SwingTimeline<GitItem> create( String uri, String name ) {
    File timelineDirectory = storageStructure.getTimelineDirectory();
    GitItemProvider itemProvider = new GitItemProvider( uri, timelineDirectory, name );
    GitItemUiFactory itemUiFactory = new GitItemUiFactory();
    File storageFile = storageStructure.getStorageFile();
    FileSessionStorage<GitItem> storage = new FileSessionStorage<>( storageFile, new GitItemSerialization() );
    return new SwingTimeline<GitItem>( itemProvider, itemUiFactory, storage );
  }
}