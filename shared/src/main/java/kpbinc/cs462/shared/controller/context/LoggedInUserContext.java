package kpbinc.cs462.shared.controller.context;

import kpbinc.util.logging.GlobalLogUtils;

import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request")
public class LoggedInUserContext {

	//~ Initialization =================================================================================================
	
	public LoggedInUserContext() {
		GlobalLogUtils.logConstruction(this);
	}
	
	
	//~ Interface ======================================================================================================
	
	public boolean isUserLoggedIn(String username) {
		UserDetails loggedInUserDetails = getSignedInUserDetails();
		boolean result = (   loggedInUserDetails != null
						  && loggedInUserDetails.getUsername().equals(username));
		return result;
	}
	
	public UserDetails getSignedInUserDetails() {
		UserDetails signedInUserDetails = null;
		
		SecurityContext context = SecurityContextHolder.getContext();
		if (context != null) {
			Authentication token = context.getAuthentication();
			if (   token != null
				&& token instanceof UsernamePasswordAuthenticationToken) {
				
				Object principal = token.getPrincipal();
				if (   principal != null
					&& principal instanceof UserDetails) {
					signedInUserDetails = (UserDetails) principal;
				}
				else {
					// TODO notify of unhandled change to Spring API?
				}
			}
		}
		
		return signedInUserDetails;
	}
	
}
