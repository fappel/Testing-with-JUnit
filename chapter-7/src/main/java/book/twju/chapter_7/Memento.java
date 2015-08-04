package book.twju.chapter_7;

import java.util.Set;

public class Memento {

  private final Set<? extends Item> items;
  private final Item topItem;

  public Memento( Set<? extends Item> items, Item topItem ) {
    this.items = items;
    this.topItem = topItem;
  }
  
  public Set<? extends Item> getItems() {
    return items;
  }

  public Item getTopItem() {
    return topItem;
  }
}