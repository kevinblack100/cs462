package kpbinc.cs462.ffds.model.mixin;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@SuppressWarnings("serial")
public abstract class UserJsonAnnotatedMixin extends User {

	public UserJsonAnnotatedMixin(
			@JsonProperty("username") String username,
			@JsonProperty("password") String password,
			@JsonProperty("enabled") boolean enabled,
			@JsonProperty("accountNonExpired") boolean accountNonExpired,
			@JsonProperty("credentialsNonExpired") boolean credentialsNonExpired,
			@JsonProperty("accountNonLocked") boolean accountNonLocked,
			@JsonProperty("authorities")
			@JsonDeserialize(contentAs = SimpleGrantedAuthority.class)
				Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, authorities);
	}

}
