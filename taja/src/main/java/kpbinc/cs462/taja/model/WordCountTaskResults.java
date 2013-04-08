package kpbinc.cs462.taja.model;

import kpbinc.util.logging.GlobalLogUtils;

public class WordCountTaskResults extends WordCountResults {

	//= Member Data ====================================================================================================
	
	private Long jobId;
	private Long taskId;
	
	
	//= Initialization =================================================================================================
	
	public WordCountTaskResults() {
		super();
		GlobalLogUtils.logConstruction(this);
	}


	//= Interface ======================================================================================================

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

	@Override
	public String getTextIdentifier() {
		String result = String.format("Job %d, Task %d", jobId, taskId);
		return result;
	}
	
}
