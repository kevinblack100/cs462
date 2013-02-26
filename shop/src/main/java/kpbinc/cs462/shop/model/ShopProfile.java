package kpbinc.cs462.shop.model;

import kpbinc.util.logging.GlobalLogUtils;

public class ShopProfile {

	//~ Class Data =====================================================================================================
	
	private static final String DEFAULT_VALUE = "unknown";
	
	
	//~ Member Data ====================================================================================================
	
	private String name;
	
	private String address;
	
	
	//~ Initialization =================================================================================================
	
	public ShopProfile() {
		GlobalLogUtils.logConstruction(this);
		setName(DEFAULT_VALUE);
		setAddress(DEFAULT_VALUE);
	}
	
	public ShopProfile(String name, String address) {
		GlobalLogUtils.logConstruction(this);
		setName(name);
		setAddress(address);
	}

	
	//~ Interface ======================================================================================================

	public String getName() {
		return name;
	}

	public void setName(String name) {
		assert(name != null);
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		assert(address != null);
		this.address = address;
	}
	
}
