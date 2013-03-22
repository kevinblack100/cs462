package kpbinc.cs462.shared.model;

import kpbinc.cs462.shared.model.aspect.HasId;
import kpbinc.util.logging.GlobalLogUtils;

public class FlowerShopProfile implements HasId<Long> {

	//= Member Data ====================================================================================================
	
	private Long id;
	private String name;
	private String location;
	private Double latitude;
	private Double longitude;
	
	
	//= Initialization =================================================================================================
	
	public FlowerShopProfile() {
		GlobalLogUtils.logConstruction(this);
	}


	//= Interface ======================================================================================================
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
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

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

}
