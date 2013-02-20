package kpbinc.cs462.ffds.controller;

import kpbinc.common.util.logging.GlobalLogUtils;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Scope(value = "request")
@RequestMapping(value = "/orders")
public class OrderController {

	//~ Class Data =====================================================================================================
	
	
	//~ Member Data ====================================================================================================
	
	
	
	//~ Initialization =================================================================================================
	
	public OrderController() {
		GlobalLogUtils.logConstruction(this);
	}
	
	
	//~ Interface ======================================================================================================
	
	@RequestMapping(value = "/submit", method = RequestMethod.GET)
	public String getOrderSubmissionForm() {
		return "orders/submit_order";
	}
	
}
