package kpbinc.cs462.shared.controller;

import kpbinc.util.logging.GlobalLogUtils;
import kpbinc.cs462.shared.controller.context.LoggedInUserContext;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Defines common members used by all controllers (or the views rendered by the controllers). Though this class is not
 * annotated as a @Controller the @Autowired members will be correctly initialized if the sub-classes that extend
 * BaseController are annotated as @Controllers.
 * 
 * @author Kevin Black
 */
public abstract class SharedBaseController {

	//= Member Data ====================================================================================================
	
	// setter @Autowired
	private LoggedInUserContext loggedInUserContext;
	
	
	//= Initialization =================================================================================================
	
	protected SharedBaseController() {
		GlobalLogUtils.logConstruction(this);
	}
	
	
	//= Interface ======================================================================================================
	
	public LoggedInUserContext getLoggedInUserContext() {
		return loggedInUserContext;
	}
	
	@Autowired
	public void setLoggedInUserContext(LoggedInUserContext loggedInUserContext) {
		this.loggedInUserContext = loggedInUserContext;
	}
	
}
