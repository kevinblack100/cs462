package kpbinc.cs462.ffds.controller;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Scope(value = "session")
@RequestMapping(value = "/secure/signin")
public class SessionController implements Serializable {

	private static final long serialVersionUID = 1L;

	public SessionController() {
		int tmpbrkpnt = 1;
	}
	
	@RequestMapping(value = "/query")
	public String presentSignin() {
		return "signin";
	}

	@RequestMapping(value = "/success")
	public String doSignin() {
		return "welcome";
	}
	
}
