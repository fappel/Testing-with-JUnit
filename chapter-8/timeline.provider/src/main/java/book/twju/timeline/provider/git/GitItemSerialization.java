package book.twju.timeline.provider.git;

import static book.twju.timeline.util.Assertion.checkArgument;
import static java.lang.Long.parseLong;
import book.twju.timeline.model.ItemSerialization;

public class GitItemSerialization implements ItemSerialization<GitItem> {

  static final String ITEM_MUST_NOT_BE_NULL = "Argument 'item' must not be null.";
  static final String INPUT_MUST_NOT_BE_NULL = "Argument 'input' must not be null.";
  static final String INPUT_IS_NO_VALID = "Input <%s> is no valid.";

  private static final String SEPARATOR = "@;@;@;@";


  @Override
  public String serialize( GitItem item ) {
    checkArgument( item != null, ITEM_MUST_NOT_BE_NULL );
    
    return new StringBuilder()
      .append( item.getId() ).append( SEPARATOR )
      .append( item.getTimeStamp() ).append( SEPARATOR )
      .append( item.getAuthor() ).append( SEPARATOR )
      .append( item.getContent() ).toString();
  }

  @Override
  public GitItem deserialize( String input ) {
    checkArgument( input != null, INPUT_MUST_NOT_BE_NULL );
    
    String[] split = input.split( SEPARATOR );
    return new GitItem( split[ 0 ], parseLong( split[ 1 ] ), split[ 2 ], split[ 3 ] );
  }
}