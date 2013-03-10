package kpbinc.cs462.driver.model;

import kpbinc.test.util.ActAndAssertJsonSerializer;

import org.junit.Test;

public class DriverProfileTests {

	@Test
	public void testJsonSerialization() {
		// ARRANGE
		String username = "avgjoe";
		DriverProfile profile = new DriverProfile(username);
		Long shopID = 1l;
		String esl = "http://something.somedomain.net/deliveryready/esl/for/avgjoe";
		profile.addDeliveryReadyESL(shopID, esl);
		
		StringBuilder builder = new StringBuilder()
			.append("{")
			.append("\"username\":\"%s\",")
			.append("\"deliveryReadyESLs\":")
				.append("{")
					.append("\"%d\":\"%s\"")
				.append("}")
			.append("}");
		String expectedJsonSerialization = String.format(builder.toString(), username, shopID, esl);
		
		// ACT/ASSERT
		ActAndAssertJsonSerializer.assertJsonSerialization(profile, expectedJsonSerialization);
	}
	
	@Test
	public void testJsonDeserialization() {
		// ARRANGE
		String username = "avgjoe";
		DriverProfile profile = new DriverProfile(username);
		Long shopID = 1l;
		String esl = "http://something.somedomain.net/deliveryready/esl/for/avgjoe";
		profile.addDeliveryReadyESL(shopID, esl);
		
		StringBuilder builder = new StringBuilder()
			.append("{")
			.append("\"username\":\"%s\",")
			.append("\"deliveryReadyESLs\":")
				.append("{")
					.append("\"%d\":\"%s\"")
				.append("}")
			.append("}");
		String jsonSerialization = String.format(builder.toString(), username, shopID, esl);
		
		// ACT/ASSERT
		ActAndAssertJsonSerializer.assertJsonDeserialization(jsonSerialization, profile, DriverProfile.class);
	}

}
