package kpbinc.cs462.driver.controller;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import kpbinc.cs462.driver.model.DriverGuildEventChannel;
import kpbinc.cs462.driver.model.UserProfile;
import kpbinc.cs462.driver.model.manage.DriverGuildEventChannelManager;
import kpbinc.cs462.driver.model.manage.UserProfileManager;
import kpbinc.cs462.shared.controller.context.CommonApplicationConstants;
import kpbinc.cs462.shared.controller.context.LoggedInUserContext;
import kpbinc.cs462.shared.event.ESLGenerator;
import kpbinc.cs462.shared.model.manage.InMemoryPersistentUserDetailsManager;
import kpbinc.net.URLPathBuilder;
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
	private ESLGenerator eslGenerator;
	
	@Autowired
	private InMemoryPersistentUserDetailsManager userDetailsManager;
	
	@Autowired
	private UserProfileManager userProfileManager;
	
	@Autowired
	private DriverGuildEventChannelManager driverGuildEventChannelManager;
	
	
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
			HttpServletRequest request,
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "textable-number", required = true) String textableNumber) {
		String redirectLocation = null;
		
		try {
			// Does account already exist?
			userDetailsManager.loadUserByUsername(username);
			// if it did not exist then an exception was thrown
			
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
			
			// TODO validate the number
			UserProfile profile = new UserProfile(username);
			profile.setTextableNumber("+" + textableNumber);
			userProfileManager.register(profile);
			
			// Create a channel for the user
			DriverGuildEventChannel channel = new DriverGuildEventChannel();
			channel.setLocalEntityId(username);
			driverGuildEventChannelManager.register(channel);
						
			// Generate and store the receive ESL
			try {
				String receiveESLPath = URLPathBuilder.build("esl", "guild", "channel", channel.getId().toString());
				URL receiveESL = eslGenerator.generate(request, receiveESLPath);
				channel.setReceiveESL(receiveESL.toString());
				driverGuildEventChannelManager.update(channel);
			}
			catch (MalformedURLException e) {
				logger.warning(GlobalLogUtils.formatHandledExceptionMessage(
						"Receive ESL for Guild to Driver", e, GlobalLogUtils.DO_PRINT_STACKTRACE));
				e.printStackTrace();
			}
			
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
