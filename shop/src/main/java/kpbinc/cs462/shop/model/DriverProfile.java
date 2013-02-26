package kpbinc.cs462.shop.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DriverProfile {

	//==================================================================================================================
	// Member Data
	//==================================================================================================================
	
	private String username;
	private String eventSignalURL;
	
	
	//==================================================================================================================
	// Initialization
	//==================================================================================================================
	
	public DriverProfile(@JsonProperty("username") String username) {
		setUsername(username);
	}

	
	//==================================================================================================================
	// Interface
	//==================================================================================================================
	
	@Override
	public boolean equals(Object object) {
		boolean result = false;
		
		if (   object != null
			&& object instanceof DriverProfile) {
			DriverProfile other = (DriverProfile) object;
			// TODO make null-safe comparisons
			result = (   this.getUsername().equals(other.getUsername())
					  && this.getEventSignalURL().equals(other.getEventSignalURL()));
		}
		
		return result;
	}
	
	public String getUsername() {
		return username;
	}
	
	private void setUsername(String username) {
		assert(username != null);
		this.username = username;
	}
	
	public String getEventSignalURL() {
		return eventSignalURL;
	}
	
	public void setEventSignalURL(String eventSignalURL) {
		this.eventSignalURL = eventSignalURL;
	}
	
}
