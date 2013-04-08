package kpbinc.cs462.taja.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kpbinc.cs462.shared.event.Event;
import kpbinc.cs462.shared.event.EventDispatcher;
import kpbinc.cs462.shared.event.EventHandler;
import kpbinc.cs462.shared.event.EventTransformer;
import kpbinc.cs462.shared.event.SingleEventTypeEventHandler;
import kpbinc.cs462.taja.model.WordCountJobResults;
import kpbinc.cs462.taja.model.WordCountTaskResults;
import kpbinc.cs462.taja.model.manage.WordCountJobResultsManager;
import kpbinc.cs462.taja.model.manage.WordCountTaskResultsManager;
import kpbinc.util.logging.GlobalLogUtils;

@Controller
@Scope(value = "request")
@RequestMapping(value = "/esl")
public class EventDispatchController extends TAJABaseSiteContextController {

	//= Member Data ====================================================================================================
	
	@Autowired
	private EventTransformer eventTransformer;
	
	@Autowired
	private WordCountJobResultsManager wordCountJobResultsManager;
	
	@Autowired
	private WordCountTaskResultsManager wordCountTaskResultsManager;
	
	private Collection<EventHandler> publicEventHandlers;
	
	
	//= Initialization =================================================================================================
	
	public EventDispatchController() {
		super();
		GlobalLogUtils.logConstruction(this);
	}

	
	//= Interface ======================================================================================================
	
	@RequestMapping(value = "/public/channel") // allow any HTTP method
	public void dispatchEvent(
		HttpServletRequest request,
		HttpServletResponse response) {
		EventDispatcher.dispatchEvent(
				"dispatch public channel event",
				request,
				response,
				eventTransformer,
				getPublicEventHandlers());
	}
	
	
	//= Support ========================================================================================================
	
	private Collection<EventHandler> getPublicEventHandlers() {
		if (publicEventHandlers == null) {
			publicEventHandlers = new ArrayList<EventHandler>();
			
			// job:task_results
			publicEventHandlers.add(new SingleEventTypeEventHandler("job", "task_results") {

				@Override
				protected void handleImpl(Event event) {
					// TODO check the task_type attribute is "wordcount"
					// Parse and store the task
					WordCountTaskResults taskResults = new WordCountTaskResults();
					taskResults.setJobId(Long.parseLong((String) event.getAttribute("job_id")));
					taskResults.setTaskId(Long.parseLong((String) event.getAttribute("task_id")));
					
					Map<String, Long> wordCounts = new TreeMap<String, Long>();
					for (Map.Entry<String, List<Object>> attribute : event.getAttributes().entrySet()) {
						String word = attribute.getKey();
						if (   !StringUtils.equals(word, "job_id")
							&& !StringUtils.equals(word, "task_id")) {
							Long totalCount = 0L;
							for (Object countRaw : attribute.getValue()) {
								Long count = Long.parseLong((String) countRaw);
								totalCount += count;
							}
							wordCounts.put(word, totalCount);
						}
					}
					taskResults.setWordCounts(wordCounts);
					
					wordCountTaskResultsManager.register(taskResults);
					
					// Update job
					WordCountJobResults jobResults = wordCountJobResultsManager.retrieveByJobId(taskResults.getJobId());
					if (jobResults == null) {
						jobResults = new WordCountJobResults();
						jobResults.setJobId(taskResults.getJobId());
						jobResults.setWordCounts(new TreeMap<String, Long>());
						wordCountJobResultsManager.register(jobResults);
					}
					Map<String, Long> jobWordCounts = jobResults.getWordCounts();
					for (Map.Entry<String, Long> wordCountEntry : taskResults.getWordCounts().entrySet()) {
						String word = wordCountEntry.getKey();
						Long taskWordCount = wordCountEntry.getValue();
						if (!jobWordCounts.containsKey(word)) {
							jobWordCounts.put(word, taskWordCount);
						}
						else {
							Long updatedJobWordCount = jobWordCounts.get(word) + taskWordCount;
							jobWordCounts.put(word, updatedJobWordCount);
						}
					}
					wordCountJobResultsManager.update(jobResults);
				}
				
			});
		}
		return publicEventHandlers;
	}
}
