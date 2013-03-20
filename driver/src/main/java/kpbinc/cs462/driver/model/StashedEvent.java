package kpbinc.cs462.driver.model;

import kpbinc.cs462.shared.event.Event;
import kpbinc.cs462.shared.model.aspect.HasId;
import kpbinc.util.logging.GlobalLogUtils;

public class StashedEvent implements HasId<Long> {

	//= Member Data ====================================================================================================
	
	private Long id;
	private Event event;
	private Long shopProfileID;
	private String driverUsername;
	
	
	//= Initialization =================================================================================================
	
	public StashedEvent() {
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
	
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
	
	public Long getShopProfileID() {
		return shopProfileID;
	}

	public void setShopProfileID(Long shopProfileID) {
		this.shopProfileID = shopProfileID;
	}

	public String getDriverUsername() {
		return driverUsername;
	}

	public void setDriverUsername(String driverUsername) {
		this.driverUsername = driverUsername;
	}
	
}
