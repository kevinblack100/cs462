package kpbinc.cs462.shop.model;

import kpbinc.cs462.shared.model.aspect.HasId;
import kpbinc.util.logging.GlobalLogUtils;

public class Order implements HasId<Long> {

	//= Class Data =====================================================================================================
	
	public enum State {
		WAITING_FOR_BIDS,
		WAITING_FOR_PICKUP,
		WAITING_FOR_DELIVERY,
		DELIVERED;
	}
	
	
	//= Member Data ====================================================================================================
	
	private Long id;
	private State state;
	private String pickupTime;
	private String deliveryAddress;
	private String deliveryTime;
	private Long selectedBidID;
	
	
	//= Initialization =================================================================================================
	
	public Order() {
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
	
	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
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
