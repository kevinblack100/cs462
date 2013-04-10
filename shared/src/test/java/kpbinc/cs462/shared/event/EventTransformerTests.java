package kpbinc.cs462.shared.event;

import static kpbinc.cs462.shared.event.CommonEventSerializationConstants.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

public class EventTransformerTests {

	//= Class Data =====================================================================================================
	
	private static final String DEFAULT_DOMAIN = "test";
	
	
	//= TESTS ==========================================================================================================
	
	@Test
	public void testBasicTransform() throws EventRenderingException {
		// ARRANGE
		String name = "basic-transform";
		Map<String, String[]> nameMultiValuePairs = new TreeMap<String, String[]>(); 
		nameMultiValuePairs.put(STANDARD_DOMAIN_KEY, ArrayUtils.toArray(DEFAULT_DOMAIN));
		nameMultiValuePairs.put(STANDARD_NAME_KEY, ArrayUtils.toArray(name));
		String attrib1Name = "attrib1";
		String attrib1Value = "Hello World";
		nameMultiValuePairs.put(attrib1Name, ArrayUtils.toArray(attrib1Value));
		List<Object> expectedValueStructure = new ArrayList<Object>();
		expectedValueStructure.add(attrib1Value);
		
		EventTransformer transformer = new EventTransformer();
		
		// ACT
		Event event = transformer.transform(nameMultiValuePairs);
		
		// ASSERT
		assertEquals("domain", DEFAULT_DOMAIN, event.getDomain());
		assertEquals("name", name, event.getName());
		assertTrue("has attrib1", event.getAttributes().containsKey(attrib1Name));
		assertEquals("attrib1", expectedValueStructure, event.getAttributes().get(attrib1Name));
	}

	@Test
	public void testMultiValuedAttributeTransform() throws EventRenderingException {
		// ARRANGE
		String name = "multi-valued-attribute-transform";
		Map<String, String[]> nameMultiValuePairs = new TreeMap<String, String[]>(); 
		nameMultiValuePairs.put(STANDARD_DOMAIN_KEY, ArrayUtils.toArray(DEFAULT_DOMAIN));
		nameMultiValuePairs.put(STANDARD_NAME_KEY, ArrayUtils.toArray(name));
		String attrib1Name = "attrib1";
		String attrib1Value1 = "Hello World";
		String attrib1Value2 = "Goodbye";
		nameMultiValuePairs.put(attrib1Name, ArrayUtils.toArray(attrib1Value1, attrib1Value2));
		List<Object> expectedValueStructure = new ArrayList<Object>();
		expectedValueStructure.add(attrib1Value1);
		expectedValueStructure.add(attrib1Value2);
		
		EventTransformer transformer = new EventTransformer();
		
		// ACT
		Event event = transformer.transform(nameMultiValuePairs);
		
		// ASSERT
		assertEquals("domain", DEFAULT_DOMAIN, event.getDomain());
		assertEquals("name", name, event.getName());
		assertTrue("has attrib1", event.getAttributes().containsKey(attrib1Name));
		assertEquals("attrib1", expectedValueStructure, event.getAttributes().get(attrib1Name));
	}
	
	@Test
	public void testExcludeNotWellknownAttributes() throws EventRenderingException {
		// ARRANGE
		String name = "exclude-not-wellknown-attributes";
		Map<String, String[]> nameMultiValuePairs = new TreeMap<String, String[]>(); 
		nameMultiValuePairs.put(STANDARD_DOMAIN_KEY, ArrayUtils.toArray(DEFAULT_DOMAIN));
		nameMultiValuePairs.put(STANDARD_NAME_KEY, ArrayUtils.toArray(name));
		String reservedAttribName = "_attrib";
		String reservedAttribValue = "should not be in transformed event";
		nameMultiValuePairs.put(reservedAttribName, ArrayUtils.toArray(reservedAttribValue));
		String attrib1Name = "attrib1";
		String attrib1Value = "Hello World";
		nameMultiValuePairs.put(attrib1Name, ArrayUtils.toArray(attrib1Value));
		List<Object> expectedValueStructure = new ArrayList<Object>();
		expectedValueStructure.add(attrib1Value);
		
		EventTransformer transformer = new EventTransformer();
		
		// ACT
		Event event = transformer.transform(nameMultiValuePairs);
		
		// ASSERT
		assertEquals("domain", DEFAULT_DOMAIN, event.getDomain());
		assertEquals("name", name, event.getName());
		assertTrue("has " + attrib1Name, event.getAttributes().containsKey(attrib1Name));
		assertEquals(attrib1Name, expectedValueStructure, event.getAttributes().get(attrib1Name));
		assertFalse("has " + reservedAttribName, event.getAttributes().containsKey(reservedAttribName));
	}
	
