package kpbinc.cs462.shop.model;

import kpbinc.cs462.shared.model.Delivery;
import kpbinc.util.logging.GlobalLogUtils;

public class Order extends Delivery {

	//= Class Data =====================================================================================================
	
	public enum State {
		WAITING_FOR_BIDS,
		WAITING_FOR_PICKUP,
		WAITING_FOR_DELIVERY,
		DELIVERED;
	}
	
	
	//= Member Data ====================================================================================================
	
	private State state;
	private Long selectedBidID;

	
	//= Initialization =================================================================================================
	
	public Order() {
		GlobalLogUtils.logConstruction(this);
	}

	
	//= Interface ======================================================================================================
	
	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public Long getSelectedBidID() {
		return selectedBidID;
	}

	public void setSelectedBidID(Long selectedBidID) {
		this.selectedBidID = selectedBidID;
	}

}
