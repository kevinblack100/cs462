package kpbinc.cs462.guild.model;

import kpbinc.cs462.shared.event.EventChannel;
import kpbinc.util.logging.GlobalLogUtils;

public class GuildUserEventChannel extends EventChannel<Void, String> {

	//= Initialization =================================================================================================
	
	public GuildUserEventChannel() {
		super();
		GlobalLogUtils.logConstruction(this);
	}

}
