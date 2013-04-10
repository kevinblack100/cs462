package kpbinc.cs462.taja.model;

import kpbinc.util.logging.GlobalLogUtils;

public class WordCountJobResults extends WordCountResults {

	//= Member Data ====================================================================================================
	
	private String jobId;
	
	
	//= Initialization =================================================================================================
	
	public WordCountJobResults() {
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

	@Override
	public String getTextIdentifier() {
		String result = String.format("Job %s", jobId);
		return result;
	}
	
}

