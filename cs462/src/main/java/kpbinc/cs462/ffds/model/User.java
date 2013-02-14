package kpbinc.cs462.ffds.model;

import kpbinc.common.util.logging.GlobalLogUtils;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("prototype")
public class User {

	private String username;
	
	public User() {
		GlobalLogUtils.logConstruction(this);
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
}
