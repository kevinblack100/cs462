package kpbinc.cs462.shop.model;

import kpbinc.cs462.shop.model.DriverProfile;
import kpbinc.test.util.ActAndAssertJsonSerializer;

import org.junit.Test;

public class DriverProfileTests {

	@Test
	public void testJsonSerialization() {
		// ARRANGE
		String username = "avgjoe";
		String esl = "http://something.somedomain.net/esl/for/avgjoe";
		DriverProfile profile = new DriverProfile(username);
		profile.setEventSignalURL(esl);
		
		String expectedJsonSerialization = String.format("{" +
				"\"username\":\"%s\"," +
				"\"eventSignalURL\":\"%s\"" +
				"}",
				username, esl);
		
		// ACT/ASSERT
		ActAndAssertJsonSerializer.assertJsonSerialization(profile, expectedJsonSerialization);
	}
	
	@Test
	public void testJsonDeserialization() {
		// ARRANGE
		String username = "avgjoe";
		String esl = "http://something.somedomain.net/esl/for/avgjoe";
		DriverProfile profile = new DriverProfile(username);
		profile.setEventSignalURL(esl);
		
		String jsonSerialization = String.format("{" +
				"\"username\":\"%s\"," +
				"\"eventSignalURL\":\"%s\"" +
				"}",
				username, esl);
		
		// ACT/ASSERT
		ActAndAssertJsonSerializer.assertJsonDeserialization(jsonSerialization, profile, DriverProfile.class);
	}

}