package kpbinc.cs462.guild.model.manage;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import kpbinc.cs462.guild.model.UserProfile;
import kpbinc.util.logging.GlobalLogUtils;

public class DriverRankingEngine {

	//= Class Data =====================================================================================================
	
	private static final int minimumRanking = 0;
	private static final int maximumRanking = 10;
	
	
	//= Member Data ====================================================================================================
	
	@Autowired
	private UserProfileManager userProfileManager; 
	
	private Random rng;
	
	
	//= Initialization =================================================================================================
	
	public DriverRankingEngine() {
		GlobalLogUtils.logConstruction(this);
		rng = new Random(System.currentTimeMillis());
	}

	
	//= Interface ======================================================================================================
	
	public void assignRanking(String username) {
		UserProfile profile = userProfileManager.retrieve(username);
		if (profile == null) {
			profile = new UserProfile();
			profile.setUsername(username);
			userProfileManager.register(profile);
		}
		Long medianRanking = new Long((maximumRanking - minimumRanking) / 2);
		profile.setDriverRanking(medianRanking);
		userProfileManager.update(profile);
	}
	
	public void updateRanking(String username) {
		UserProfile profile = userProfileManager.retrieve(username);
		if (profile == null) {
			assignRanking(username);
		}
		else {
			Long newRanking = new Long(rng.nextInt(maximumRanking));
			profile.setDriverRanking(newRanking);
			userProfileManager.update(profile);
		}
	}
	
}
