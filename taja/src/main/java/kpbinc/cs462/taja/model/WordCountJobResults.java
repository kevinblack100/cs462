package kpbinc.cs462.taja.model;

import kpbinc.util.logging.GlobalLogUtils;

public class WordCountJobResults extends WordCountResults {

	//= Member Data ====================================================================================================
	
	private Long jobId;
	
	
	//= Initialization =================================================================================================
	
	public WordCountJobResults() {
		GlobalLogUtils.logConstruction(this);
	}


	//= Interface ======================================================================================================

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	@Override
	public String getTextIdentifier() {
		String result = String.format("Job %d", jobId);
		return result;
	}
	
}

