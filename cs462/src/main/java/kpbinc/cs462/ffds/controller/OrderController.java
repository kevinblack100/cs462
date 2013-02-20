package kpbinc.cs462.ffds.controller;

import kpbinc.common.util.logging.GlobalLogUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Scope(value = "request")
@RequestMapping(value = "/orders")
public class OrderController extends BaseController {

	//~ Class Data =====================================================================================================
	
	
	//~ Member Data ====================================================================================================
	
	@Autowired
	private LoginController loginController;
	
	
	//~ Initialization =================================================================================================
	
	public OrderController() {
		super();
		GlobalLogUtils.logConstruction(this);
	}
	
	
	//~ Interface ======================================================================================================
	
	@RequestMapping(value = "/submit", method = RequestMethod.GET)
	public String getOrderSubmissionForm() {
		return "orders/submit_order";
	}
	
}