	@Test
	public void testNonStandardDomain() throws EventRenderingException {
		// ARRANGE
		String name = "non-standard-domain-transform";
		Map<String, String[]> nameMultiValuePairs = new TreeMap<String, String[]>(); 
		nameMultiValuePairs.put(NON_STANDARD_DOMAIN_KEY, ArrayUtils.toArray(DEFAULT_DOMAIN));
		nameMultiValuePairs.put(STANDARD_NAME_KEY, ArrayUtils.toArray(name));
		String attrib1Name = "attrib1";
		String attrib1Value = "Hello World";
		nameMultiValuePairs.put(attrib1Name, ArrayUtils.toArray(attrib1Value));
		List<Object> expectedValueStructure = new ArrayList<Object>();
		expectedValueStructure.add(attrib1Value);
		
		EventTransformer transformer = new EventTransformer();
		
		// ACT
		Event event = transformer.transform(nameMultiValuePairs);
		
		// ASSERT
		assertEquals("domain", DEFAULT_DOMAIN, event.getDomain());
		assertEquals("name", name, event.getName());
		assertTrue("has attrib1", event.getAttributes().containsKey(attrib1Name));
		assertEquals("attrib1", expectedValueStructure, event.getAttributes().get(attrib1Name));
	}
	
	@Test
	public void testNonStandardName() throws EventRenderingException {
		// ARRANGE
		String name = "non-standard-name-transform";
		Map<String, String[]> nameMultiValuePairs = new TreeMap<String, String[]>(); 
		nameMultiValuePairs.put(STANDARD_DOMAIN_KEY, ArrayUtils.toArray(DEFAULT_DOMAIN));
		nameMultiValuePairs.put(NON_STANDARD_NAME_KEY, ArrayUtils.toArray(name));
		String attrib1Name = "attrib1";
		String attrib1Value = "Hello World";
		nameMultiValuePairs.put(attrib1Name, ArrayUtils.toArray(attrib1Value));
		List<Object> expectedValueStructure = new ArrayList<Object>();
		expectedValueStructure.add(attrib1Value);
		
		EventTransformer transformer = new EventTransformer();
		
		// ACT
		Event event = transformer.transform(nameMultiValuePairs);
		
		// ASSERT
		assertEquals("domain", DEFAULT_DOMAIN, event.getDomain());
		assertEquals("name", name, event.getName());
		assertTrue("has attrib1", event.getAttributes().containsKey(attrib1Name));
		assertEquals("attrib1", expectedValueStructure, event.getAttributes().get(attrib1Name));
	}
	
	@Test(expected = EventRenderingException.class) //< ASSERT
	public void testMissingDomain() throws EventRenderingException {
		// ARRANGE
		String name = "missing-domain";
		Map<String, String[]> nameMultiValuePairs = new TreeMap<String, String[]>(); 
		// NB no domain
		nameMultiValuePairs.put(STANDARD_NAME_KEY, ArrayUtils.toArray(name));
		String attrib1Name = "attrib1";
		String attrib1Value = "Hello World";
		nameMultiValuePairs.put(attrib1Name, ArrayUtils.toArray(attrib1Value));
		
		EventTransformer transformer = new EventTransformer();
		
		// ACT -> ASSERT
		@SuppressWarnings("unused")
		Event event = transformer.transform(nameMultiValuePairs);
	}
	
