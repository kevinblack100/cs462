package kpbinc.cs462.driver.model;

import kpbinc.cs462.shared.event.EventChannel;
import kpbinc.util.logging.GlobalLogUtils;

public class DriverGuildEventChannel extends EventChannel<String, Void> {

	//= Initialization =================================================================================================
	
	public DriverGuildEventChannel() {
		super();
		GlobalLogUtils.logConstruction(this);
	}

}
