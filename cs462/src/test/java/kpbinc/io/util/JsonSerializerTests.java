package kpbinc.io.util;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class JsonSerializerTests {

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
}
