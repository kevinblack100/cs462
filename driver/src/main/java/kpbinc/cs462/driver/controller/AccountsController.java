package kpbinc.cs462.driver.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import kpbinc.cs462.shared.controller.context.CommonApplicationConstants;
import kpbinc.cs462.shared.controller.context.LoggedInUserContext;
import kpbinc.cs462.shared.model.InMemoryPersistentUserDetailsManager;
import kpbinc.util.logging.GlobalLogUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Scope(value = "request")
@RequestMapping(value = "/secure/accounts")
public class AccountsController extends DriverBaseSiteContextController implements Serializable {

	//= Class Data =====================================================================================================
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(AccountsController.class.getName());

	
	//= Member Data ====================================================================================================
	
	@Autowired
	private LoggedInUserContext loggedInUserContext;
	
	@Autowired
	private CommonApplicationConstants applicationConstants;
	
	@Autowired
	private InMemoryPersistentUserDetailsManager userDetailsManager;
	
	
	//= Initialization =================================================================================================
	
	public AccountsController() {
		GlobalLogUtils.logConstruction(this);
	}
	
	
	//= Interface ======================================================================================================
	
	//- Registration ---------------------------------------------------------------------------------------------------
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String getRegistrationForm() {
		return "accounts/register";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerAccount(
			@RequestParam(value = "username", required = true) String username) {
		String redirectLocation = null;
		
		try {
			// Does account already exist?
			UserDetails currentRegistrantDetails = userDetailsManager.loadUserByUsername(username);
			
			logger.info(String.format("Account with username \"%s\" already exists\n", username));
			
			redirectLocation = "/pages/secure/accounts/register";
		}
		catch (UsernameNotFoundException exception) {
			// Account does not exist
			String defaultPassword = applicationConstants.getDefaultPassword();
			logger.info("Registering account with username: " + username + " and default password: " + defaultPassword);
			
			Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
			
			UserDetails newRegistrantDetails = new User(username, defaultPassword, authorities);
			userDetailsManager.createUser(newRegistrantDetails);
			
			redirectLocation = "/pages/secure/accounts/signin";
		}
		
		return "redirect:" + redirectLocation;
	}
	
	//- Sign-in & Authentication Dispatch ------------------------------------------------------------------------------
	
	@RequestMapping(value = "/signin")
	public String getSigninForm() {
		return "accounts/signin";
	}
	
	@RequestMapping(value = "/authenticate/success")
	public String authenticatedSuccessfully() {
		String redirectLocation = "/pages/";
		return "redirect:" + redirectLocation;
	}
	
}
