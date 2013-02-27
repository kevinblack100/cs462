package kpbinc.cs462.driver.controller;

import kpbinc.util.logging.GlobalLogUtils;
import kpbinc.cs462.shared.controller.SharedBaseSiteContextController;

/**
 * Defines common members used by all controllers (or the views rendered by the controllers). Though this class is not
 * annotated as a @Controller the @Autowired members will be correctly initialized if the sub-classes that extend
 * BaseController are annotated as @Controllers.
 * 
 * @author Kevin Black
 */
public abstract class DriverBaseSiteContextController extends SharedBaseSiteContextController {

	//~ Member Data ====================================================================================================
	
	
	//~ Initialization =================================================================================================
	
	protected DriverBaseSiteContextController() {
		super();
		GlobalLogUtils.logConstruction(this);
	}
	
}
