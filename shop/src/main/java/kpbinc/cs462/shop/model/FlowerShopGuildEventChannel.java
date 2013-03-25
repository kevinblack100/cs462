package kpbinc.cs462.shop.model;

import kpbinc.cs462.shared.event.EventChannel;
import kpbinc.util.logging.GlobalLogUtils;

public class FlowerShopGuildEventChannel extends EventChannel<Void, Void> {

	//= Initialization =================================================================================================
	
	public FlowerShopGuildEventChannel() {
		super();
		GlobalLogUtils.logConstruction(this);
	}

}
