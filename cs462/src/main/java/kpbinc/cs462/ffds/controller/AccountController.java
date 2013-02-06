package kpbinc.cs462.ffds.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Scope(value = "request")
@RequestMapping(value = "/secure/accounts")
public class AccountController {

	public static final String DEFAULT_PASSWORD = "password";
	
	@Autowired
	private InMemoryUserDetailsManager userDetailsManager;
	
	@Autowired
	private ServletContext servletContext;
	
	public AccountController() {
		int tmpbrkpnt = 1;
	}
	
	public String getDefaultPassword() {
		return DEFAULT_PASSWORD;
	}
	
	@RequestMapping(value = "/register/query")
	public String getRegistrationForm() {
		return "accounts/register";
	}
	
	@RequestMapping(value = "/register/execute")
	public void registerAccount(
			@RequestParam(value = "username", required = true) String username,
			HttpServletResponse response) throws IOException {
		String redirectLocation = null;
		
		try {
			// Does account already exist?
			UserDetails currentRegistrantDetails = userDetailsManager.loadUserByUsername(username);
			
			System.out.printf("Account with username \"%s\" already exists\n", username);
			
			redirectLocation = response.encodeRedirectURL("/cs462/ffds/secure/accounts/register/query");
		}
		catch (UsernameNotFoundException exception) {
			// Account does not exist
			System.out.println("Registering account with username: " + username + " and default password: " + getDefaultPassword());
			
			Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
			
			UserDetails newRegistrantDetails = new User(username, getDefaultPassword(), authorities);
			userDetailsManager.createUser(newRegistrantDetails);
			
			// write entry to the users.properties file as well
			try {
				String filePath = servletContext.getRealPath("/WEB-INF/ffds/users.properties");
				File userPropertiesFile = new File(filePath);
				boolean append = true;
				FileWriter writer = new FileWriter(userPropertiesFile, append);
				String accountEntry = String.format("\n%s=%s,%s,enabled", username, getDefaultPassword(), "ROLE_USER");
				writer.write(accountEntry);
				writer.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			
			redirectLocation = response.encodeRedirectURL("/cs462/ffds/secure/signin/query");
		}
		
		response.sendRedirect(redirectLocation);
	}
	
}
