package kpbinc.cs462.shop.model;

import kpbinc.cs462.shared.model.aspect.HasId;
import kpbinc.util.logging.GlobalLogUtils;

public class Order implements HasId<Long> {

	//= Member Data ====================================================================================================
	
	private Long id;
	private String pickupTime;
	private String deliveryAddress;
	private String deliveryTime;
	private Long selectedBidID;
	
	
	//= Initialization =================================================================================================
	
	public Order() {
		GlobalLogUtils.logConstruction(this);
	}

	
	//= Interface ======================================================================================================
	
	public Long getId() {
		return id;
	}

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
