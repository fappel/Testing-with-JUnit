package book.twju.timeline.ui;

import static book.twju.timeline.model.FakeItems.ALL_ITEMS;
import static book.twju.timeline.model.FakeItems.FIRST_ITEM;
import static book.twju.timeline.model.FakeItems.SECOND_ITEM;
import static book.twju.timeline.model.FakeItems.THIRD_ITEM;
import static java.util.Arrays.asList;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import book.twju.timeline.model.FakeItem;
import book.twju.timeline.model.Timeline;

public class TopItemUpdaterTest {

  private ItemUiMap<FakeItem, Object> itemUiMap;
  private Timeline<FakeItem> timeline;
  private TestTopItemUpdater updater;

  @Before
  public void setUp() {
    timeline = stubTimeline( asArray( ALL_ITEMS ) );
    itemUiMap = stubItemUiMap( asArray( ALL_ITEMS ) );
    updater = new TestTopItemUpdater( timeline, itemUiMap );
  }

  @Test
  public void update() {
    updater.setBelowTop( SECOND_ITEM, THIRD_ITEM );
    
    updater.update();
    
    verify( timeline ).setTopItem( SECOND_ITEM );
  }
  
  @Test
  public void updateWithoutVisibleItems() {
    updater.update();
    
    verify( timeline, never() ).setTopItem( any( FakeItem.class ) );
  }
  
  @Test
  public void updateIfTopItemHasNotChanged() {
    when( timeline.getTopItem() ).thenReturn( Optional.of( SECOND_ITEM ) );
    updater.setBelowTop( SECOND_ITEM, THIRD_ITEM );

    updater.update();
    
    verify( timeline, never() ).setTopItem( any( FakeItem.class ) );
  }
  
  @Test
  public void updateIfTopItemHasChanged() {
    when( timeline.getTopItem() ).thenReturn( Optional.of( FIRST_ITEM ) );
    updater.setBelowTop( SECOND_ITEM, THIRD_ITEM );
    
    updater.update();
    
    verify( timeline ).setTopItem( SECOND_ITEM );
  }
  
  @Test
  public void updateIfNoItemUiHasBeenMapped() {
    when( itemUiMap.findByItemId( anyString() ) ).thenReturn( null );
     
    updater.update();

    verify( timeline, never() ).setTopItem( any( FakeItem.class ) );
  }
  
  @SuppressWarnings("unchecked")
  private static Timeline<FakeItem> stubTimeline( FakeItem ... fakeItems ) {
    Timeline<FakeItem> result = mock( Timeline.class );
    when( result.getItems() ).thenReturn( asList( fakeItems ) );
    when( result.getTopItem() ).thenReturn( Optional.empty() );
    return result;
  }

  @SuppressWarnings("unchecked")
  private static ItemUiMap<FakeItem, Object> stubItemUiMap( FakeItem ... fakeItems ) {
    ItemUiMap<FakeItem, Object> itemUiMap = mock( ItemUiMap.class );
    for( FakeItem fakeItem : fakeItems ) {
      ItemUi<FakeItem> itemUi = mock( ItemUi.class );
      when( itemUiMap.findByItemId( fakeItem.getId() ) ).thenReturn( itemUi );
    }
    return itemUiMap;
  }
  
  private FakeItem[] asArray( Set<FakeItem> allItems ) {
    return allItems.toArray( new FakeItem[ allItems.size() ] );
  }  
}