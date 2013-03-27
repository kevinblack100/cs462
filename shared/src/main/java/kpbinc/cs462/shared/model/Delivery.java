package kpbinc.cs462.shared.model;

import kpbinc.cs462.shared.model.aspect.HasId;
import kpbinc.util.logging.GlobalLogUtils;

public class Delivery implements HasId<Long> {

	//= Member Data ====================================================================================================
	
	private Long id;
	private String pickupTime;
	private String deliveryAddress;
	private String deliveryTime;
	private Long selectedBidID;
	
	
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

	public String getPickupTime() {
		return pickupTime;
	}

	public void setPickupTime(String pickupTime) {
		this.pickupTime = pickupTime;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Long getSelectedBidID() {
		return selectedBidID;
	}

	public void setSelectedBidID(Long selectedBidID) {
		this.selectedBidID = selectedBidID;
	}

}
