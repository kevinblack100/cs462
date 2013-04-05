package kpbinc.cs462.taja.controller;

import kpbinc.cs462.shared.controller.SharedBaseSiteContextController;
import kpbinc.util.logging.GlobalLogUtils;

public class TAJABaseSiteContextController extends SharedBaseSiteContextController {

	//= Initialization =================================================================================================
	
	public TAJABaseSiteContextController() {
		super();
		GlobalLogUtils.logConstruction(this);
	}

}
