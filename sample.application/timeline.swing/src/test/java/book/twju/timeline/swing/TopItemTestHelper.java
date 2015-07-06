package book.twju.timeline.swing;

import static javax.swing.JFrame.EXIT_ON_CLOSE;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
import static javax.swing.SwingUtilities.invokeAndWait;
import static javax.swing.SwingUtilities.invokeLater;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Container;
import java.util.Optional;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import book.twju.timeline.model.Item;
import book.twju.timeline.model.Timeline;
import book.twju.timeline.swing.ItemUiList;
import book.twju.timeline.swing.SwingItemUi;
import book.twju.timeline.ui.ItemUiMap;

class TopItemTestHelper {

  @SuppressWarnings("unchecked")
  static ItemUiList<Item> stubUiItemList() {
    ItemUiList<Item> result = mock( ItemUiList.class );
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setVerticalScrollBarPolicy( VERTICAL_SCROLLBAR_ALWAYS );
    when( result.getComponent() ).thenReturn( scrollPane );
    return result;
  }

  @SuppressWarnings("unchecked")
  static ItemUiMap<Item, Container> stubUiItemMap() {
    return mock( ItemUiMap.class );
  }

  @SuppressWarnings("unchecked")
  static Timeline<Item> stubTimeline() {
    Timeline<Item> result = mock( Timeline.class );
    when( result.getTopItem() ).thenReturn( Optional.empty() );
    return result;
  }

  @SuppressWarnings("unchecked")
  static SwingItemUi<Item> stubSwingItemUi( JPanel component ) {
    SwingItemUi<Item> result = mock( SwingItemUi.class );
    when( result.getComponent() ).thenReturn( component );
    return result;
  }

  static JPanel showInFrame( JPanel panel ) throws Exception {
    invokeLater( () -> {
      JFrame frame = new JFrame( "Test Window" );
      frame.add( panel );
      frame.setDefaultCloseOperation( EXIT_ON_CLOSE );
      frame.setBounds( 100, 100, 350, 700 );
      frame.setVisible( true );
    } );
    invokeAndWait( mock( Runnable.class ) );
    invokeAndWait( mock( Runnable.class ) );
    return panel;
  }
}