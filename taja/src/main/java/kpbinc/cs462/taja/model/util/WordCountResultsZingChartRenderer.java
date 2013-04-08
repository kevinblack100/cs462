package kpbinc.cs462.taja.model.util;

import kpbinc.cs462.taja.model.WordCountResults;
import kpbinc.net.UTF8URLEncoder;
import kpbinc.util.logging.GlobalLogUtils;

public class WordCountResultsZingChartRenderer {

	//= Initialization =================================================================================================
	
	public WordCountResultsZingChartRenderer() {
		GlobalLogUtils.logConstruction(this);
	}

	
	//= Interface ======================================================================================================
	
	public String getQueryString(WordCountResults results) {
		StringBuilder builder = new StringBuilder();
		builder.append("{\"graphset\":[{\"type\":\"bar\",\"scale-x\":{\"values\":[");
		boolean firstWord = true;
		for (String word : results.getWordCounts().keySet()) {
			if (!firstWord) {
				builder.append(",");
			}
			builder.append(String.format("\"%s\"", word));
			firstWord = false;
		}
		builder.append("]},\"series\":[{\"values\":[");
		boolean firstCount = true;
		for (Long count : results.getWordCounts().values()) {
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
	}
	
}
