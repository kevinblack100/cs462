package kpbinc.cs462.ffds.controller.policy;

import kpbinc.cs462.ffds.model.GrantedAuthorityRoles;
import kpbinc.util.logging.GlobalLogUtils;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request")
public class OrderPolicy {

	//~ Initialization =================================================================================================
	
	public OrderPolicy() {
		GlobalLogUtils.logConstruction(this);
	}
	
	
	//~ Interface ======================================================================================================
	
	public boolean maySubmit(UserDetails user) {
		boolean result = (   user != null
						  && user.getAuthorities().contains(GrantedAuthorityRoles.ROLE_ADMIN));
		return result;
	}
	
}
