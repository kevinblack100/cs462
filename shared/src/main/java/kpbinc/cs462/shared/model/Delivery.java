package kpbinc.cs462.shared.model;

import kpbinc.cs462.shared.model.aspect.HasId;
import kpbinc.util.logging.GlobalLogUtils;

public class Delivery implements HasId<Long> {

	//= Member Data ====================================================================================================
	
	private Long id;
	private String deliveryAddress;
	private String requestedPickupTime;
	private String requestedDeliveryTime;
	
	
	//= Initialization =================================================================================================
	
	public Delivery() {
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
	
	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getRequestedPickupTime() {
		return requestedPickupTime;
	}

	public void setRequestedPickupTime(String requestedPickupTime) {
		this.requestedPickupTime = requestedPickupTime;
	}

	public String getRequestedDeliveryTime() {
		return requestedDeliveryTime;
	}

	public void setRequestedDeliveryTime(String requestedDeliveryTime) {
		this.requestedDeliveryTime = requestedDeliveryTime;
	}

}
