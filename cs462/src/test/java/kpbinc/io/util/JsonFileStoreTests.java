package kpbinc.io.util;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import kpbinc.test.io.util.FileIOTestContext;
import kpbinc.util.Wrapper;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;

public class JsonFileStoreTests {

	//==================================================================================================================
	// Member Data
	//==================================================================================================================
	
	private FileIOTestContext fileIOContext = new FileIOTestContext(JsonFileStoreTests.class, "json");
	
	
	//==================================================================================================================
	// Tests
	//==================================================================================================================
	
	@Test
	public void testBasicTypeConstructor() {
		// ARRANGE
		Map<String, String> map = new HashMap<String, String>();
		map.put("A", "first letter");
		map.put("Z", "last letter");
		
		File destination = fileIOContext.getPathAssuredFileHandle("basic-type-constructor.fs");
		JsonFileStore<Map<String, String>> fileStore = new JsonFileStore<Map<String, String>>(destination);
		
		// ACT
		fileStore.commit(map);
		Map<String, String> mapCopy = fileStore.read();
		
		// ASSERT
		assertEquals(map, mapCopy);
	}

	@Test
	public void testCustomTypeConstructor() {
		// ARRANGE
		Map<String, Wrapper<String>> map = new HashMap<String, Wrapper<String>>();
		map.put("A", new Wrapper<String>("first letter"));
		map.put("Z", new Wrapper<String>("last letter"));
		
		File destination = fileIOContext.getPathAssuredFileHandle("custom-type-constructor.fs");
		JsonFileStore<Map<String, Wrapper<String>>> fileStore =
				new JsonFileStore<Map<String, Wrapper<String>>>(destination,
						new TypeReference<Map<String, Wrapper<String>>>() {});
		
		// ACT
		fileStore.commit(map);
		Map<String, Wrapper<String>> mapCopy = fileStore.read();
		
		// ASSERT
		assertEquals(map, mapCopy);
	}
	
}
