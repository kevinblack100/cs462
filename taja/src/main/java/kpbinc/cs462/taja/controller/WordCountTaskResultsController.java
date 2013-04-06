package kpbinc.cs462.taja.controller;

import java.util.Collection;

import kpbinc.cs462.taja.model.WordCountTaskResults;
import kpbinc.cs462.taja.model.manage.WordCountTaskResultsManager;
import kpbinc.util.logging.GlobalLogUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Scope(value = "request")
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
	
	@RequestMapping(value = "/tasks")
	public String getTaskList(ModelMap model) {
		model.addAttribute("filteredByJob", false);
		
		Collection<WordCountTaskResults> tasks = wordCountTaskResultsManager.retrieveAll();
		model.addAttribute("tasks", tasks);
		
		return "tasks/tasks_list";
	}
	
	@RequestMapping(value = "/jobs/{job-id}/tasks")
	public String getJobTaskList(
			@PathVariable("job-id") Long jobId,
			ModelMap model) {
		model.addAttribute("filteredByJob", true);
		model.addAttribute("jobId", jobId);
		
		Collection<WordCountTaskResults> tasks = wordCountTaskResultsManager.retrieveByJobId(jobId);
		model.addAttribute("tasks", tasks);
		
		return "tasks/tasks_list";
	}
	
}
