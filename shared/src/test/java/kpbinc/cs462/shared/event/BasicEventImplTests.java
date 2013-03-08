package kpbinc.cs462.shared.event;

import static org.junit.Assert.*;

import org.junit.Test;

public class BasicEventImplTests {

	//= Class Data =====================================================================================================
	
	private static final String DEFAULT_DOMAIN = "test";
	
	
	//= Tests ==========================================================================================================
	
	@Test
	public void testConstructorSuccess() {
		// ARRANGE
		String name = "constructor-success";
		
		// ACT
		Event event = new BasicEventImpl(DEFAULT_DOMAIN, name);
		
		// ASSERT
		assertEquals("domain", DEFAULT_DOMAIN, event.getDomain());
		assertEquals("name", name, event.getName());
	}

	@Test
	public void testAddAttribute() {
		// ARRANGE
		String name = "add-attribute";
		BasicEventImpl event = new BasicEventImpl(DEFAULT_DOMAIN, name);
		String attrib1Name = "attrib1";
		
		// ACT
		Object previousValue = event.addAttribute(attrib1Name, "Hello World");
		
		// ASSERT
		assertNull("previous value", previousValue);
		assertTrue("event contains " + attrib1Name, event.getAttributes().containsKey(attrib1Name));
	}
	
}
