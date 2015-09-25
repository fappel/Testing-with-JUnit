package book.twju.timeline.provider.git;

import static book.twju.timeline.provider.git.GitItem.ofCommit;
import static book.twju.timeline.provider.git.GitItemProvider.DESTINATION_MUST_NOT_BE_NULL;
import static book.twju.timeline.provider.git.GitItemProvider.LATEST_ITEM_MUST_NOT_BE_NULL;
import static book.twju.timeline.provider.git.GitItemProvider.NAME_MUST_NOT_BE_NULL;
import static book.twju.timeline.provider.git.GitItemProvider.URI_MUST_NOT_BE_NULL;
import static book.twju.timeline.test.util.FileHelper.delete;
import static book.twju.timeline.test.util.ThrowableCaptor.thrownBy;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.lib.ObjectId;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import book.twju.timeline.test.util.GitRepository;
import book.twju.timeline.test.util.GitRule;

public class GitItemProviderITest {
  
  private static final String CLONE_NAME = "test";
  private static final int INITIAL_COMMIT_COUNT = 6;
  
  @Rule public final TemporaryFolder temporaryFolder = new TemporaryFolder();
  @Rule public final GitRule gitRule = new GitRule();
  
  private GitRepository repository;
  private GitItemProvider provider;
  private File remoteLocation;
  private File destination;
  
  @Before
  public void setUp() throws IOException {
    remoteLocation = temporaryFolder.newFolder();
    repository = createRepository( remoteLocation );
    destination = temporaryFolder.newFolder();
    provider = new GitItemProvider( remoteLocation.toURI().toString(), destination, CLONE_NAME );
  }

  @Test
  public void fetchItems() throws IOException {
    int fetchCount = INITIAL_COMMIT_COUNT / 3;
    
    List<GitItem> actual = provider.fetchItems( null, fetchCount );
    
    assertThat( actual )
      .isEqualTo( subList( 0, fetchCount ) )
      .hasSize( fetchCount );
  }
  
  @Test
  public void fetchItemsIfFetchCountExceedsCommitCounts() throws IOException {
    int fetchCount = INITIAL_COMMIT_COUNT + 1;
    
    List<GitItem> actual = provider.fetchItems( null, fetchCount );

    assertThat( actual ).hasSize( INITIAL_COMMIT_COUNT );
  }
  
  @Test
  public void fetchItemsSubsequently() throws IOException {
    int fetchCount = INITIAL_COMMIT_COUNT / 3;
    
    List<GitItem> first = provider.fetchItems( null, fetchCount );
    List<GitItem> second = provider.fetchItems( first.get( fetchCount - 1 ), fetchCount );
    
    assertThat( second )
      .isNotEqualTo( first )
      .isEqualTo( subList( fetchCount, fetchCount * 2 ) )
      .hasSize( fetchCount );
  }

  @Test
  public void fetchItemsWithNegativeFetchCount() throws IOException {
    Throwable actual = thrownBy( () -> provider.fetchItems( null, -1 ) );
    
    assertThat( actual )
      .hasMessage( GitItemProvider.FETCH_COUNT_MUST_NOT_BE_NEGATIVE )
      .isInstanceOf( IllegalArgumentException.class );
  }
  
  @Test
  public void fetchItemsWithUnrelatedItem() throws IOException {
    GitItem unrelated = new GitItem( ObjectId.toString( ObjectId.zeroId() ), 0L, "a", "c" );
    
    Throwable actual = thrownBy( () -> provider.fetchItems( unrelated, INITIAL_COMMIT_COUNT ) );
    
    assertThat( actual )
     .hasMessageContaining( unrelated.toString() )
     .hasMessageContaining( CLONE_NAME )
     .isInstanceOf( IllegalArgumentException.class );
  }

  @Test
  public void fetchNew() throws IOException {
    List<GitItem> latestItems = provider.fetchItems( null, 1 );
    int newCount = 2;
    int all = INITIAL_COMMIT_COUNT + newCount;
    createCommits( repository, newCount, "n" );
    
    List<GitItem> actual = provider.fetchNew( latestItems.get( 0 ) );

    assertThat( actual )
      .doesNotContainAnyElementsOf( subList( newCount, all ) )
      .isEqualTo( subList( 0, newCount ) )
      .hasSize( newCount );
  }
  
  @Test
  public void fetchNewIfNoNewItemExists() throws IOException {
    List<GitItem> latestItem = provider.fetchItems( null, 1 );
    
    List<GitItem> actual = provider.fetchNew( latestItem.get( 0 ) );
    
    assertThat( actual ).isEmpty();
  }
  
  @Test
  public void fetchNewWithNullAsLatestItem() {
    Throwable actual = thrownBy( () -> provider.fetchNew( null ) );
    
    assertThat( actual )
      .hasMessage( LATEST_ITEM_MUST_NOT_BE_NULL )
      .isInstanceOf( IllegalArgumentException.class );
  }
  
  @Test
  public void fetchNewWithUnrelatedItem() throws IOException {
    GitItem unrelated = new GitItem( ObjectId.toString( ObjectId.zeroId() ), 0L, "a", "c" );
    
    List<GitItem> actual = provider.fetchNew( unrelated );
    
    assertThat( actual )
      .isEqualTo( subList( 0, INITIAL_COMMIT_COUNT ) )
      .hasSize( INITIAL_COMMIT_COUNT );
  }

