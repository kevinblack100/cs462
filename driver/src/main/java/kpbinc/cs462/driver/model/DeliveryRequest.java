package kpbinc.cs462.driver.model;

import kpbinc.cs462.shared.model.Delivery;
import kpbinc.util.logging.GlobalLogUtils;

public class DeliveryRequest extends Delivery {

	//= Class Data =====================================================================================================
	
	public enum State {
		AVAILABLE_FOR_BID,
		QUOTED_MANUALLY,
		QUOTED_AUTOMATICALLY,
		QUOTED_SEMIAUTOMATICALLY,
		AWARDED,
		NOT_AWARDED,
		PICKED_UP,
		DELIVERED;
	}
	
	
	//= Member Data ====================================================================================================
	
	private State state;
	
	private String username;
	
	private Long shopDeliveryId;
	
	private Long shopId;
	private String shopName;
	private String shopAddress;
	private Double shopLatitude;
	private Double shopLongitude;
	
	private String estimatedDeliveryTime;
	private Float bidAmount;
	private String bidAmountUnits;
	
	
	//= Initialization =================================================================================================	
	
	public DeliveryRequest() {
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
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public Long getShopDeliveryId() {
		return shopDeliveryId;
	}

	public void setShopDeliveryId(Long shopDeliveryId) {
		this.shopDeliveryId = shopDeliveryId;
	}

	public Long getShopId() {
		return shopId;
	}
	
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	
	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

	public Double getShopLatitude() {
		return shopLatitude;
	}

	public void setShopLatitude(Double shopLatitude) {
		this.shopLatitude = shopLatitude;
	}

	public Double getShopLongitude() {
		return shopLongitude;
	}

	public void setShopLongitude(Double shopLongitude) {
		this.shopLongitude = shopLongitude;
	}

	public String getEstimatedDeliveryTime() {
		return estimatedDeliveryTime;
	}

	public void setEstimatedDeliveryTime(String estimatedDeliveryTime) {
		this.estimatedDeliveryTime = estimatedDeliveryTime;
	}

	public Float getBidAmount() {
		return bidAmount;
	}

	public void setBidAmount(Float amount) {
		this.bidAmount = amount;
	}

	public String getBidAmountUnits() {
		return bidAmountUnits;
	}

	public void setBidAmountUnits(String amountUnits) {
		this.bidAmountUnits = amountUnits;
	}

}
