package kpbinc.cs462.taja.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import kpbinc.cs462.taja.model.WordCountTaskResults;
import kpbinc.cs462.taja.model.manage.WordCountTaskResultsManager;
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
		
		Map<Long, String> renderingQueryStrings = getZingChartRenderingQueryStrings(tasks);
		model.addAttribute("renderingQueryStrings", renderingQueryStrings);
		
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
		
		Map<Long, String> renderingQueryStrings = getZingChartRenderingQueryStrings(tasks);
		model.addAttribute("renderingQueryStrings", renderingQueryStrings);
		
		return "tasks/tasks_list";
	}
	
	
	//= Support ========================================================================================================
	
	private Map<Long, String> getZingChartRenderingQueryStrings(Collection<WordCountTaskResults> tasks) {
		Map<Long, String> renderingQueryStrings = new HashMap<Long, String>();
		for (WordCountTaskResults task : tasks) {
			String renderingQueryString = getZingChartRenderingQueryString(task);
			renderingQueryStrings.put(task.getId(), renderingQueryString);
		}
		return renderingQueryStrings;
	}
	
	private String getZingChartRenderingQueryString(WordCountTaskResults task) {
		StringBuilder builder = new StringBuilder();
		builder.append("{\"graphset\":[{\"type\":\"bar\",\"scale-x\":{\"values\":[");
		boolean firstWord = true;
		for (String word : task.getWordCounts().keySet()) {
			if (!firstWord) {
				builder.append(",");
			}
			builder.append(String.format("\"%s\"", word));
			firstWord = false;
		}
		builder.append("]},\"series\":[{\"values\":[");
		boolean firstCount = true;
		for (Long count : task.getWordCounts().values()) {
			if (!firstCount) {
				builder.append(",");
			}
			builder.append(count);
			firstCount = false;
		}
		builder.append("]}]}]}");
		
		String dataUrlEncoding = UTF8URLEncoder.encode(builder.toString());
		
		String queryString = String.format("d=%s", dataUrlEncoding);
		
		return queryString;
		
		/* Example bar chart code from ZingChart
		{
			"graphset": [
				{
					"type":"bar",
					"background-color":"#ffffff",
					"scale-x": {
						"line-color":"#ffffff",
						"values": ["Jan","Feb","Mar","Apr","May","Jun"],
						"guide": {
							"visible":false
						},
						"tick": {
							"visible":false
						},
						"item": {
							"font-family":"helvetica",
							"font-color":"#05636c",
							"font-weight":"bold",
							"font-size":"12px",
							"visible":true
						}
					},
					"scale-y": {
						"values":"0:50:10",
						"line-width":"0px",
						"format":"%v",
						"tick": {
							"visible":false
						},
						"item": {
							"font-family":"helvetica",
							"font-color":"#05636c",
							"font-weight":"bold",
							"font-size":"12px",
							"visible":true
						},
						"guide": {
							"visible":false
						}
					},
					"tooltip": {
						"visible":true
					},
					"plot": {
						"tooltip-text":" %v  %t <br> Key: %k"
					},
					"series": [
						{
							"values":["28","35","19","7","30","46","38"],
							"text":"Shoes",
							"background-color":"#05636c"
						},
						{
							"values":["40","35","26","23","27","22","30"],
							"text":"Pants",
							"background-color":"#69a1a7"
						}
					]
				}
			]
		}
		*/
	}
	
}
