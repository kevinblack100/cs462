package kpbinc.cs462.shop.model;

import kpbinc.util.logging.GlobalLogUtils;

public class Order {

	//= Member Data ====================================================================================================
	
	private Long orderID;
	private String pickupTime;
	private String deliveryAddress;
	private String deliveryTime;
	private Long selectedBidID;
	
	
	//= Initialization =================================================================================================
	
	public Order() {
		GlobalLogUtils.logConstruction(this);
	}

	
	//= Interface ======================================================================================================
	
	public Long getOrderID() {
		return orderID;
	}

	public void setOrderID(Long orderID) {
		this.orderID = orderID;
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
