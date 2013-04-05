package kpbinc.cs462.taja.model;

import java.util.Map;

import kpbinc.cs462.shared.model.aspect.HasId;
import kpbinc.util.logging.GlobalLogUtils;

public class WordCountTaskResults implements HasId<Long> {

	//= Member Data ====================================================================================================
	
	private Long id;
	private Long jobId;
	private Long taskId;
	private Map<String, Long> wordCounts;
	
	
	//= Initialization =================================================================================================
	
	public WordCountTaskResults() {
		GlobalLogUtils.logConstruction(this);
	}


	//= Interface ======================================================================================================

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Map<String, Long> getWordCounts() {
		return wordCounts;
	}

	public void setWordCounts(Map<String, Long> wordCounts) {
		this.wordCounts = wordCounts;
	}
		
}
