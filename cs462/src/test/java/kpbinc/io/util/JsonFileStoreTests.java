package kpbinc.io.util;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import kpbinc.test.io.util.FileIOTestContext;

import org.junit.Test;

public class JsonFileStoreTests {

	//==================================================================================================================
	// Member Data
	//==================================================================================================================
	
	private FileIOTestContext fileIOContext = new FileIOTestContext(JsonFileStoreTests.class, "json");
	
	
	//==================================================================================================================
	// Tests
	//==================================================================================================================
	
	@Test
	public void test1DStringStringMap() {
		// ARRANGE
		Map<String, String> map = new HashMap<String, String>();
		map.put("A", "first letter");
		map.put("Z", "last letter");
		
		File destination = fileIOContext.getPathAssuredFileHandle("1d-string-string-map.fs");
		JsonFileStore<Map<String, String>> fileStore = new JsonFileStore<Map<String, String>>(destination);
		
		// ACT
		fileStore.commit(map);
		Map<String, String> mapCopy = fileStore.read();
		
		// ASSERT
		assertEquals(map, mapCopy);
	}

}
