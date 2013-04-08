package kpbinc.cs462.taja.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import kpbinc.cs462.taja.model.WordCountJobResults;
import kpbinc.cs462.taja.model.manage.WordCountJobResultsManager;
import kpbinc.util.logging.GlobalLogUtils;

@Controller
@Scope(value = "request")
@RequestMapping(value = "/jobs")
public class WordCountJobResultsController extends TAJABaseSiteContextController {

	//= Member Data ====================================================================================================
	
	@Autowired
	private WordCountJobResultsManager wordCountJobResultsManager;
	
	
	//= Initialization =================================================================================================
	
	public WordCountJobResultsController() {
		super();
		GlobalLogUtils.logConstruction(this);
	}

	
	//= Interface ======================================================================================================
	
	//- Web API --------------------------------------------------------------------------------------------------------
	
	@RequestMapping
	public String getJobList(ModelMap model) {
		Collection<WordCountJobResults> jobs = wordCountJobResultsManager.retrieveAll();
		model.addAttribute("jobs", jobs);
		
		return "jobs/jobs_list";
	}
	
	@RequestMapping(value = "/{job-id}")
	public String getJobProfile(
			@PathVariable(value = "job-id") Long jobId,
			ModelMap model) {
		WordCountJobResults job = wordCountJobResultsManager.retrieveByJobId(jobId);
		model.addAttribute("job", job);
		
		return "jobs/job_profile";
	}
	
}
