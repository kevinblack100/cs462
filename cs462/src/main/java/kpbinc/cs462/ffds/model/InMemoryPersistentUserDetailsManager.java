package kpbinc.cs462.ffds.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import kpbinc.common.util.logging.GlobalLogUtils;
import kpbinc.io.util.JsonFileStore;
import kpbinc.io.util.JsonFileStorePersistentMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;

//@Repository(value = "inMemoryPersistentUserDetailsManager")
//@Scope(value = "singleton")
public class InMemoryPersistentUserDetailsManager implements UserDetailsService, UserDetailsManager {

	//==================================================================================================================
	// Class Data
	//==================================================================================================================
	
	private static final String fileStoreRelativePath = "/WEB-INF/ffds/user_details.json";
	
	
	//==================================================================================================================
	// Member Data
	//==================================================================================================================
	
	@Autowired
	private ServletContext servletContext;
	
	private Map<String, UserDetails> managedUserDetails;
	
	
	//==================================================================================================================
	// Initialization
	//==================================================================================================================
	
	public InMemoryPersistentUserDetailsManager() {
		GlobalLogUtils.logConstruction(this);
	}
	
	@PostConstruct
	private void initializeManagedUserDetails() {
		String fileStoreFullPath = servletContext.getRealPath(fileStoreRelativePath);
		File fileStoreFile = new File(fileStoreFullPath);
		JsonFileStore<Map<String, UserDetails>> jsonFileStore = 
				new JsonFileStore<Map<String, UserDetails>>(fileStoreFile, 
						new TypeReference<Map<String, UserDetails>>() {});
		managedUserDetails = JsonFileStorePersistentMap.<String, UserDetails>buildWithDelegateFromFileStore(jsonFileStore);
	}
	
	
	//==================================================================================================================
	// Interface
	//==================================================================================================================
	
	public Set<String> getAllUsernames() {
		Set<String> result = Collections.unmodifiableSet(managedUserDetails.keySet());
		return result;
	}
	
	@Override
	public void createUser(UserDetails user) {
		assert(user != null);
		if (user != null) {
			String username = user.getUsername();
			if (!managedUserDetails.containsKey(username)) {
				UserDetails userRecord = copyUserDetails(user);
				managedUserDetails.put(username, userRecord);
			}
			// else, already exists
			// TODO default to updating the user then?
		}
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		if (!managedUserDetails.containsKey(username)) {
			throw new UsernameNotFoundException(String.format("No user details stored for username \"%s\"", username));
		}
		// else
		
		UserDetails storedDetails = managedUserDetails.get(username);
		UserDetails copiedDetails = copyUserDetails(storedDetails);
		
		return copiedDetails;
	}
	
	@Override
	public boolean userExists(String username) {
		boolean result = managedUserDetails.containsKey(username);
		return result;
	}

	@Override
	public void updateUser(UserDetails user) {
		assert(user != null);
		if (user != null) {
			String username = user.getUsername();
			if (managedUserDetails.containsKey(username)) {
				UserDetails userRecord = copyUserDetails(user);
				managedUserDetails.put(username, userRecord);
			}
			// else doesn't exist so cannot be updated
		}
	}
	
	@Override
	public void changePassword(String oldPassword, String newPassword) {
		// what is the "current user"
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteUser(String username) {
		managedUserDetails.remove(username);
	}


	//==================================================================================================================
	// Support
	//==================================================================================================================
	
	private UserDetails copyUserDetails(UserDetails source) {
		Collection<GrantedAuthority> copiedAuthorities = new ArrayList<GrantedAuthority>(source.getAuthorities());
		
		UserDetails result = new User(
				source.getUsername(),
				source.getPassword(),
				source.isEnabled(),
				source.isAccountNonExpired(),
				source.isCredentialsNonExpired(),
				source.isAccountNonLocked(),
				copiedAuthorities);
		
		return result;
	}
	
}
