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
	private Map<Long, Map<String, String>> deliveryReadyESLs;
	
	
	//= Initialization =================================================================================================
	
	public DriverProfile(@JsonProperty("username") String username) {
		GlobalLogUtils.logConstruction(this);
		setUsername(username);
		deliveryReadyESLs = new DecoratedMap<Long, Map<String, String>>(new HashMap<Long, Map<String, String>>());
	}

	
	//= Interface ======================================================================================================
	
	@Override
	public boolean equals(Object object) {
		boolean result = false;
		if (   object != null 
			&& object instanceof DriverProfile) {
			DriverProfile other = (DriverProfile) object;
			result = (   StringUtils.equals(this.username, other.username)
					  && this.deliveryReadyESLs.equals(other.deliveryReadyESLs));
		}
		return result;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Map<Long, Map<String, String>> getDeliveryReadyESLs() {
		return deliveryReadyESLs;
	}

	public void setDeliveryReadyESLs(Map<Long, Map<String, String>> deliveryReadyESLs) {
		if (DecoratedMap.class.isInstance(deliveryReadyESLs)) {
			this.deliveryReadyESLs = deliveryReadyESLs;
		}
		else {
			this.deliveryReadyESLs = new DecoratedMap<Long, Map<String, String>>(deliveryReadyESLs);
		}
	}
	
	public void addDeliveryReadyESL(Long shopID, String eventFullName, String deliveryReadyESL) {
		Map<String, String> eventToESLMap = deliveryReadyESLs.get(shopID);
		if (eventToESLMap == null) {
			eventToESLMap = new HashMap<String, String>();
			deliveryReadyESLs.put(shopID, eventToESLMap);
		}
		eventToESLMap.put(eventFullName, deliveryReadyESL);
	}

}
