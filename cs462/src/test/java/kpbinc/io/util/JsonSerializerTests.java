package kpbinc.io.util;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;

public class JsonSerializerTests {

	//==================================================================================================================
	// Action and Assertion Support
	//==================================================================================================================
	
	private void assertJsonSerialization(Object object, String expectedJsonSerialization) {
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
			File destination = getPathAssuredFileHandle(filename);
			JsonSerializer.serialize(object, destination);
		}
		catch (IOException e) {
			fail("json serialization: " + e.getMessage());
		}
		
		// ASSERT
		String actualJsonSerialization = readFile(filename);
		assertEquals(expectedJsonSerialization, actualJsonSerialization);
	}
	
	private <T> void assertJsonDeserialization(String jsonSerialization, T expectedObject, Class<T> clazz) {
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
	
	private <T> void assertJsonDeserialization(String jsonSerialization, T expectedObject, TypeReference<T> typeRef) {
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
			File source = getFileHandle(filename);
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
		writeFile(filename, jsonSerialization);
		
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
	
	
	//==================================================================================================================
	// File IO Support
	//==================================================================================================================
	
	private static final String BASE_DIRECTORY = "/tmp/" + JsonSerializerTests.class.getSimpleName();
	
	private File getFileHandle(String filename) {
		File file = new File(BASE_DIRECTORY + "/" + filename + ".json");
		return file;
	}
	
	private File getPathAssuredFileHandle(String filename) {
		File file = getFileHandle(filename);
		file.getParentFile().mkdirs();
		return file;
	}
	
	private void writeFile(String filename, String content) {
		try {
			File destination = getPathAssuredFileHandle(filename);
			
			FileWriter fw = new FileWriter(destination);
			
			fw.write(content);
			
			fw.close();
		}
		catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	private String readFile(String filename) {
		String result = null;
		
		try {
			File source = getFileHandle(filename);
			FileReader fr = new FileReader(source);
			BufferedReader br = new BufferedReader(fr);
			
			StringBuilder builder = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				builder.append(line);
				line = br.readLine();
			}
			
			br.close();
			
			result = builder.toString();
		}
		catch (IOException e) {
			fail(e.getMessage());
		}
		
		return result;
	}

	private void deleteFile(String filename) {
		File file = getFileHandle(filename);
		boolean deleted = file.delete();
		if (!deleted) {
			fail(String.format("Deletion of %s failed.", file.getAbsolutePath()));
		}
	}
	
}
