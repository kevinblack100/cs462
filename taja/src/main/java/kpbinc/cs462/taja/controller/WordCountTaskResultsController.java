package kpbinc.cs462.taja.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import kpbinc.cs462.taja.model.WordCountTaskResults;
import kpbinc.cs462.taja.model.manage.WordCountTaskResultsManager;
import kpbinc.cs462.taja.model.util.WordCountResultsZingChartRenderer;
import kpbinc.net.UTF8URLEncoder;
import kpbinc.util.logging.GlobalLogUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Scope(value = "request")
@RequestMapping(value = "/tasks")
public class WordCountTaskResultsController extends TAJABaseSiteContextController {
	
	//= Member Data ====================================================================================================
	
	@Autowired
	private WordCountTaskResultsManager wordCountTaskResultsManager;
	
	
	//= Initialization =================================================================================================
	
	public WordCountTaskResultsController() {
		super();
		GlobalLogUtils.logConstruction(this);
	}

	
	//= Interface ======================================================================================================
	
	//- Web API --------------------------------------------------------------------------------------------------------
	
	@RequestMapping
	public String getTaskList(ModelMap model) {		
		Collection<WordCountTaskResults> tasks = wordCountTaskResultsManager.retrieveAll();
		model.addAttribute("tasks", tasks);
		
		return "tasks/tasks_list";
	}
	
	@RequestMapping(value = "/{task-results-id}")
	public String getTaskProfile(
			@PathVariable(value = "task-results-id") Long taskResultsId,
			ModelMap model) {
		WordCountTaskResults task = wordCountTaskResultsManager.retrieve(taskResultsId);
		model.addAttribute("task", task);
		
		WordCountResultsZingChartRenderer renderer = new WordCountResultsZingChartRenderer();
		String renderingQueryString = renderer.getQueryString(task);
		model.addAttribute("renderingQueryString", renderingQueryString);
		
		return "tasks/task_profile";
	}
	
}
