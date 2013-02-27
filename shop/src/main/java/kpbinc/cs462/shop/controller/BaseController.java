package kpbinc.cs462.shop.controller;

import kpbinc.util.logging.GlobalLogUtils;
import kpbinc.cs462.shared.controller.SharedBaseController;
import kpbinc.cs462.shop.controller.policy.OrderPolicy;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Defines common members used by all controllers (or the views rendered by the controllers). Though this class is not
 * annotated as a @Controller the @Autowired members will be correctly initialized if the sub-classes that extend
 * BaseController are annotated as @Controllers.
 * 
 * @author Kevin Black
 */
public abstract class BaseController extends SharedBaseController {

	//~ Member Data ====================================================================================================
	
	@Autowired
	private OrderPolicy orderPolicy;
	
	
	//~ Initialization =================================================================================================
	
	protected BaseController() {
		super();
		GlobalLogUtils.logConstruction(this);
	}
	
}
