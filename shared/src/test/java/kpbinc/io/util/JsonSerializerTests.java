package kpbinc.io.util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import kpbinc.test.io.util.FileIOTestContext;
import kpbinc.test.util.ActAndAssertJsonSerializer;

import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.core.type.TypeReference;

public class JsonSerializerTests {

	//==================================================================================================================
	// Member Data
	//==================================================================================================================
	
	private FileIOTestContext fileIOContext = new FileIOTestContext(JsonSerializerTests.class, "json");
	
	
	//==================================================================================================================
	// Action and Assertion Support
	//==================================================================================================================
	
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
	// String Serialization
	//------------------------------------------------------------------------------------------------------------------
	
	@Test
	public void testStringSerialization() {
		// ARRANGE
		String value = "somestring";
		String expectedJsonSerialization = String.format("\"%s\"", value);
		
		// ACT/ASSERT
		ActAndAssertJsonSerializer.assertJsonSerialization(value, expectedJsonSerialization);
	}

	@Test
	public void testStringDeserialization() {
		// ARRANGE
		String expectedValue = "somestring";
		String jsonSerialization = String.format("\"%s\"", expectedValue);
		
		// ACT/ASSERT
		ActAndAssertJsonSerializer.assertJsonDeserialization(jsonSerialization, expectedValue, String.class);
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
		ActAndAssertJsonSerializer.assertJsonSerialization(list, expectedJsonSerialization);
	}
	
	@Test
	public void testListOfStringDeserializatin() {
		// ARRANGE
		List<String> expectedList = new ArrayList<String>();
		String value = "somestring";
		expectedList.add(value);
		String jsonSerialization = String.format("[\"%s\"]", value);
		
		// ACT/ASSERT
		ActAndAssertJsonSerializer.assertJsonDeserialization(jsonSerialization, expectedList, new TypeReference<List<String>>() {});
	}
	
	//------------------------------------------------------------------------------------------------------------------
	// User Serialization
	//------------------------------------------------------------------------------------------------------------------
	
	@Test
	public void testUserSerialization() {
		// ARRANGE
		String authorityString = "ROLE_USER";
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(authorityString));
		UserDetails user = new User("avgjoe", "password", authorities);
		String expectedJsonSerialization = String.format(
				"{" + 
				"\"username\":\"%s\"," +
				"\"password\":\"%s\"," +
				"\"enabled\":true," +
				"\"accountNonExpired\":true," +
				"\"credentialsNonExpired\":true," +
				"\"accountNonLocked\":true," +
				"\"authorities\":[{" +
					"\"authority\":\"%s\"}]" +
				"}",
				user.getUsername(), user.getPassword(), authorityString);
		
		// ACT/ASSERT
		ActAndAssertJsonSerializer.assertJsonSerialization(user, expectedJsonSerialization);
	}
	
	@Test
	public void testUserDeserialization() {
		// ARRANGE
		String authorityString = "ROLE_USER";
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(authorityString));
		UserDetails user = new User("avgjoe", "password", authorities);
		String jsonSerialization = String.format(
				"{" + 
				"\"username\":\"%s\"," +
				"\"password\":\"%s\"," +
				"\"enabled\":true," +
				"\"accountNonExpired\":true," +
				"\"credentialsNonExpired\":true," +
				"\"accountNonLocked\":true," +
				"\"authorities\":[{" +
					"\"authority\":\"%s\"}]" +
				"}",
				user.getUsername(), user.getPassword(), authorityString);
		
		// ACT/ASSERT
		ActAndAssertJsonSerializer.assertJsonDeserialization(jsonSerialization, user, UserDetails.class);
	}
	
	//- Raw Type -------------------------------------------------------------------------------------------------------
	
	@Test
	public void testObjectSerialization() {
		// ARRANGE
		Map<String, String> object = new TreeMap<String, String>();
		String propAName = "propA";
		String propAValue = "a";
		String propBName = "propB";
		String propBValue = "b";
		object.put(propAName, propAValue);
		object.put(propBName, propBValue);
		
		String serializationTemplate = new StringBuilder()
			.append("{")
				.append("\"%s\":\"%s\",")
				.append("\"%s\":\"%s\"")
			.append("}")
			.toString();
		String expectedJsonSerialization = String.format(serializationTemplate, propAName, propAValue, propBName, propBValue);
		
		// ACT/ASSERT
		ActAndAssertJsonSerializer.assertJsonSerialization(object, expectedJsonSerialization);
	}
	
	@Test
	public void testObjectDeserialization() {
		// ARRANGE
		Map<String, String> object = new TreeMap<String, String>();
		String propAName = "propA";
		String propAValue = "a";
		String propBName = "propB";
		String propBValue = "b";
		object.put(propAName, propAValue);
		object.put(propBName, propBValue);
		
		String serializationTemplate = new StringBuilder()
			.append("{")
				.append("\"%s\":\"%s\",")
				.append("\"%s\":\"%s\"")
			.append("}")
			.toString();
		String jsonSerialization = String.format(serializationTemplate, propAName, propAValue, propBName, propBValue);
		
		// ACT/ASSERT
		ActAndAssertJsonSerializer.assertJsonDeserialization(jsonSerialization, object, Map.class);
	}
	
	@Test
	public void testNestedObjectSerialization() {
		// ARRANGE
		Map<String, Object> object = new TreeMap<String, Object>();
		String propAName = "propA";
		String propAValue = "a";
		object.put(propAName, propAValue);
		
		Map<String, String> nestedObject = new TreeMap<String, String>();
		String propBName = "propB";
		String propBValue = "b";
		nestedObject.put(propBName, propBValue);
		
		String nestedObjectName = "nestedObject";
		object.put(nestedObjectName, nestedObject);
		
		String serializationTemplate = new StringBuilder()
			.append("{")
				.append("\"%s\":{")
					.append("\"%s\":\"%s\"")
				.append("},")	
				.append("\"%s\":\"%s\"")
			.append("}")
			.toString();
		String expectedJsonSerialization = 
				String.format(serializationTemplate, nestedObjectName, propBName, propBValue, propAName, propAValue);
		
		// ACT/ASSERT
		ActAndAssertJsonSerializer.assertJsonSerialization(object, expectedJsonSerialization);
	}
	
	@Test
	public void testNestedObjectDeserialization() {
		// ARRANGE
		Map<String, Object> object = new TreeMap<String, Object>();
		String propAName = "propA";
		String propAValue = "a";
		object.put(propAName, propAValue);
		
		Map<String, String> nestedObject = new TreeMap<String, String>();
		String propBName = "propB";
		String propBValue = "b";
		nestedObject.put(propBName, propBValue);
		
		String nestedObjectName = "nestedObject";
		object.put(nestedObjectName, nestedObject);
		
		String serializationTemplate = new StringBuilder()
			.append("{")
				.append("\"%s\":{")
					.append("\"%s\":\"%s\"")
				.append("},")	
				.append("\"%s\":\"%s\"")
			.append("}")
			.toString();
		String jsonSerialization = 
				String.format(serializationTemplate, nestedObjectName, propBName, propBValue, propAName, propAValue);
		
		// ACT/ASSERT
		ActAndAssertJsonSerializer.assertJsonDeserialization(jsonSerialization, object, Map.class);
	}
	
}
