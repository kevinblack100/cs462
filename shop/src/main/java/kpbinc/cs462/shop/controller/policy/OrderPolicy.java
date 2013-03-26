package kpbinc.cs462.shop.controller.policy;

import kpbinc.cs462.shared.model.GrantedAuthorityRoles;
import kpbinc.cs462.shop.model.Order;
import kpbinc.util.logging.GlobalLogUtils;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request")
public class OrderPolicy {

	//= Initialization =================================================================================================
	
	public OrderPolicy() {
		GlobalLogUtils.logConstruction(this);
	}
	
	
	//= Interface ======================================================================================================
	
	//- Create ---------------------------------------------------------------------------------------------------------
	
	public boolean maySubmit(UserDetails user) {
		boolean result = isAdmin(user);
		return result;
	}
	
	//- Update ---------------------------------------------------------------------------------------------------------
	
	public boolean maySelectBid(UserDetails user, Order order) {
		boolean result = (   isAdmin(user)
						  && orderStateIs(order, Order.State.WAITING_FOR_BIDS));
		return result;
	}
	
	
	//= Support ========================================================================================================
	
	private boolean isAdmin(UserDetails user) {
		boolean result = (   user != null
				  		  && user.getAuthorities().contains(GrantedAuthorityRoles.ROLE_ADMIN));
		return result;
	}
	
	private boolean orderStateIs(Order order, Order.State state) {
		boolean result = (   order != null
						  && state != null
						  && state.equals(order.getState()));
		return result;
	}
}
