package kpbinc.cs462.shared.model.manage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import kpbinc.io.util.JsonFileStore;
import kpbinc.io.util.JsonFileStorePersistentMap;
import kpbinc.util.logging.GlobalLogUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

import com.fasterxml.jackson.core.type.TypeReference;

public class InMemoryPersistentUserDetailsManager implements UserDetailsService, UserDetailsManager {
	
	//==================================================================================================================
	// Member Data
	//==================================================================================================================
	
	@Autowired
	private ServletContext servletContext;
	
	private String fileStoreRelativePath;
	
	private Map<String, UserDetails> managedUserDetails;
	
	
	//==================================================================================================================
	// Initialization
	//==================================================================================================================
	
	/**
	 * 
	 * @param fileStoreRelativePath a non-null file path string
	 * 
	 * @throws IllegalArgumentException if fileStoreRelativePath is null
	 */
	public InMemoryPersistentUserDetailsManager(String fileStoreRelativePath) {
		GlobalLogUtils.logConstruction(this);
		
		if (fileStoreRelativePath == null) {
			throw new IllegalArgumentException("fileStoreRelativePath must not be null");
		}
		this.fileStoreRelativePath = fileStoreRelativePath;
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
