package kpbinc.test.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import kpbinc.io.util.JsonSerializer;

import com.fasterxml.jackson.core.type.TypeReference;

public class ActAndAssertJsonSerializer {

	//~ Interface ======================================================================================================
	
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

}
