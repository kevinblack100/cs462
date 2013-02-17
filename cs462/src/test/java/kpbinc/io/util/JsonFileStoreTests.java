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
	public void testCustomSimpleTypeConstructor() {
		// ARRANGE
		Map<String, Wrapper<String>> map = new HashMap<String, Wrapper<String>>();
		map.put("A", new Wrapper<String>("first letter"));
		map.put("Z", new Wrapper<String>("last letter"));
		
		File destination = fileIOContext.getPathAssuredFileHandle("custom-simple-type-constructor.fs");
		JsonFileStore<Map<String, Wrapper<String>>> fileStore =
				new JsonFileStore<Map<String, Wrapper<String>>>(destination,
						new TypeReference<Map<String, Wrapper<String>>>() {});
		
		// ACT
		fileStore.commit(map);
		Map<String, Wrapper<String>> mapCopy = fileStore.read();
		
		// ASSERT
		assertEquals(map, mapCopy);
	}

	@Test
	public void testCustomCollectionTypeConstructor() {
		// ARRANGE
		Map<String, Map<String, String>> mainMap = new HashMap<String, Map<String, String>>();
		
		Map<String, String> capitalLetters = new HashMap<String, String>();
		capitalLetters.put("A", "first captial letter");
		capitalLetters.put("Z", "last capital letter");
		
		Map<String, String> lowercaseLetters = new HashMap<String, String>();
		lowercaseLetters.put("a", "first lowercase letter");
		lowercaseLetters.put("z", "last lowercase letter");
		
		mainMap.put("capital letters", capitalLetters);
		mainMap.put("lowercase letter", lowercaseLetters);
		
		File destination = fileIOContext.getPathAssuredFileHandle("custom-collection-type-constructor.fs");
		JsonFileStore<Map<String, Map<String, String>>> fileStore =
				new JsonFileStore<Map<String, Map<String, String>>>(destination,
						new TypeReference<Map<String, Map<String, String>>>() {});
		
		// ACT
		fileStore.commit(mainMap);
		Map<String, Map<String, String>> mapCopy = fileStore.read();
		
		// ASSERT
		assertEquals(mainMap, mapCopy);
	}

}
