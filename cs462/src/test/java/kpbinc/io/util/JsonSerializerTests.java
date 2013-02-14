package kpbinc.io.util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kpbinc.test.io.util.FileIOTestContext;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;

public class JsonSerializerTests {

	//==================================================================================================================
	// Member Data
	//==================================================================================================================
	
	private FileIOTestContext fileIOContext = new FileIOTestContext(JsonSerializerTests.class, "json");
	
	
	//==================================================================================================================
	// Action and Assertion Support
	//==================================================================================================================
	
	public static void assertJsonSerialization(Object object, String expectedJsonSerialization) {
		try {
			// ACT
			String actualJsonSerialization = JsonSerializer.serialize(object);
			
			// ASSERT
			assertEquals(expectedJsonSerialization, actualJsonSerialization);
		}
		catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	private void assertJsonFileSerialization(Object object, String filename, String expectedJsonSerialization) {
		// ACT
		try {
			File destination = fileIOContext.getPathAssuredFileHandle(filename);
			JsonSerializer.serialize(object, destination);
		}
		catch (IOException e) {
			fail("json serialization: " + e.getMessage());
		}
		
		// ASSERT
		String actualJsonSerialization = fileIOContext.readFile(filename);
		assertEquals(expectedJsonSerialization, actualJsonSerialization);
	}
	
	public static <T> void assertJsonDeserialization(String jsonSerialization, T expectedObject, Class<T> clazz) {
		try {
			// ACT
			T actualObject = JsonSerializer.deserialize(jsonSerialization, clazz);
			
			// ASSERT
			assertEquals(expectedObject, actualObject);
		}
		catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	public static <T> void assertJsonDeserialization(String jsonSerialization, T expectedObject, TypeReference<T> typeRef) {
		try {
			// ACT
			T actualObject = JsonSerializer.deserialize(jsonSerialization, typeRef);
			
			// ASSERT
			assertEquals(expectedObject, actualObject);
		}
		catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	private <T> void assertJsonFileDeserialization(String filename, T expectedObject, Class<T> clazz) {
		try {
			// ACT
			File source = fileIOContext.getFileHandle(filename);
			T actualObject = JsonSerializer.deserialize(source, clazz);
			
			// ASSERT
			assertEquals(expectedObject, actualObject);
		}
		catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	
	//==================================================================================================================
	// Tests
	//==================================================================================================================
	
	//------------------------------------------------------------------------------------------------------------------
	// Sring Serialization
	//------------------------------------------------------------------------------------------------------------------
	
	@Test
	public void testStringSerialization() {
		// ARRANGE
		String value = "somestring";
		String expectedJsonSerialization = String.format("\"%s\"", value);
		
		// ACT/ASSERT
		assertJsonSerialization(value, expectedJsonSerialization);
	}

	@Test
	public void testStringDeserialization() {
		// ARRANGE
		String expectedValue = "somestring";
		String jsonSerialization = String.format("\"%s\"", expectedValue);
		
		// ACT/ASSERT
		assertJsonDeserialization(jsonSerialization, expectedValue, String.class);
	}
	
	@Test
	public void testStringSerializationToFile() {
		// ARRANGE
		String value = "somestring";
		String expectedJsonSerialization = String.format("\"%s\"", value);
		
		// ACT/ASSERT
		assertJsonFileSerialization(value, "string-serialization", expectedJsonSerialization);
	}
	
	@Test
	public void testStringDeserializationFromFile() {
		// ARRANGE
		String expectedValue = "somestring";
		String jsonSerialization = String.format("\"%s\"", expectedValue);
		String filename = "string-deserialization";
		fileIOContext.writeFile(filename, jsonSerialization);
		
		// ACT/ASSERT
		assertJsonFileDeserialization(filename, expectedValue, String.class);
	}
	
	
	//------------------------------------------------------------------------------------------------------------------
	// List<String> Serialization
	//------------------------------------------------------------------------------------------------------------------
	
	@Test
	public void testListOfStringSerialization() {
		// ARRANGE
		List<String> list = new ArrayList<String>();
		String value = "somestring";
		list.add(value);
		String expectedJsonSerialization = String.format("[\"%s\"]", value);
		
		// ACT/ASSERT
		assertJsonSerialization(list, expectedJsonSerialization);
	}
	
	@Test
	public void testListOfStringDeserializatin() {
		// ARRANGE
		List<String> expectedList = new ArrayList<String>();
		String value = "somestring";
		expectedList.add(value);
		String jsonSerialization = String.format("[\"%s\"]", value);
		
		// ACT/ASSERT
		assertJsonDeserialization(jsonSerialization, expectedList, new TypeReference<List<String>>() {});
	}
	
}
