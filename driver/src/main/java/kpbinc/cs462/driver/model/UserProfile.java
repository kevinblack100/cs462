package kpbinc.cs462.driver.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import kpbinc.cs462.shared.model.manage.InMemoryPersistentUserDetailsManager;
import kpbinc.util.logging.GlobalLogUtils;

/**
 * Stores extra information about a user than contained in the UserDetails objects managed by
 * {@link InMemoryPersistentUserDetailsManager}.
 * 
 * @author Kevin Black
 */
public class UserProfile {

	//= Member Data ====================================================================================================
	
	private String username;
	private Map<String, String> apiIDs; // api -> api-ID
	private Double lastKnownLatitude;
	private Double lastKnownLongitude;
	private String textableNumber;
	
	
	//= Initialization =================================================================================================
	
	public UserProfile(@JsonProperty("username") String username) {
		GlobalLogUtils.logConstruction(this);
		setUsername(username);
		setApiIDs(new HashMap<String, String>());
	}

	
	//= Interface ======================================================================================================

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		if (username == null) {
			throw new IllegalArgumentException("username must not be null");
		}
		this.username = username;
	}

	public Map<String, String> getApiIDs() {
		return apiIDs;
	}

	public void setApiIDs(Map<String, String> apiIDs) {
		if (apiIDs == null) {
			throw new IllegalArgumentException("API IDs must not be null");
		}
		this.apiIDs = apiIDs;
	}

	public void addApiID(String api, String id) {
		getApiIDs().put(api, id);
	}

	public Double getLastKnownLatitude() {
		return lastKnownLatitude;
	}

	public void setLastKnownLatitude(Double lastKnownLatitude) {
		this.lastKnownLatitude = lastKnownLatitude;
	}

	public Double getLastKnownLongitude() {
		return lastKnownLongitude;
	}

	public void setLastKnownLongitude(Double lastKnownLongitude) {
		this.lastKnownLongitude = lastKnownLongitude;
	}

	public String getTextableNumber() {
		return textableNumber;
	}

	public void setTextableNumber(String textableNumber) {
		this.textableNumber = textableNumber;
	}
	
}