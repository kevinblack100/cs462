package kpbinc.poc;

import org.apache.commons.lang3.Validate;
import org.junit.Test;

public class ApacheCommonsLangValidatorTests {

	@Test(expected = NullPointerException.class) // ASSERT
	public void testNotNull() {
		// ACT -> ASSERT
		Validate.notNull(null);
	}
	
	@Test(expected = NullPointerException.class) // ASSERT
	public void testNotNullWithMessage() {
		// ACT -> ASSERT
		Validate.notNull(null, "message");
	}

}
