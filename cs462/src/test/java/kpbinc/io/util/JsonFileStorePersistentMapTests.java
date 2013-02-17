package kpbinc.io.util;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import kpbinc.test.io.util.FileIOTestContext;
import kpbinc.util.Wrapper;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;

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
		Map<String, String> jfspMap = new JsonFileStorePersistentMap<String, String, Map<String, String>>(input);
		
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
		Map<String, String> jfspMap = new JsonFileStorePersistentMap<String, String, Map<String, String>>(output, map);
		
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
		
		Map<String, String> jfspMap = new JsonFileStorePersistentMap<String, String, Map<String, String>>(output, map);
		
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
		
		Map<String, String> jfspMap = new JsonFileStorePersistentMap<String, String, Map<String, String>>(output, map);
		
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
		
		Map<String, String> jfspMap = new JsonFileStorePersistentMap<String, String, Map<String, String>>(output);
		
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
		
		Map<String, String> jfspMap = new JsonFileStorePersistentMap<String, String, Map<String, String>>(output, map);
		
		// ACT
		jfspMap.remove("A");
		
		// ASSERT
		Map<String, String> persistedMap = fileStore.read();
		assertEquals(jfspMap, persistedMap);
	}
	
	@Test
	public void testCommit() {
		// ARRANGE
		Map<String, Wrapper<String>> map = new HashMap<String, Wrapper<String>>();
		map.put("A", new Wrapper<String>("first letter"));
		Wrapper<String> zValueWrapper = new Wrapper<String>("last letter");
		map.put("Z", zValueWrapper);
		
		File output = fileIOContext.getPathAssuredFileHandle("commit");
		TypeReference<Map<String, Wrapper<String>>> typeRef = new TypeReference<Map<String, Wrapper<String>>>() {};
		JsonFileStore<Map<String, Wrapper<String>>> fileStore = 
				new JsonFileStore<Map<String, Wrapper<String>>>(output, typeRef);
		
		JsonFileStorePersistentMap<String, Wrapper<String>, Map<String, Wrapper<String>>> jfspMap = 
				new JsonFileStorePersistentMap<String, Wrapper<String>, Map<String, Wrapper<String>>>(output, map);
		
		// ACT (1)
		zValueWrapper.setWrappedObject("really, the last letter");
		
		// ASSERT (1)
		Map<String, Wrapper<String>> persistedMap1 = fileStore.read();
		assertNotEquals(jfspMap, persistedMap1);
		
		// ACT (2)
		jfspMap.commit();
		
		// ASSERT (2)
		Map<String, Wrapper<String>> persistedMap2 = fileStore.read();
		assertEquals(jfspMap, persistedMap2);
	}
	
}
