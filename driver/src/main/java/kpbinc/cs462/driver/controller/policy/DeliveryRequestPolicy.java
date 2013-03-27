package kpbinc.cs462.driver.controller.policy;

import kpbinc.cs462.driver.model.DeliveryRequest;
import kpbinc.cs462.driver.model.manage.DeliveryRequestManager;
import kpbinc.util.logging.GlobalLogUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request")
public class DeliveryRequestPolicy {

	//= Initialization =================================================================================================
	
	public DeliveryRequestPolicy() {
		GlobalLogUtils.logConstruction(this);
	}

	
	//= Interface ======================================================================================================
	
	public boolean mayCompleteDelivery(UserDetails userDetails, DeliveryRequest deliveryRequest) {
		boolean result = (   userDetails != null
						  && deliveryRequest != null
						  && deliveryRequest.getState().equals(DeliveryRequest.State.PICKED_UP)
						  && StringUtils.equalsIgnoreCase(userDetails.getUsername(), deliveryRequest.getUsername()));
		return result;
	}
	
}
