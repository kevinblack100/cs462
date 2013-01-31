package kpbinc.cs462.ffds.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("prototype")
public class User {

	private String username;
	
	public User() {
		int tmpbrkpnt = 1;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
}
