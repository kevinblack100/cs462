package kpbinc.cs462.shared.event;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import kpbinc.cs462.shared.controller.context.ContextPaths;
import kpbinc.util.logging.GlobalLogUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component(value = "eslGenerator")
@Scope(value = "request")
public class ESLGenerator {

	//= Member Data ====================================================================================================
	
	@Autowired
	private ContextPaths contextPaths;
	
	
	//= Initialization =================================================================================================
	
	public ESLGenerator() {
		GlobalLogUtils.logConstruction(this);
	}

	
	//= Interface ======================================================================================================
	
	/**
	 * Generates an event signal URL with the given filePath relative to the given request's protocol, host, and port
	 * and the dynamic path of the autowired ContextPaths. 
	 * 
	 * @param request
	 * @param filePath
	 * @return event signal URL
	 */
	public URL generate(HttpServletRequest request, String filePath) throws MalformedURLException {
		URL requestURL = new URL(request.getRequestURL().toString());
		String eslProtocol = requestURL.getProtocol();
		String eslHost = requestURL.getHost();
		int eslPort = requestURL.getPort();
		String eslFullPath = new StringBuilder(contextPaths.getDynamicPath())
				.append((StringUtils.isNotBlank(filePath) ? filePath : ""))
				.toString();
		URL esl = new URL(eslProtocol, eslHost, eslPort, eslFullPath);
		return esl;
	}
	
}
