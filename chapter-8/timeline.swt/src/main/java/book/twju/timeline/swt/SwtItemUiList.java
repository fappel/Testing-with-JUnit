package book.twju.timeline.swt;

import static book.twju.timeline.swt.Resources.getColorWhite;
import static book.twju.timeline.swt.SwtTimelineCompound.createBackgroundProcessor;
import static book.twju.timeline.ui.FetchOperation.MORE;
import static java.util.Arrays.asList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import book.twju.timeline.model.Item;
import book.twju.timeline.ui.ItemUiList;
import book.twju.timeline.ui.ItemUiMap;
import book.twju.timeline.util.BackgroundProcessor;

class SwtItemUiList<T extends Item> extends ItemUiList<T, Composite> {
  
  ScrolledComposite uiRoot;
  Composite content;
  Button fetchMore;
  
  SwtItemUiList( ItemUiMap<T, Composite> itemUiMap ) {
    this( itemUiMap, createBackgroundProcessor() );
  }
  
  SwtItemUiList( ItemUiMap<T, Composite> itemUiMap, BackgroundProcessor backgroundProcessor ) {
    super( itemUiMap, backgroundProcessor );
  }

  @Override
  protected Composite getUiRoot() {
    return uiRoot;
  }
  
  @Override
  protected Composite getContent() {
    return content;
  }

  @Override
  protected void beforeContentUpdate() {
    fetchMore.dispose();
  }
  
  @Override
  protected void afterContentUpdate() {
    createFetchMore();
    layoutContent();
  }
  
  @Override
  public void createUi( Composite parent ) {
    createControl( parent );
    createContent();
    createFetchMore();
  }
  
  private void createControl( Composite parent ) {
    uiRoot = new ScrolledComposite( parent, SWT.V_SCROLL );
    uiRoot.setBackground( getColorWhite() );
    uiRoot.addListener( SWT.Resize, evt -> layoutContent() );
  }
  
  private void createContent() {
    content = new Composite( uiRoot, SWT.NONE );
    content.setBackground( getColorWhite() );
    uiRoot.setContent( content );
  }
  
  private void createFetchMore() {
    fetchMore = new Button( content, SWT.NONE );
    fetchMore.setText( "more" );
    fetchMore.addListener( SWT.Selection, event -> fetch( MORE ) );
  }

  private void layoutContent() {
    GridLayout gridLayout = new GridLayout();
    content.setLayout( gridLayout );
    content.setSize( computePreferredContentSize() );
    int itemControlWidth = computePreferredContentSize().x - gridLayout.verticalSpacing * 2;
    asList( content.getChildren() ).forEach( itemControl -> setLayoutData( itemControl, itemControlWidth ) );
    content.setSize( computePreferredContentSize() );
    content.layout();
  }

  private Point computePreferredContentSize() {
    return content.computeSize( uiRoot.getClientArea().width, SWT.DEFAULT, true );
  }

  private void setLayoutData( Control itemControl , int width  ) {
    itemControl.setLayoutData( new GridData( width, SWT.DEFAULT ) );
  }
}