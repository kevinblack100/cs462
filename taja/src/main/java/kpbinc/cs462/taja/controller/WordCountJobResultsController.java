package kpbinc.cs462.taja.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import kpbinc.cs462.taja.model.WordCountJobResults;
import kpbinc.cs462.taja.model.WordCountTaskResults;
import kpbinc.cs462.taja.model.manage.WordCountJobResultsManager;
import kpbinc.cs462.taja.model.manage.WordCountTaskResultsManager;
import kpbinc.cs462.taja.model.util.WordCountResultsSampleCalculator;
import kpbinc.cs462.taja.model.util.WordCountResultsZingChartRenderer;
import kpbinc.util.logging.GlobalLogUtils;

@Controller
@Scope(value = "request")
@RequestMapping(value = "/jobs")
public class WordCountJobResultsController extends TAJABaseSiteContextController {

	//= Class Data =====================================================================================================
	
	private static final int DEFAULT_TOP_RANKS = 10;
	private static final Long DEFAULT_MIN_RANK = 4L;
	
	
	//= Member Data ====================================================================================================
	
	@Autowired
	private WordCountJobResultsManager wordCountJobResultsManager;
	
	@Autowired
	private WordCountTaskResultsManager wordCountTaskResultsManager;
	
	
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
			@PathVariable(value = "job-id") String jobId,
			ModelMap model) {
		WordCountJobResults job = wordCountJobResultsManager.retrieveByJobId(jobId);
		model.addAttribute("job", job);
		
		Collection<WordCountTaskResults> tasks = wordCountTaskResultsManager.retrieveByJobId(jobId);
		model.addAttribute("tasks", tasks);
		
		WordCountResultsZingChartRenderer renderer = new WordCountResultsZingChartRenderer();
		List<String> wordsOfTopNRank =
				new WordCountResultsSampleCalculator().getWordsOfTopNRank(job, DEFAULT_TOP_RANKS, DEFAULT_MIN_RANK);
		String renderingQueryString = renderer.getQueryString(job, wordsOfTopNRank);
		model.addAttribute("renderingQueryString", renderingQueryString);
		
		String componentRenderingQueryString = renderer.getQueryString(tasks, wordsOfTopNRank);
		model.addAttribute("componentRenderingQueryString", componentRenderingQueryString);
		
		return "jobs/job_profile";
	}
	
}
