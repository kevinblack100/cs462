package kpbinc.cs462.taja.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kpbinc.cs462.shared.model.aspect.HasId;
import kpbinc.util.logging.GlobalLogUtils;

public abstract class WordCountResults implements HasId<Long> {

	//= Member Data ====================================================================================================
	
	private Long id;
	private Map<String, Long> wordCounts;
	
	
	//= Initialization =================================================================================================
	
	protected WordCountResults() {
		GlobalLogUtils.logConstruction(this);
	}
	
	
	//= Interface ======================================================================================================
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Map<String, Long> getWordCounts() {
		return wordCounts;
	}

	public void setWordCounts(Map<String, Long> wordCounts) {
		this.wordCounts = wordCounts;
	}

	@JsonIgnore
	public String getTextIdentifier() {
		String result = String.format("%s %d", WordCountResults.class.getSimpleName(), id);
		return result;
	}
	
}
