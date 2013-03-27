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
	private Long selectedBidId;

	
	//= Initialization =================================================================================================
	
	public Order() {
		super();
		GlobalLogUtils.logConstruction(this);
	}

	
	//= Interface ======================================================================================================
	
	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public Long getSelectedBidId() {
		return selectedBidId;
	}

	public void setSelectedBidId(Long selectedBidId) {
		this.selectedBidId = selectedBidId;
	}

}
