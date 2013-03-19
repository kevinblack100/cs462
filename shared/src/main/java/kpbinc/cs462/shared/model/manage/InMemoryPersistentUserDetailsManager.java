package kpbinc.cs462.shared.model.manage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import kpbinc.io.util.JsonFileStore;
import kpbinc.util.PropertyAccessor;
import kpbinc.util.logging.GlobalLogUtils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

import com.fasterxml.jackson.core.type.TypeReference;

public class InMemoryPersistentUserDetailsManager
	extends JsonFileStorePersistentMapStorageManager<String, UserDetails>
	implements UserDetailsService, UserDetailsManager {
	
	//= Initialization =================================================================================================
	
	/**
	 * @param fileStoreRelativePath path to the file store in which to persist the item map relative to the
	 * ServletContext
	 * 
	 * @throws IllegalArgumentException if fileStoreRelativePath is null
	 */
	public InMemoryPersistentUserDetailsManager(String fileStoreRelativePath) {
		super(fileStoreRelativePath, new PropertyAccessor<UserDetails, String>() {

			@Override
			public String getPropertyName() {
				return "username";
			}

			@Override
			public String getPropertyValue(UserDetails object) {
				String username = object.getUsername();
				return username;
			}

			@Override
			public void setPropertyValue(UserDetails object, String value) {
				throw new UnsupportedOperationException(String.format("setPropertyValue '%s' not supported for objects of class '%s'",
						getPropertyName(),
						UserDetails.class.getName()));
			}
			
		});
		GlobalLogUtils.logConstruction(this);
	}
	
	@Override
	protected JsonFileStore<Map<String, UserDetails>> getJsonFileStore(File file) {
		JsonFileStore<Map<String, UserDetails>> jsonFileStore = 
				new JsonFileStore<Map<String, UserDetails>>(file, new TypeReference<Map<String, UserDetails>>() {});
		return jsonFileStore;
	}
	
	
	//= Interface ======================================================================================================
	
	public Set<String> getAllUsernames() {
		Set<String> result = Collections.unmodifiableSet(getItemMap().keySet());
		return result;
	}
	
	@Override
	public void createUser(UserDetails user) {
		assert(user != null);
		if (user != null) {
			register(user);
		}
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (!managesItemWithKey(username)) {
			throw new UsernameNotFoundException(String.format("No user details stored for username '%s'", username));
		}
		// else
		
		UserDetails storedDetails = retrieve(username);
		UserDetails copiedDetails = copyUserDetails(storedDetails);
		
		return copiedDetails;
	}
	
	@Override
	public boolean userExists(String username) {
		boolean result = managesItemWithKey(username);
		return result;
	}

	@Override
	public void updateUser(UserDetails user) {
		assert(user != null);
		if (user != null) {
			if (manages(user)) {
				UserDetails userRecord = copyUserDetails(user);
				update(userRecord);
			}
			// else not managed so cannot be updated
		}
	}
	
	@Override
	public void changePassword(String oldPassword, String newPassword) {
		// TODO what is the "current user"?
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteUser(String username) {
		unregisterItemWithKey(username);
	}


	//= Support ========================================================================================================
	
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
