package kpbinc.io.util;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import kpbinc.test.io.util.FileIOTestContext;

import org.junit.Test;

public class JsonFileStorePersistentListTests {

	//= Class Data =====================================================================================================
	
	private static FileIOTestContext fileIOContext = new FileIOTestContext(JsonFileStorePersistentListTests.class, "json");
	
	private static final String DEFAULT_FIRST_ITEM = "first item";
	private static final String DEFAULT_SECOND_ITEM = "second item";
	private static final String DEFAULT_THIRD_ITEM = "third item";
	
	
	//= Test Support ===================================================================================================
	
	private static List<String> buildDefaultList() {
		List<String> defaultList = new ArrayList<String>();
		defaultList.add(DEFAULT_FIRST_ITEM);
		defaultList.add(DEFAULT_SECOND_ITEM);
		defaultList.add(DEFAULT_THIRD_ITEM);

		return defaultList;
	}
	
	private static void failNotYetImplemented() {
		fail("Not yet implemented");
	}
	
	
	//= Tests ==========================================================================================================
	
	@Test
	public void testReadConstructor() {
		// ARRANGE
		List<String> list = buildDefaultList();
		
		File input = fileIOContext.getPathAssuredFileHandle("read-constructor");
		JsonFileStore<List<String>> fileStore = new JsonFileStore<List<String>>(input);
		fileStore.commit(list);
		
		// ACT
		List<String> persistentList = JsonFileStorePersistentListBuilder.<String>buildWithDelegateFromFileStore(fileStore);
		
		// ASSERT
		assertEquals(list, persistentList);
	}
	
	@Test
	public void testWriteConstructor() {
		// ARRANGE
		List<String> list = buildDefaultList();
		
		File input = fileIOContext.getPathAssuredFileHandle("write-constructor");
		JsonFileStore<List<String>> fileStore = new JsonFileStore<List<String>>(input);
		
		// ACT
		List<String> persistentList = JsonFileStorePersistentListBuilder.<String>buildWithDelegate(fileStore, list);
		
		// ASSERT
		assertEquals(list, persistentList);
		
		List<String> persistedList = fileStore.read();
		assertEquals(list, persistedList);
	}

	@Test
	public void testAdd() {
		failNotYetImplemented();
	}
	
	@Test
	public void testAddAtIndex() {
		failNotYetImplemented();
	}
	
	@Test
	public void testAddAll() {
		failNotYetImplemented();
	}
	
	@Test
	public void testAddAllAtIndex() {
		failNotYetImplemented();
	}
	
	@Test
	public void testClear() {
		failNotYetImplemented();
	}
	
	@Test
	public void testRemove() {
		failNotYetImplemented();
	}
	
	@Test
	public void testRemoveAtIndex() {
		failNotYetImplemented();
	}
	
	@Test
	public void testRemoveAll() {
		failNotYetImplemented();
	}
	
	@Test
	public void testRetainAll() {
		failNotYetImplemented();
	}
	
	@Test
	public void testSet() {
		failNotYetImplemented();
	}
	
}
