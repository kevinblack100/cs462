package kpbinc.cs462.guild.model;

import kpbinc.cs462.shared.event.EventChannel;
import kpbinc.util.logging.GlobalLogUtils;

public class GuildFlowerShopEventChannel extends EventChannel<Void, Long> {

	//= Initialization =================================================================================================
	
	public GuildFlowerShopEventChannel() {
		super();
		GlobalLogUtils.logConstruction(this);
	}

}
