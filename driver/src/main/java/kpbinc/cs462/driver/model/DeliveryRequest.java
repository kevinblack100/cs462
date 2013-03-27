package kpbinc.cs462.driver.model;

import kpbinc.cs462.shared.model.Delivery;
import kpbinc.util.logging.GlobalLogUtils;

public class DeliveryRequest extends Delivery {

	//= Class Data =====================================================================================================
	
	public enum State {
		AVAILABLE_FOR_BID,
		AWARDED,
		NOT_AWARDED,
		PICKED_UP,
		DELIVERED;
	}
	
	
	//= Member Data ====================================================================================================
	
	private State state;
	private String username;
	private Long shopDeliveryId;
	private String shopName;
	private String shopAddress;
	private Long shopLatitude;
	private Long shopLongitude;
	
	
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

	public Long getShopLatitude() {
		return shopLatitude;
	}

	public void setShopLatitude(Long shopLatitude) {
		this.shopLatitude = shopLatitude;
	}

	public Long getShopLongitude() {
		return shopLongitude;
	}

	public void setShopLongitude(Long shopLongitude) {
		this.shopLongitude = shopLongitude;
	}

}
