package kpbinc.cs462.shop.controller;

import java.io.Serializable;

import kpbinc.cs462.shared.controller.context.CommonApplicationConstants;
import kpbinc.util.logging.GlobalLogUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Scope(value = "request")
@RequestMapping(value = "/secure/accounts")
public class LoginController extends ShopBaseSiteContextController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private CommonApplicationConstants applicationConstants;
	
	public LoginController() {
		GlobalLogUtils.logConstruction(this);
	}
	
}
