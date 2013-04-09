package kpbinc.cs462.taja.model.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.Validate;

import kpbinc.cs462.taja.model.WordCountResults;
import kpbinc.util.logging.GlobalLogUtils;

public class WordCountResultsSampleCalculator {

	//= Class Data =====================================================================================================
	
	private static class InvertedRankComparator implements Comparator<Long> {

		private static final int BEFORE = -1;
		private static final int EQUALS = 0;
		private static final int AFTER = 1;
		
		@Override
		public int compare(Long lhs, Long rhs) {
			if (lhs == null) {
				return AFTER;
			}
			// else
			if (rhs == null) {
				return BEFORE;
			}
			// else
			if (lhs < rhs) {
				return AFTER;
			}
			// else
			if (rhs < lhs) {
				return BEFORE;
			}
			// else
			return EQUALS;
		}
		
	}
	
	
	//= Initialization =================================================================================================
	
	public WordCountResultsSampleCalculator() {
		GlobalLogUtils.logConstruction(this);
	}

	
	//= Interface ======================================================================================================
	
	public List<String> getWordsOfTopNRank(WordCountResults results, int numberOfTopRanks) {
		Validate.notNull(results, "results must not be null");
		Validate.notNull(results.getWordCounts(), "results.wordCounts must not be null");
		
		Map<Long, List<String>> invertedWordCounts = new TreeMap<Long, List<String>>(new InvertedRankComparator());
		for (Map.Entry<String, Long> wordCount : results.getWordCounts().entrySet()) {
			Long count = wordCount.getValue();
			if (!invertedWordCounts.containsKey(count)) {
				invertedWordCounts.put(count, new ArrayList<String>());
			}
			invertedWordCounts.get(count).add(wordCount.getKey());
		}
		
		List<String> wordsOfTopNRank = new ArrayList<String>();
		Iterator<Long> rankIterator = invertedWordCounts.keySet().iterator();
		int ranksAdded = 0;
		while (   ranksAdded < numberOfTopRanks
			   && rankIterator.hasNext()) {
			Long rank = rankIterator.next();
			wordsOfTopNRank.addAll(wordsOfTopNRank.size(), invertedWordCounts.get(rank));
			++ranksAdded;
		}
		
		return wordsOfTopNRank;
	}
	
}
