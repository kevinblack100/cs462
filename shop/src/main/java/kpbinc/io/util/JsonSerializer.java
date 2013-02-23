package kpbinc.io.util;

import java.io.File;
import java.io.IOException;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import kpbinc.cs462.ffds.model.mixin.SimpleGrantedAuthorityJacksonAnnotatedMixin;
import kpbinc.cs462.ffds.model.mixin.UserDetailsJacksonAnnotatedMixin;
import kpbinc.cs462.ffds.model.mixin.UserJsonAnnotatedMixin;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Encapsulates JSON serialization/deserialization. Based on the JsonSerializer in the DataProvider module of the BYU
 * NLP group's CCASH project.
 * 
 * @author Kevin Black
 */
public class JsonSerializer {

	//==================================================================================================================
	// Class Data & Initialization
	//==================================================================================================================
	
	private static ObjectMapper mapper = initializeMapper();
	
	/**
	 * Initializes an ObjectMapper instance.
	 * 
	 * @return initialized ObjectMapper instance.
	 */
	private static ObjectMapper initializeMapper() {
		ObjectMapper result = new ObjectMapper();
		result.addMixInAnnotations(UserDetails.class, UserDetailsJacksonAnnotatedMixin.class);
		result.addMixInAnnotations(User.class, UserJsonAnnotatedMixin.class);
		result.addMixInAnnotations(SimpleGrantedAuthority.class, SimpleGrantedAuthorityJacksonAnnotatedMixin.class);
		return result;
	}
	
	
	//==================================================================================================================
	// Interface
	//==================================================================================================================
	
	//------------------------------------------------------------------------------------------------------------------
	// Serialization
	//------------------------------------------------------------------------------------------------------------------
	
	public static String serialize(Object object) throws IOException {
		String jsonSerialization = mapper.writeValueAsString(object);
		return jsonSerialization;
	}
	
	public static void serialize(Object object, File file) throws IOException {
		mapper.writeValue(file, object);
	}
	
	//------------------------------------------------------------------------------------------------------------------
	// Deserialization
	//------------------------------------------------------------------------------------------------------------------
	
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
