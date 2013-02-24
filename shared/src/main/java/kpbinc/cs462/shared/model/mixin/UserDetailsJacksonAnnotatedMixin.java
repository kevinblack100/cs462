package kpbinc.cs462.shared.model.mixin;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = User.class)
public interface UserDetailsJacksonAnnotatedMixin extends UserDetails {

}
