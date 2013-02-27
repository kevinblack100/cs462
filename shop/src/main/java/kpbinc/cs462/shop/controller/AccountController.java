package kpbinc.cs462.shop.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import kpbinc.cs462.shared.controller.context.CommonApplicationConstants;
import kpbinc.cs462.shared.model.InMemoryPersistentUserDetailsManager;
import kpbinc.cs462.shop.model.DriverProfile;
import kpbinc.cs462.shop.model.DriverProfileManager;
import kpbinc.cs462.shop.model.GrantedAuthorityRoles;
import kpbinc.util.logging.GlobalLogUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Scope(value = "request")
@RequestMapping(value = "/secure/accounts")
public class AccountController extends ShopBaseSiteContextController {
	
	//==================================================================================================================
	// Class Data
	//==================================================================================================================
	
	private static final Logger logger = Logger.getLogger(AccountController.class.getName());
	
	
	//==================================================================================================================
	// Member Data
	//==================================================================================================================
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private CommonApplicationConstants applicationConstants;
	
	@Autowired
	private InMemoryPersistentUserDetailsManager userDetailsManager;
	
	@Autowired
	private DriverProfileManager driverProfileManager;
	
	
	//==================================================================================================================
	// Initialization
	//==================================================================================================================
	
	public AccountController() {
		GlobalLogUtils.logConstruction(this);
	}
	
	
	//==================================================================================================================
	// Interface
	//==================================================================================================================
	
	//------------------------------------------------------------------------------------------------------------------
	// Account Registration
	//------------------------------------------------------------------------------------------------------------------
	
	@RequestMapping(value = "/register/query")
	public String getRegistrationForm() {
		return "accounts/register";
	}
	
	@RequestMapping(value = "/register/execute")
	public String registerAccount(
			@RequestParam(value = "username", required = true) String username,
			HttpServletResponse response) throws IOException {
		String redirectLocation = null;
		
		try {
			// Does account already exist?
			UserDetails currentRegistrantDetails = userDetailsManager.loadUserByUsername(username);
			
			logger.info(String.format("Account with username \"%s\" already exists\n", username));
			
			redirectLocation = "/ffds/secure/accounts/register/query";
		}
		catch (UsernameNotFoundException exception) {
			// Account does not exist
			String defaultPassword = applicationConstants.getDefaultPassword();
			logger.info("Registering account with username: " + username + " and default password: " + defaultPassword);
			
			Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(GrantedAuthorityRoles.ROLE_USER);
			
			UserDetails newRegistrantDetails = new User(username, defaultPassword, authorities);
			userDetailsManager.createUser(newRegistrantDetails);
			
			redirectLocation = "/ffds/secure/signin/query";
		}
		
		return "redirect:" + redirectLocation;
	}

	//------------------------------------------------------------------------------------------------------------------
	// Account Management
	//------------------------------------------------------------------------------------------------------------------
	
	@RequestMapping(value = "/manage", method = RequestMethod.GET)
	public String getManagementForm(ModelMap model) {
		UserDetails loggedInUserDetails = getLoggedInUserContext().getSignedInUserDetails();
		assert(loggedInUserDetails != null);
		
		boolean isDriver = loggedInUserDetails.getAuthorities().contains(GrantedAuthorityRoles.ROLE_DRIVER);
		model.addAttribute("isDriver", isDriver);
		
		String driverESL = "";
		DriverProfile profile = driverProfileManager.getProfileFor(loggedInUserDetails.getUsername());
		if (profile != null) {
			driverESL = profile.getEventSignalURL();
		}
		model.addAttribute("driverESL", driverESL);
		
		return "accounts/manage";
	}
	
	@RequestMapping(value = "/manage", method = RequestMethod.POST)
	public String saveChanges(
			@RequestParam(value = "driver-indicator", defaultValue = "false") boolean isDriver,
			@RequestParam(value = "driver-esl", defaultValue = "") String driverESL) {
		
		UserDetails loggedInUserDetails = getLoggedInUserContext().getSignedInUserDetails();
		assert(loggedInUserDetails != null);
		String username = loggedInUserDetails.getUsername();
		
		if (isDriver) {
			if (!loggedInUserDetails.getAuthorities().contains(GrantedAuthorityRoles.ROLE_DRIVER)) {
				Collection<GrantedAuthority> modifiedAuthorities = new ArrayList<GrantedAuthority>(loggedInUserDetails.getAuthorities());
				modifiedAuthorities.add(GrantedAuthorityRoles.ROLE_DRIVER);
				
				updateAuthorities(username, modifiedAuthorities);
			}
			
			DriverProfile profile = new DriverProfile(username);
			profile.setEventSignalURL(driverESL);
			driverProfileManager.createOrUpdate(profile);
		}
		else {
			if (loggedInUserDetails.getAuthorities().contains(GrantedAuthorityRoles.ROLE_DRIVER)) {
				Collection<GrantedAuthority> modifiedAuthorities = new ArrayList<GrantedAuthority>(loggedInUserDetails.getAuthorities());
				modifiedAuthorities.remove(GrantedAuthorityRoles.ROLE_DRIVER);
				
				updateAuthorities(username, modifiedAuthorities);
			}
			driverProfileManager.deleteProfileFor(username);
		}
		
		return "redirect:/ffds/users/" + username;
	}
	
	/**
	 * Sets the user's authorities to the given ones. If the given username matches the logged-in user's then the
	 * authentication token is updated with the new authorities but former credentials so that the logged-in user does
	 * not have to log in again.
	 *  
	 * @param username name of the user whose authorities should be updated
	 * @param modifiedAuthorities desired set of authorities for the user
	 */
	private void updateAuthorities(
			String username,
			Collection<GrantedAuthority> modifiedAuthorities) {
	
		UserDetails fullLoggedInUserDetails = userDetailsManager.loadUserByUsername(username);
		if (!fullLoggedInUserDetails.getAuthorities().equals(modifiedAuthorities)) {
			String password = fullLoggedInUserDetails.getPassword();
			UserDetails updatedDetails = new User(username, password, modifiedAuthorities);
			userDetailsManager.updateUser(updatedDetails);
		
			SecurityContext context = SecurityContextHolder.getContext();
			Authentication loggedInAuthentication = context.getAuthentication();
			if (   loggedInAuthentication != null
				&& username.equals(loggedInAuthentication.getName())) {
				// Note, use the old credentials so that the user doesn't have to login again
				// See Rob Winch's response on
				// http://forum.springsource.org/archive/index.php/t-109288.html?s=38e47057c160c0a03e04ba230627d021
				Authentication updatedAuthentication = new UsernamePasswordAuthenticationToken(
						updatedDetails, loggedInAuthentication.getCredentials(), modifiedAuthorities);
				context.setAuthentication(updatedAuthentication);
			}
		}
	}
}
