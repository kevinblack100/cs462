package kpbinc.cs462.guild.model;

import kpbinc.util.logging.GlobalLogUtils;

public class UserProfile {

	//= Member Data ====================================================================================================
	
	private String username;
	private Long driverRanking;
	
	
	//= Initialization =================================================================================================
	
	public UserProfile() {
		GlobalLogUtils.logConstruction(this);
	}


	//= Interface ======================================================================================================
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getDriverRanking() {
		return driverRanking;
	}

	public void setDriverRanking(Long driverRanking) {
		this.driverRanking = driverRanking;
	}
	
}