  @Test
  public void getNewCount() throws IOException {
    List<GitItem> latestItem = provider.fetchItems( null, 1 );
    createCommits( repository, 1, "n" );
    
    int actual = provider.getNewCount( latestItem.get( 0 ) );
    
    assertThat( actual ).isEqualTo( 1 );
  }
  
  @Test
  public void getNewCountIfNoNewItemExists() throws IOException {
    List<GitItem> latestItem = provider.fetchItems( null, 1 );
    
    int actual = provider.getNewCount( latestItem.get( 0 ) );
    
    assertThat( actual ).isEqualTo( 0 );
  }
  
  @Test
  public void getNewCountWithUnrelatedItem() throws IOException {
    GitItem unrelated = new GitItem( ObjectId.toString( ObjectId.zeroId() ), 0L, "a", "c" );
    
    int actual = provider.getNewCount( unrelated );
    
    assertThat( actual ).isEqualTo( INITIAL_COMMIT_COUNT );
  }
  
  @Test
  public void getNewCountWithNullAsLatestItem() {
    Throwable actual = thrownBy( () -> provider.getNewCount( null ) );
    
    assertThat( actual )
      .hasMessage( LATEST_ITEM_MUST_NOT_BE_NULL )
      .isInstanceOf( IllegalArgumentException.class );
  }
  
  @Test
  public void constructWithNullAsUri() {
     Throwable actual = thrownBy( () -> new GitItemProvider( null, new File( "" ), "" ) );
     
     assertThat( actual )
       .hasMessage( URI_MUST_NOT_BE_NULL )
       .isInstanceOfAny( IllegalArgumentException.class );
  }
  
  @Test
  public void constructWithNullAsDestination() {
    Throwable actual = thrownBy( () -> new GitItemProvider( "uri", null, "" ) );
    
    assertThat( actual )
      .hasMessage( DESTINATION_MUST_NOT_BE_NULL )
      .isInstanceOfAny( IllegalArgumentException.class );
  }
  
  @Test
  public void constructWithNullAsName() {
    Throwable actual = thrownBy( () -> new GitItemProvider( "uri", new File( "" ), null ) );
    
    assertThat( actual )
      .hasMessage( NAME_MUST_NOT_BE_NULL )
      .isInstanceOfAny( IllegalArgumentException.class );
  }

  @Test
  public void constructWithInvalidUri() {
    String uri = "uri";
    
    Throwable actual = thrownBy( () -> new GitItemProvider( uri, temporaryFolder.newFolder(), CLONE_NAME ) );
    
    assertThat( actual )
      .hasMessageContaining( uri )
      .isInstanceOfAny( IllegalArgumentException.class );
  }
  
  @Test
  public void constructWithFileAsDestination() throws IOException {
    String uri = remoteLocation.toURI().toString();
    File fileAsDestination = temporaryFolder.newFile();
    
    Throwable actual = thrownBy( () -> new GitItemProvider( uri, fileAsDestination, CLONE_NAME ) );
    
    assertThat( actual )
      .hasMessageContaining( fileAsDestination.toString() )
      .isInstanceOfAny( IllegalArgumentException.class );
  }
  
  @Test
  public void constructWithExistingButEmptyDestination() throws IOException {
    String uri = remoteLocation.toURI().toString();
    File destination = temporaryFolder.newFolder();
    File repositoryDirectory = new File( destination, CLONE_NAME );
    repositoryDirectory.createNewFile();
    
    Throwable actual = thrownBy( () -> new GitItemProvider( uri, destination, CLONE_NAME ) );
    
    assertThat( actual )
      .hasMessageContaining( repositoryDirectory.toString() )
      .isInstanceOfAny( IllegalArgumentException.class );
  }
  
  @Test
  public void constructIfRepositoryAlreadyExists() {
    GitItemProvider actual = new GitItemProvider( remoteLocation.toURI().toString(), destination, CLONE_NAME );
    
    assertThat( actual.fetchItems( null, 1 ) ).isNotNull();
  }
  
  @Test
  public void deleteRepositoryLocationAfterGitOperations() throws IOException {
    List<GitItem> latestItems = provider.fetchItems( null, 1 );
    createCommits( repository, 2, "n" );
    provider.fetchNew( latestItems.get( 0 ) );

    delete( destination );
    
    assertThat( destination ).doesNotExist();
  }
  
  private List<GitItem> subList( int fromIndex , int toIndex  ) {
    return repository
      .logAll()
      .stream()
      .map( commit -> ofCommit( commit ) )
      .collect( toList() )
      .subList( fromIndex, toIndex );
  }

  private GitRepository createRepository( File remoteLocation ) throws IOException {
    GitRepository result = gitRule.create( remoteLocation );
    createCommits( result, INITIAL_COMMIT_COUNT, "f" );
    return result;
  }

  private void createCommits( GitRepository repository, int commitCount, String filePrefix ) throws IOException {
    for( int i = 0; i < commitCount; i++ ) {
      repository.commitFile( filePrefix + i, "c" + i, "m" + i );
    }
  }
}