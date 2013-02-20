package kpbinc.cs462.ffds.controller;

import java.util.Date;

import kpbinc.common.util.logging.GlobalLogUtils;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Scope(value = "request")
@RequestMapping(value = "/orders")
public class OrderController extends BaseController {
	
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
	
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public String submitOrder(
			@RequestParam(value = "pickup-time") String pickupTime) {
		return "redirect:/ffds/";
	}
	
}
