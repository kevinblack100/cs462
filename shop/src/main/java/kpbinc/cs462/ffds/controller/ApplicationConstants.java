package kpbinc.cs462.ffds.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session")
public class ApplicationConstants {

	public static final String DEFAULT_PASSWORD = "password";
	
	public String getDefaultPassword() {
		return DEFAULT_PASSWORD;
	}
	
}
