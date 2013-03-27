package kpbinc.cs462.shop.model;

import kpbinc.cs462.shared.model.aspect.HasId;
import kpbinc.util.logging.GlobalLogUtils;

public class DeliveryBid implements HasId<Long> {

	//= Member Data ====================================================================================================
	
	public Long id;
	public Long orderID;
	public String driverId;
	public String driverName;
	public Long driverRanking;
	public String estimatedDeliveryTime;
	public Double amount;
	public String amountUnits;
	
	
	//= Initialization =================================================================================================
	
	public DeliveryBid() {
		GlobalLogUtils.logConstruction(this);
	}

	
	//= Interface ======================================================================================================
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderID() {
		return orderID;
	}

	public void setOrderID(Long orderID) {
		this.orderID = orderID;
	}

	public String getDriverId() {
		return driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	
	public Long getDriverRanking() {
		return driverRanking;
	}

	public void setDriverRanking(Long driverRanking) {
		this.driverRanking = driverRanking;
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