	@Test(expected = EventRenderingException.class) //< ASSERT
	public void testMultiValuedDomain() throws EventRenderingException {
		// ARRANGE
		String name = "multi-valued-domain";
		Map<String, String[]> nameMultiValuePairs = new TreeMap<String, String[]>();
		nameMultiValuePairs.put(STANDARD_DOMAIN_KEY, ArrayUtils.toArray(DEFAULT_DOMAIN, "alternate"));
		nameMultiValuePairs.put(STANDARD_NAME_KEY, ArrayUtils.toArray(name));
		String attrib1Name = "attrib1";
		String attrib1Value = "Hello World";
		nameMultiValuePairs.put(attrib1Name, ArrayUtils.toArray(attrib1Value));
		
		EventTransformer transformer = new EventTransformer();
		
		// ACT -> ASSERT
		@SuppressWarnings("unused")
		Event event = transformer.transform(nameMultiValuePairs);
	}
	
	@Test(expected = EventRenderingException.class) //< ASSERT
	public void testMissingName() throws EventRenderingException {
		// ARRANGE
		Map<String, String[]> nameMultiValuePairs = new TreeMap<String, String[]>(); 
		nameMultiValuePairs.put(STANDARD_DOMAIN_KEY, ArrayUtils.toArray(DEFAULT_DOMAIN));
		// NB no name
		String attrib1Name = "attrib1";
		String attrib1Value = "Hello World";
		nameMultiValuePairs.put(attrib1Name, ArrayUtils.toArray(attrib1Value));
		
		EventTransformer transformer = new EventTransformer();
		
		// ACT -> ASSERT
		@SuppressWarnings("unused")
		Event event = transformer.transform(nameMultiValuePairs);
	}
	
	@Test(expected = EventRenderingException.class) //< ASSERT
	public void testMultiValuedName() throws EventRenderingException {
		// ARRANGE
		String name = "multi-valued-name";
		Map<String, String[]> nameMultiValuePairs = new TreeMap<String, String[]>(); 
		nameMultiValuePairs.put(STANDARD_DOMAIN_KEY, ArrayUtils.toArray(DEFAULT_DOMAIN));
		nameMultiValuePairs.put(STANDARD_NAME_KEY, ArrayUtils.toArray(name, "alternate"));
		String attrib1Name = "attrib1";
		String attrib1Value = "Hello World";
		nameMultiValuePairs.put(attrib1Name, ArrayUtils.toArray(attrib1Value));
		
		EventTransformer transformer = new EventTransformer();
		
		// ACT -> ASSERT
		@SuppressWarnings("unused")
		Event event = transformer.transform(nameMultiValuePairs);
	}
	
	@Test(expected = EventRenderingException.class) //< ASSERT
	public void testAttributeWithNoValues() throws EventRenderingException {
		// ARRANGE
		String name = "attribute-with-no-values";
		Map<String, String[]> nameMultiValuePairs = new TreeMap<String, String[]>(); 
		nameMultiValuePairs.put(STANDARD_DOMAIN_KEY, ArrayUtils.toArray(DEFAULT_DOMAIN));
		nameMultiValuePairs.put(STANDARD_NAME_KEY, ArrayUtils.toArray(name, "alternate"));
		String attrib1Name = "attrib1";
		nameMultiValuePairs.put(attrib1Name, new String[] {});
		
		EventTransformer transformer = new EventTransformer();
		
		// ACT -> ASSERT
		@SuppressWarnings("unused")
		Event event = transformer.transform(nameMultiValuePairs);
	}
	
	@Test(expected = EventRenderingException.class) //< ASSERT
	public void testNullValues() throws EventRenderingException {
		// ARRANGE
		String name = "attribute-with-no-values";
		Map<String, String[]> nameMultiValuePairs = new TreeMap<String, String[]>(); 
		nameMultiValuePairs.put(STANDARD_DOMAIN_KEY, ArrayUtils.toArray(DEFAULT_DOMAIN));
		nameMultiValuePairs.put(STANDARD_NAME_KEY, ArrayUtils.toArray(name, "alternate"));
		String attrib1Name = "attrib1";
		nameMultiValuePairs.put(attrib1Name, ArrayUtils.toArray((String) null));
		
		EventTransformer transformer = new EventTransformer();
		
		// ACT -> ASSERT
		@SuppressWarnings("unused")
		Event event = transformer.transform(nameMultiValuePairs);
	}	
	
}
