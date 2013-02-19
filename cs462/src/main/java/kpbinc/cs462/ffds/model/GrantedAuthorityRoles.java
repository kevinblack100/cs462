package kpbinc.cs462.ffds.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class GrantedAuthorityRoles {

	private static final String ROLE_USER_NAME = "ROLE_USER";
	public static final GrantedAuthority ROLE_USER = new SimpleGrantedAuthority(ROLE_USER_NAME);
	
	private static final String ROLE_DRIVER_NAME = "ROLE_DRIVER";
	public static final GrantedAuthority ROLE_DRIVER = new SimpleGrantedAuthority(ROLE_DRIVER_NAME);
	
}
