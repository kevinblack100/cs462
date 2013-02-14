package kpbinc.io.util;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Encapsulates JSON serialization/deserialization. Based on the JsonSerializer in the DataProvider module of the BYU
 * NLP group's CCASH project.
 * 
 * @author Kevin Black
 *
 */
public class JsonSerializer {

	private static ObjectMapper mapper = initializeMapper();
	
	/**
	 * Initializes the ObjectMapper instance.
	 * 
	 * @return initialized ObjectMapper instance.
	 */
	private static ObjectMapper initializeMapper() {
		return new ObjectMapper();
	}
	
	public static String serialize(Object object) throws IOException {
		String jsonSerialization = mapper.writeValueAsString(object);
		return jsonSerialization;
	}
	
	public static void serialize(Object object, File file) throws IOException {
		mapper.writeValue(file, object);
	}
	
	public static <T> T deserialize(String jsonSerialization, Class<T> clazz) throws IOException {
		T deserializedObject = mapper.readValue(jsonSerialization, clazz);
		return deserializedObject;
	}
	
	public static <T> T deserialize(String jsonSerialization, TypeReference<T> typeRef) throws IOException {
		T deserializedObject = mapper.readValue(jsonSerialization, typeRef);
		return deserializedObject;
	}
	
	public static <T> T deserialize(File file, Class<T> clazz) throws IOException {
		T deserializedObject = mapper.readValue(file, clazz);
		return deserializedObject;
	}
	
	public static <T> T deserialize(File file, TypeReference<T> typeRef) throws IOException {
		T deserializedObject = mapper.readValue(file, typeRef);
		return deserializedObject;
	}
	
}
