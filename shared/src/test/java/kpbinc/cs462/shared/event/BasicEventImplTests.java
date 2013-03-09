package kpbinc.cs462.shared.event;

import static kpbinc.cs462.shared.event.CommonEventSerializationConstants.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class BasicEventImplTests {

	//= Class Data =====================================================================================================
	
	private static final String DEFAULT_DOMAIN = "test";
	
	
	//= Tests ==========================================================================================================
	
	@Test
	public void testConstructorSuccess() throws EventRenderingException {
		// ARRANGE
		String name = "constructor-success";
		
		// ACT
		Event event = new BasicEventImpl(DEFAULT_DOMAIN, name);
		
		// ASSERT
		assertEquals("domain", DEFAULT_DOMAIN, event.getDomain());
		assertEquals("name", name, event.getName());
	}
	
	@Test(expected = EventRenderingException.class) //< ASSERT
	public void testConstructorFailure() throws EventRenderingException {
		// ARRANGE nothing
		
		// ACT -> ASSERT
		@SuppressWarnings("unused")
		Event event = new BasicEventImpl("", " ");
	}

	@Test
	public void testAddSingleValuedAttribute() throws EventRenderingException  {
		// ARRANGE
		String name = "add-single-valued-attribute";
		BasicEventImpl event = new BasicEventImpl(DEFAULT_DOMAIN, name);
		String attrib1Name = "attrib1";
		String attrib1Value = "Hello World";
		
		List<Object> expectedValueStructure = new ArrayList<Object>();
		expectedValueStructure.add(attrib1Value);
		
		// ACT
		event.addAttribute(attrib1Name, attrib1Value);
		
		// ASSERT
		assertTrue("event contains " + attrib1Name, event.getAttributes().containsKey(attrib1Name));
		assertEquals(attrib1Name + " value", expectedValueStructure, event.getAttributes().get(attrib1Name));
	}
	
	@Test
	public void testAddMultiValuedAttribute() throws EventRenderingException  {
		// ARRANGE
		String name = "add-multi-valued-attribute";
		BasicEventImpl event = new BasicEventImpl(DEFAULT_DOMAIN, name);
		
		String attrib1Name = "attrib1";
		String attrib1Value1 = "Hello World";
		String attrib1Value2 = "Goodbye";
		
		List<Object> expectedValueStructure = new ArrayList<Object>();
		expectedValueStructure.add(attrib1Value1);
		expectedValueStructure.add(attrib1Value2);
		
		// ACT
		event.addAttribute(attrib1Name, attrib1Value1);
		event.addAttribute(attrib1Name, attrib1Value2);
		
		// ASSERT
		assertTrue("event contains " + attrib1Name, event.getAttributes().containsKey(attrib1Name));
		assertEquals(attrib1Name + " value structure", expectedValueStructure, event.getAttributes().get(attrib1Name));
	}
	
	@Test(expected = EventRenderingException.class) //< ASSERT
	public void testAddWellknownReservedAttribute() throws EventRenderingException {
		// ARRANGE
		String name = "add-wellknown-reserved-attribute";
		BasicEventImpl event = new BasicEventImpl(DEFAULT_DOMAIN, name);
		
		// ACT -> ASSERT
		event.addAttribute(DOMAIN_KEY, "alternate");
	}
	
	@Test(expected = EventRenderingException.class) //< ASSERT
	public void testAddNonWellknownReservedAttribute() throws EventRenderingException {
		// ARRANGE
		String name = "add-non-wellknown-reserved-attribute";
		BasicEventImpl event = new BasicEventImpl(DEFAULT_DOMAIN, name);
		
		String reservedAttribName = "_anystring";
		assertTrue(isReservedAttributeName(reservedAttribName));
		
		// ACT -> ASSERT
		event.addAttribute(reservedAttribName, "somestring");
	}
	
}
