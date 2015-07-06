package book.twju.timeline.provider.git;

import org.assertj.core.api.AbstractAssert;

import book.twju.timeline.provider.git.GitItem;


public class GitItemAssert extends AbstractAssert<GitItemAssert, GitItem>{
  
  public static GitItemAssert assertThat( GitItem actual ) {
    return new GitItemAssert( actual );
  }

  public GitItemAssert( GitItem actual ) {
    super( actual, GitItemAssert.class );
  }
  
  @Override
  public GitItemAssert isEqualTo( Object expected ) {
    isNotNull();
    return hasId( ( ( GitItem )expected ).getId() )
          .hasTimeStamp( ( ( GitItem )expected ).getTimeStamp() )
          .hasAuthor( ( ( GitItem )expected ).getAuthor() )
          .hasContent( ( ( GitItem )expected ).getContent() );
  }
  
  public GitItemAssert hasId( String expected ) {
    isNotNull();
    if( !actual.getId().equals( expected ) ) {
      failWithMessage( "Expect id to be <%s>, but was <%s>.", expected, actual.getId() );
    }
    return this;
  }
  
  public GitItemAssert hasTimeStamp( long expected ) {
    isNotNull();
    if( actual.getTimeStamp() != expected ) {
      failWithMessage( "Expect timeStamp to be <%s>, but was <%s>.", expected, actual.getTimeStamp() );
    }
    return this;
  }
  
  public GitItemAssert hasAuthor( String expected ) {
    isNotNull();
    if( !actual.getAuthor().equals( expected ) ) {
      failWithMessage( "Expect author to be <%s>, but was <%s>.", expected, actual.getAuthor() );
    }
    return this;
  }
  
  public GitItemAssert hasContent( String expected ) {
    isNotNull();
    if( !actual.getContent().equals( expected ) ) {
      failWithMessage( "Expect content to be <%s>, but was <%s>.", expected, actual.getContent() );
    }
    return this;
  }
}