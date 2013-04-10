package kpbinc.cs462.taja.model;

import kpbinc.util.logging.GlobalLogUtils;

public class WordCountTaskResults extends WordCountResults {

	//= Member Data ====================================================================================================
	
	private String jobId;
	private String taskId;
	
	
	//= Initialization =================================================================================================
	
	public WordCountTaskResults() {
		super();
		GlobalLogUtils.logConstruction(this);
	}


	//= Interface ======================================================================================================

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	@Override
	public String getTextIdentifier() {
		String result = String.format("Job %s, Task %s", jobId, taskId);
		return result;
	}
	
}
