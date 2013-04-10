package kpbinc.cs462.shared.event;

import static kpbinc.cs462.shared.event.CommonEventSerializationConstants.*;

import static org.junit.Assert.*;

import org.junit.Test;

public class CommonEventSerializationConstantsTests {

	@Test
	public void testIsReservedAttributeName() {
		// ARRANGE nothing
		
		// ACT/ASSERT
		assertTrue(isReservedAttributeName(STANDARD_DOMAIN_KEY));
		assertTrue(isReservedAttributeName(STANDARD_NAME_KEY));
		assertTrue(isReservedAttributeName("_timestamp"));
		assertFalse(isReservedAttributeName("test"));
	}

}
