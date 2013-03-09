package kpbinc.cs462.shared.event;

import static kpbinc.cs462.shared.event.CommonEventSerializationConstants.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class URLEncodeEventSerializerTests {

	//= Class Data =====================================================================================================
	
	private static final String DEFAULT_DOMAIN = "test";

	
	//= ACT/ASSERT =====================================================================================================
	
	private void actAndAssert(Event event, String expectedSerialization) {
		// ACT
		EventSerializer serializer = new URLEncodeEventSerializer();
		String actualSerialization = serializer.serialize(event);
		
		// ASSERT
		assertEquals(expectedSerialization, actualSerialization);
	}
	
	
	//= Tests ==========================================================================================================
	
	@Test
	public void testEncodeDomainAndName() throws EventRenderingException {
		// ARRANGE
		String name = "encode-domain-and-name";
		Event event = new BasicEventImpl(DEFAULT_DOMAIN, name);
		String expectedSerialization = String.format("%s=%s&%s=%s",
				DOMAIN_KEY, event.getDomain(),
				NAME_KEY, event.getName());
		
		actAndAssert(event, expectedSerialization);
	}

	@Test
	public void testEncodeWithOneAttribute() throws EventRenderingException {
		// ARRANGE
		String name = "encode-domain-and-name";
		BasicEventImpl event = new BasicEventImpl(DEFAULT_DOMAIN, name);
		
		String attrib1Name = "attrib1";
		String attrib1Value = "Hello World";
		event.addAttribute(attrib1Name, attrib1Value);
		
		String expectedSerialization = String.format("%s=%s&%s=%s&%s=%s",
				DOMAIN_KEY, event.getDomain(),
				NAME_KEY, event.getName(),
				// TODO create a URLEncoder wrapper that will not throw UnsupportedEncodingExceptions and use it here
				attrib1Name, "Hello+World");
		
		actAndAssert(event, expectedSerialization);
	}
	
	/**
	 * Validate that the encoder properly separates (and encodes) multiple attribute name-value pairs.
	 */
	@Test
	public void testEncodeWithTwoAttributes() throws EventRenderingException {		
		// ARRANGE
		String name = "encode-domain-and-name";
		BasicEventImpl event = new BasicEventImpl(DEFAULT_DOMAIN, name);
		
		String attrib1Name = "attrib1";
		String attrib1Value = "Hello World";
		event.addAttribute(attrib1Name, attrib1Value);
		
		String attrib2Name = "attrib2";
		String attrib2Value = "shared-code";
		event.addAttribute(attrib2Name, attrib2Value);
		
		String expectedSerialization = String.format("%s=%s&%s=%s&%s=%s&%s=%s",
				DOMAIN_KEY, event.getDomain(),
				NAME_KEY, event.getName(),
				// TODO create a URLEncoder wrapper that will not throw UnsupportedEncodingExceptions and use it here
				attrib1Name, "Hello+World",	
				attrib2Name, attrib2Value);
		
		actAndAssert(event, expectedSerialization);
	}
	
	/**
	 * Validate that the encoder properly encodes multi-valued attributes
	 */
	@Test
	public void testEncodeMultiValuedAttribute() throws EventRenderingException {
		// ARRANGE
		String name = "encode-domain-and-name";
		BasicEventImpl event = new BasicEventImpl(DEFAULT_DOMAIN, name);
		
		String attrib1Name = "attrib1";
		String attrib1Value1 = "Hello World";
		event.addAttribute(attrib1Name, attrib1Value1);
		
		String attrib1Value2 = "Goodbye";
		event.addAttribute(attrib1Name, attrib1Value2);
		
		String expectedSerialization = String.format("%s=%s&%s=%s&%s=%s&%s=%s",
				DOMAIN_KEY, event.getDomain(),
				NAME_KEY, event.getName(),
				// TODO create a URLEncoder wrapper that will not throw UnsupportedEncodingExceptions and use it here
				attrib1Name, "Hello+World",	
				attrib1Name, attrib1Value2);
		
		actAndAssert(event, expectedSerialization);
	}
	
}
