package kpbinc.cs462.driver.model;

import kpbinc.util.logging.GlobalLogUtils;

public class FlowerShopProfile {

	//= Member Data ====================================================================================================
	
	private Long id;
	private String name;
	private String location;
	private String eventSignalURL;
	
	
	//= Initialization =================================================================================================
	
	public FlowerShopProfile() {
		GlobalLogUtils.logConstruction(this);
	}


	//= Interface ======================================================================================================
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getEventSignalURL() {
		return eventSignalURL;
	}


	public void setEventSignalURL(String eventSignalURL) {
		this.eventSignalURL = eventSignalURL;
	}
	
}
