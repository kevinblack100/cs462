package kpbinc.cs462.ffds.controller;

import kpbinc.common.util.logging.GlobalLogUtils;
import kpbinc.cs462.ffds.controller.policy.OrderPolicy;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseController {

	//~ Member Data ====================================================================================================
	
	@Autowired
	private OrderPolicy orderPolicy;
	
	
	//~ Initialization =================================================================================================
	
	protected BaseController() {
		GlobalLogUtils.logConstruction(this);
	}
	
}
