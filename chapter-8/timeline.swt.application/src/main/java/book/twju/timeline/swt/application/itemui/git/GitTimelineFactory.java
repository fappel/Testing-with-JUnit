package book.twju.timeline.swt.application.itemui.git;

import java.io.File;

import org.eclipse.swt.widgets.Composite;

import book.twju.timeline.provider.git.GitItem;
import book.twju.timeline.provider.git.GitItemProvider;
import book.twju.timeline.provider.git.GitItemSerialization;
import book.twju.timeline.swt.SwtTimeline;
import book.twju.timeline.util.FileSessionStorage;
import book.twju.timeline.util.FileStorageStructure;

public class GitTimelineFactory {
  
  private final FileStorageStructure storageStructure;

  public GitTimelineFactory( FileStorageStructure storageStructure ) {
    this.storageStructure = storageStructure;
  }
  
  public SwtTimeline<GitItem> create( Composite parent, String uri, String name ) {
    File timelineDirectory = storageStructure.getTimelineDirectory();
    GitItemProvider itemProvider = new GitItemProvider( uri, timelineDirectory, name );
    GitItemUiFactory itemUiFactory = new GitItemUiFactory();
    File storageFile = storageStructure.getStorageFile();
    FileSessionStorage<GitItem> storage = new FileSessionStorage<>( storageFile, new GitItemSerialization() );
    return new SwtTimeline<GitItem>( parent, itemProvider, itemUiFactory, storage );
  }
}