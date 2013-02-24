package kpbinc.util;

import static org.junit.Assert.*;
import kpbinc.cs462.shared.TestConstants;
import kpbinc.io.util.JsonSerializerTests;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;

public class WrapperTests {

	@Test
	public void testDefaultConstructor() {
		// ARRANGE/ACT
		Wrapper<String> wrapper = new Wrapper<String>();
		
		// ASSERT
		assertNull(wrapper.getWrappedObject());
	}
	
	@Test
	public void testObjectNotCopied() {
		// ARRANGE/ACT
		String object = TestConstants.TEST_STRING_ONE;
		Wrapper<String> wrapper = new Wrapper<String>(object);
		
		// ASSERT
		assertTrue(object == wrapper.getWrappedObject());
	}
	
	@Test
	public void testEqualsSelf() {
		// ARRANGE
		String object = TestConstants.TEST_STRING_ONE;
		Wrapper<String> wrapper = new Wrapper<String>(object);
		
		// ACT/ASSERT
		assertEquals(wrapper, wrapper);
	}

	@Test
	public void testEqualsWithNullContent() {
		// ARRANGE
		Wrapper<String> wrapper1 = new Wrapper<String>();
		Wrapper<String> wrapper2 = new Wrapper<String>();
		
		// ACT/ASSERT
		assertEquals(wrapper1, wrapper2);
	}

	@Test
	public void testEqualsWithEqualNonNullContent() {
		// ARRANGE
		String object1 = TestConstants.TEST_STRING_ONE;
		Wrapper<String> wrapper1 = new Wrapper<String>(object1);
		String object2 = TestConstants.TEST_STRING_ONE;
		Wrapper<String> wrapper2 = new Wrapper<String>(object2);
		
		// ACT/ASSERT
		assertEquals("1 eq 2", wrapper1, wrapper2);
		assertEquals("2 eq 1", wrapper2, wrapper1);
	}
	
	@Test
	public void testNotEqualsNull() {
		// ARRANGE
		Wrapper<String> wrapper = new Wrapper<String>();
		
		// ACT/ASSERT
		assertNotEquals(wrapper, null);
	}
	
	@Test
	public void testNotEqualsWithNullAndNonNullContent() {
		// ARRANGE
		Wrapper<String> wrapper1 = new Wrapper<String>();
		String object = TestConstants.TEST_STRING_ONE;
		Wrapper<String> wrapper2 = new Wrapper<String>(object);
		
		// ACT/ASSERT
		assertNotEquals(wrapper1, wrapper2);
		assertNotEquals(wrapper2, wrapper1);
	}
	
	@Test
	public void testNotEqualsWithDifferentContent() {
		// ARRANGE
		String object1 = TestConstants.TEST_STRING_ONE;
		Wrapper<String> wrapper1 = new Wrapper<String>(object1);
		String object2 = TestConstants.TEST_STRING_TWO;
		Wrapper<String> wrapper2 = new Wrapper<String>(object2);
		
		// ACT/ASSERT
		assertNotEquals(wrapper1, wrapper2);
		assertNotEquals(wrapper2, wrapper1);
	}
	
	@Test
	public void testNotEqualsDifferentlyTypeWrapper() {
		// ARRANGE
		Wrapper<Integer> numberWrapper = new Wrapper<Integer>(0);
		Wrapper<String> stringWrapper = new Wrapper<String>(TestConstants.TEST_STRING_ONE);
		
		// ACT/ASSERT
		assertNotEquals(numberWrapper, stringWrapper);
		assertNotEquals(stringWrapper, numberWrapper);
	}
	
	@Test
	public void testJsonSerialization() {
		// ARRANGE
		String object = TestConstants.TEST_STRING_ONE;
		Wrapper<String> wrapper = new Wrapper<String>(object);
		
		String expectedJsonSerialization = String.format("{\"wrappedObject\":\"%s\"}", object);
		
		// ACT/ASSERT
		JsonSerializerTests.assertJsonSerialization(wrapper, expectedJsonSerialization);
	}
	
	@Test
	public void testJsonDeserialization() {
		// ARRANGE
		String object = TestConstants.TEST_STRING_ONE;
		String jsonSerialization = String.format("{\"wrappedObject\":\"%s\"}", object);
		
		Wrapper<String> expectedWrapper = new Wrapper<String>(object);
		
		// ACT/ASSERT
		JsonSerializerTests.assertJsonDeserialization(jsonSerialization, expectedWrapper, new TypeReference<Wrapper<String>>(){});
	}
	
}
