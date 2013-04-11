package kpbinc.cs462.taja.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.lang3.Validate;

import kpbinc.cs462.taja.model.WordCountResults;
import kpbinc.net.UTF8URLEncoder;
import kpbinc.util.logging.GlobalLogUtils;

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
public class WordCountResultsZingChartRenderer {

	//= Class Data =====================================================================================================
	
	private static final Logger logger = Logger.getLogger(WordCountResultsZingChartRenderer.class.getName());
	
	private static final int DEFAULT_WIDTH = 1000;
	private static final int DEFAULT_HEIGHT = 500;
	
	
	//= Initialization =================================================================================================
	
	public WordCountResultsZingChartRenderer() {
		GlobalLogUtils.logConstruction(this);
	}

	
	//= Interface ======================================================================================================
	
	public String getQueryString(WordCountResults results) {
		Validate.notNull(results, "results must not be null");
		Validate.notNull(results.getWordCounts(), "results.wordCounts must not be null");
		
		String queryString = getQueryString(results, new ArrayList<String>(results.getWordCounts().keySet()));
		return queryString;
	}
	
	public String getQueryString(WordCountResults results, List<String> words) {
		Validate.notNull(results, "results must not be null");
		Validate.notNull(results.getWordCounts(), "results.wordCounts must not be null");
		
		String queryString = getQueryString(Arrays.asList(results), words);
		return queryString;	
	}
	
	public String getQueryString(Collection<? extends WordCountResults> resultsCollection, List<String> words) {
		Validate.notNull(resultsCollection, "results must not be null");
		Validate.notNull(words, "words must not be null");
		Validate.isTrue(!words.isEmpty(), "words must not be empty");
		
		StringBuilder builder = new StringBuilder();
		builder.append("{\"graphset\":[{\"type\":\"hbar\",\"scale-x\":{\"values\":[");
		boolean firstWord = true;
		for (String word : words) {
			if (!firstWord) {
				builder.append(",");
			}
			builder.append(String.format("\"%s\"", word));
			firstWord = false;
		}
		builder.append("]},\"series\":[");
		boolean firstSeries = true;
		for (WordCountResults results : resultsCollection) {
			if (!firstSeries) {
				builder.append(",");
			}
			builder.append(getSeriesValues(results, words));
			firstSeries = false;
		}
		builder.append("]}]}");
		
		String dataValue = builder.toString();
		logger.info(String.format("Prepared ZingChart data: %s", dataValue));
		
		String dataUrlEncoding = UTF8URLEncoder.encode(dataValue);
		
		String queryString = String.format("d=%s&w=%d&h=%d", dataUrlEncoding, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		
		return queryString;
	}
	
	private String getSeriesValues(WordCountResults results, List<String> words) {
		StringBuilder builder = new StringBuilder();
		builder.append("{\"values\":[");
		boolean firstCount = true;
		for (String word : words) {
			Long count = results.getWordCounts().get(word);
			count = (count != null ? count : 0L);
			if (!firstCount) {
				builder.append(",");
			}
			builder.append(count);
			firstCount = false;
		}
		builder.append("]}");
		
		return builder.toString();
	}
	
}
