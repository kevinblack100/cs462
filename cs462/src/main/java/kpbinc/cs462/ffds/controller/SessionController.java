package kpbinc.cs462.ffds.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletResponse;

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
	public void doSignin(HttpServletResponse response) throws IOException {
		String location = response.encodeRedirectURL("/cs462/ffds/");
		response.sendRedirect(location);
	}
	
}
