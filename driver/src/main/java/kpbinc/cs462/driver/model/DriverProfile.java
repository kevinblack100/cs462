package kpbinc.cs462.driver.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

import kpbinc.util.DecoratedMap;
import kpbinc.util.logging.GlobalLogUtils;

public class DriverProfile {

	//= Member Data ====================================================================================================
	
	private String username;
	private Map<Long, Map<String, String>> registeredESLs;
	
	
	//= Initialization =================================================================================================
	
	public DriverProfile(@JsonProperty("username") String username) {
		GlobalLogUtils.logConstruction(this);
		setUsername(username);
		registeredESLs = new DecoratedMap<Long, Map<String, String>>(new HashMap<Long, Map<String, String>>());
	}

	
	//= Interface ======================================================================================================
	
	@Override
	public boolean equals(Object object) {
		boolean result = false;
		if (   object != null 
			&& object instanceof DriverProfile) {
			DriverProfile other = (DriverProfile) object;
			result = (   StringUtils.equals(this.username, other.username)
					  && this.registeredESLs.equals(other.registeredESLs));
		}
		return result;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Map<Long, Map<String, String>> getRegisteredESLs() {
		return registeredESLs;
	}

	public void setRegisteredESLs(Map<Long, Map<String, String>> registeredESLs) {
		if (DecoratedMap.class.isInstance(registeredESLs)) {
			this.registeredESLs = registeredESLs;
		}
		else {
			this.registeredESLs = new DecoratedMap<Long, Map<String, String>>(registeredESLs);
		}
	}
	
	public void addRegisteredESL(Long shopID, String eventFullName, String esl) {
		Map<String, String> eventToESLMap = registeredESLs.get(shopID);
		if (eventToESLMap == null) {
			eventToESLMap = new HashMap<String, String>();
			registeredESLs.put(shopID, eventToESLMap);
		}
		eventToESLMap.put(eventFullName, esl);
	}

}
