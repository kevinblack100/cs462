package kpbinc.io.util;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import kpbinc.test.io.util.FileIOTestContext;

import org.junit.Test;

// TODO make a test template class
public class JsonFileStorePersistentMapTests {

	//==================================================================================================================
	// Member Data
	//==================================================================================================================
	
	private FileIOTestContext fileIOContext = new FileIOTestContext(JsonFileStorePersistentMapTests.class, "json");
	
	
	//==================================================================================================================
	// Tests
	//==================================================================================================================
	
	@Test
	public void testReadConstructor() {
		// ARRANGE
		Map<String, String> map = new HashMap<String, String>();
		map.put("A", "first letter");
		map.put("Z", "last letter");
		
		File input = fileIOContext.getPathAssuredFileHandle("read-constructor");
		JsonFileStore<Map<String, String>> fileStore = new JsonFileStore<Map<String,String>>(input);
		fileStore.commit(map);
		
		// ACT
		Map<String, String> jfspMap = new JsonFileStorePersistentMap<String, String>(input);
		
		// ASSERT
		assertEquals(map, jfspMap);
	}

	@Test
	public void testWriteConstructor() {
		// ARRANGE
		Map<String, String> map = new HashMap<String, String>();
		map.put("A", "first letter");
		map.put("Z", "last letter");
		
		File output = fileIOContext.getPathAssuredFileHandle("write-constructor");
		JsonFileStore<Map<String, String>> fileStore = new JsonFileStore<Map<String,String>>(output);
		
		// ACT
		Map<String, String> jfspMap = new JsonFileStorePersistentMap<String, String>(map, output);
		
		// ASSERT
		Map<String, String> persistedMap = fileStore.read();
		assertEquals(jfspMap, persistedMap);
	}
	
	@Test
	public void testClear() {
		// ARRANGE
		Map<String, String> map = new HashMap<String, String>();
		map.put("A", "first letter");
		map.put("Z", "last letter");
		
		File output = fileIOContext.getPathAssuredFileHandle("clear");
		JsonFileStore<Map<String, String>> fileStore = new JsonFileStore<Map<String,String>>(output);
		
		Map<String, String> jfspMap = new JsonFileStorePersistentMap<String, String>(map, output);
		
		// ACT
		jfspMap.clear();
		
		// ASSERT
		Map<String, String> persistedMap = fileStore.read();
		assertEquals(jfspMap, persistedMap);
	}
	
	@Test
	public void testPut() {
		// ARRANGE
		Map<String, String> map = new HashMap<String, String>();
		map.put("A", "first letter");
		map.put("Z", "last letter");
		
		File output = fileIOContext.getPathAssuredFileHandle("put-key-value");
		JsonFileStore<Map<String, String>> fileStore = new JsonFileStore<Map<String,String>>(output);
		
		Map<String, String> jfspMap = new JsonFileStorePersistentMap<String, String>(map, output);
		
		// ACT
		jfspMap.put("B", "second letter");
		
		// ASSERT
		Map<String, String> persistedMap = fileStore.read();
		assertEquals(jfspMap, persistedMap);
	}
	
	@Test
	public void testPutAll() {
		// ARRANGE
		File output = fileIOContext.getPathAssuredFileHandle("put-all");
		JsonFileStore<Map<String, String>> fileStore = new JsonFileStore<Map<String,String>>(output);
		
		Map<String, String> jfspMap = new JsonFileStorePersistentMap<String, String>(output);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("A", "first letter");
		map.put("Z", "last letter");
		
		// ACT
		jfspMap.putAll(map);
		
		// ASSERT
		Map<String, String> persistedMap = fileStore.read();
		assertEquals(map, persistedMap);
	}
	
	@Test
	public void testRemove() {
		// ARRANGE
		Map<String, String> map = new HashMap<String, String>();
		map.put("A", "first letter");
		map.put("Z", "last letter");
		
		File output = fileIOContext.getPathAssuredFileHandle("remove-key");
		JsonFileStore<Map<String, String>> fileStore = new JsonFileStore<Map<String,String>>(output);
		
		Map<String, String> jfspMap = new JsonFileStorePersistentMap<String, String>(map, output);
		
		// ACT
		jfspMap.remove("A");
		
		// ASSERT
		Map<String, String> persistedMap = fileStore.read();
		assertEquals(jfspMap, persistedMap);
	}
	
}
