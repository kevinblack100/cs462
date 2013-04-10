package kpbinc.cs462.taja.controller;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kpbinc.cs462.shared.event.BasicEventImpl;
import kpbinc.cs462.shared.event.ESLGenerator;
import kpbinc.cs462.shared.event.Event;
import kpbinc.cs462.shared.event.EventDispatcher;
import kpbinc.cs462.shared.event.EventGenerator;
import kpbinc.cs462.shared.event.EventHandlerWithContext;
import kpbinc.cs462.shared.event.EventRenderingException;
import kpbinc.cs462.shared.event.EventTransformer;
import kpbinc.cs462.shared.event.SingleEventTypeEventHandlerWithContext;
import kpbinc.cs462.shared.model.manage.LoggedEventManager;
import kpbinc.cs462.taja.model.WordCountJobResults;
import kpbinc.cs462.taja.model.WordCountTaskResults;
import kpbinc.cs462.taja.model.manage.WordCountJobResultsManager;
import kpbinc.cs462.taja.model.manage.WordCountTaskResultsManager;
import kpbinc.net.URLPathBuilder;
import kpbinc.util.logging.GlobalLogUtils;

@Controller
@Scope(value = "request")
@RequestMapping(value = "/esl")
public class EventDispatchController extends TAJABaseSiteContextController {

	//= Class Data =====================================================================================================
	
	private static final Logger logger = Logger.getLogger(EventDispatchController.class.getName());
	
	private static final String JobEventsJobIDAttributeName = "request_id";
	
	
	//= Member Data ====================================================================================================
	
	@Autowired
	private EventTransformer eventTransformer;
	
	@Autowired
	private EventGenerator eventGenerator;

	@Autowired
	private ESLGenerator eslGenerator;
	
	@Autowired
	private LoggedEventManager loggedEventManager;
	
	@Autowired
	private WordCountJobResultsManager wordCountJobResultsManager;
	
	@Autowired
	private WordCountTaskResultsManager wordCountTaskResultsManager;
	
	private Collection<EventHandlerWithContext<HttpServletRequest>> publicEventHandlers;
	
	
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
		EventDispatcher.dispatchEventWithContext(
				"dispatch public channel event",
				request,
				response,
				eventTransformer,
				getPublicEventHandlers(),
				request,
				loggedEventManager,
				request.getRequestURL().toString());
	}
	
	
	//= Support ========================================================================================================
	
	private Collection<EventHandlerWithContext<HttpServletRequest>> getPublicEventHandlers() {
		if (publicEventHandlers == null) {
			publicEventHandlers = new ArrayList<EventHandlerWithContext<HttpServletRequest>>();
			
			// job:task_results
			publicEventHandlers.add(new SingleEventTypeEventHandlerWithContext<HttpServletRequest>("job", "task_results") {

				@Override
				protected void handleImpl(Event event, HttpServletRequest context) {
					// TODO check the task_type attribute is "wordcount"
					// Parse and store the task
					WordCountTaskResults taskResults = new WordCountTaskResults();
					taskResults.setJobId((String) event.getAttribute(JobEventsJobIDAttributeName));
					taskResults.setTaskId((String) event.getAttribute("task_id"));
					
					Map<String, Long> wordCounts = new TreeMap<String, Long>();
					for (Map.Entry<String, List<Object>> attribute : event.getAttributes().entrySet()) {
						String word = attribute.getKey();
						if (   !StringUtils.equals(word, JobEventsJobIDAttributeName)
							&& !StringUtils.equals(word, "task_id")
							&& !StringUtils.equals(word, "analysis_available_esl")) {
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
					
					// Prepare job:analysis_available
					String analysisAvailableESL = (String) event.getAttribute("analysis_available_esl");
					if (analysisAvailableESL != null) {
						Event analysisAvailableEvent = null;
						
						try {
							String jobId = jobResults.getJobId();
							analysisAvailableEvent = new BasicEventImpl("job", "analysis_available");
							analysisAvailableEvent.addAttribute(JobEventsJobIDAttributeName, jobId);
							String filePath = URLPathBuilder.build("jobs", jobId);
							String analysisURL = eslGenerator.generate(context, filePath).toString();
							analysisAvailableEvent.addAttribute("analysis_url", analysisURL);
						}
						catch (EventRenderingException e) {
							logger.warning(GlobalLogUtils.formatHandledExceptionMessage(
									String.format("preparing %s:%s", getDomain(), getName()),
									e, GlobalLogUtils.DO_PRINT_STACKTRACE));
							e.printStackTrace();
						}
						catch (MalformedURLException e1) {
							logger.warning(GlobalLogUtils.formatHandledExceptionMessage(
									"preparing analysis URL", e1, GlobalLogUtils.DO_PRINT_STACKTRACE));
							e1.printStackTrace();
						}
						
						if (analysisAvailableEvent != null) {
							eventGenerator.sendAndLogEvent(analysisAvailableESL, analysisAvailableEvent, loggedEventManager);
						}
					}
				}
				
			});
		}
		return publicEventHandlers;
	}
}
