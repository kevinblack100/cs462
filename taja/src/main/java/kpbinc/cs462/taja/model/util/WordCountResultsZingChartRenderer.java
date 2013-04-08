package kpbinc.cs462.taja.model.util;

import java.util.ArrayList;
import java.util.List;

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
		Validate.notNull(words, "words must not be null");
		Validate.isTrue(!words.isEmpty(), "words must not be empty");
		
		StringBuilder builder = new StringBuilder();
		builder.append("{\"graphset\":[{\"type\":\"bar\",\"scale-x\":{\"values\":[");
		boolean firstWord = true;
		for (String word : words) {
			if (!firstWord) {
				builder.append(",");
			}
			builder.append(String.format("\"%s\"", word));
			firstWord = false;
		}
		builder.append("]},\"series\":[{\"values\":[");
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
		builder.append("]}]}]}");
		
		String dataUrlEncoding = UTF8URLEncoder.encode(builder.toString());
		
		String queryString = String.format("d=%s", dataUrlEncoding);
		
		return queryString;
	}
	
}
