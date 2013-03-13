package kpbinc.cs462.shop.model;

import kpbinc.util.logging.GlobalLogUtils;

public class DeliveryBid {

	//= Member Data ====================================================================================================
	
	public Long bidID;
	public Long orderID;
	public String estimatedDeliveryTime;
	public Double amount;
	public String amountUnits;
	
	
	//= Initialization =================================================================================================
	
	public DeliveryBid() {
		GlobalLogUtils.logConstruction(this);
	}

	
	//= Interface ======================================================================================================
	
	public Long getBidID() {
		return bidID;
	}

	public void setBidID(Long bidID) {
		this.bidID = bidID;
	}

	public Long getOrderID() {
		return orderID;
	}

	public void setOrderID(Long orderID) {
		this.orderID = orderID;
	}

	public String getEstimatedDeliveryTime() {
		return estimatedDeliveryTime;
	}

	public void setEstimatedDeliveryTime(String estimatedDeliveryTime) {
		this.estimatedDeliveryTime = estimatedDeliveryTime;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getAmountUnits() {
		return amountUnits;
	}

	public void setAmountUnits(String amountUnits) {
		this.amountUnits = amountUnits;
	}

}
