package kpbinc.cs462.driver.controller;

import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Scope(value = "request")
@RequestMapping(value = "/event")
public class EventDispatchController {

	private static final Logger logger = Logger.getLogger(EventDispatchController.class.getName());
	
	public EventDispatchController() {
		// TODO Auto-generated constructor stub
	}

	@RequestMapping(value = "/dispatch", method = RequestMethod.GET)
	public String getDispatchForm() {
		return "events/dispatch";
	}
	
	@RequestMapping(value = "/dispatch", method = RequestMethod.POST)
	public String dispatch(@RequestParam Map<String, String> params) {
		for (Map.Entry<String, String> entry : params.entrySet()) {
			logger.info(String.format("param: { key: %s, value: %s }", entry.getKey(), entry.getValue()));
		}
		return "events/dispatch";
	}
	
	@RequestMapping(value = "/dispatch2", method = RequestMethod.POST)
	public String dispatch(HttpServletRequest request) {
		Map<String, String[]> parameters = request.getParameterMap();
		for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
			StringBuilder builder = new StringBuilder("param: { key: ").
					append(entry.getKey()).append(", value: [");
			for (int i = 0, l = entry.getValue().length; i < l; i++) {
				String value = entry.getValue()[i];
				builder.append(value);
				if (i < l - 1) {
					builder.append(", ");
				}
			}
			builder.append("] }");
			logger.info(builder.toString());
		}
		return "events/dispatch";
	}
	
}
