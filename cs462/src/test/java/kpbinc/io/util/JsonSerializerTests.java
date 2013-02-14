package kpbinc.io.util;

import static org.junit.Assert.*;

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
			
			// ARRANGE
			assertEquals(expectedJsonSerialization, actualJsonSerialization);
		}
		catch (IOException e) {
			fail(e.getMessage());
		}
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
