package kpbinc.cs462.ffds.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller(value = "loginController")
@Scope(value = "request")
@RequestMapping(value = "/secure/signin")
public class LoginController implements Serializable {

	private static final long serialVersionUID = 1L;

	public LoginController() {
		int tmpbrkpnt = 1;
	}
	
	@RequestMapping(value = "/query")
	public String presentSignin() {
		return "signin";
	}

	@RequestMapping(value = "/success")
	public void doSignin(HttpServletResponse response) throws IOException {
		UserDetails signedInUserDetails = getSignedInUserDetails();
		String redirectLocation = "/cs462/ffds/";
		if (signedInUserDetails != null) {
			String username = signedInUserDetails.getUsername();
			redirectLocation += "users/" + username;
		}
		response.sendRedirect(redirectLocation);
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
