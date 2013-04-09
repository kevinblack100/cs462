package kpbinc.cs462.taja.model.util;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import kpbinc.cs462.taja.model.WordCountJobResults;
import kpbinc.cs462.taja.model.WordCountResults;

import org.junit.Test;

public class WordCountResultsSampleCalculatorTests {

	//= Tests ==========================================================================================================
	
	//- getWordsOfTopNRank ---------------------------------------------------------------------------------------------
	
	@Test
	public void testGetWordsOfTopNRankMostCommonUseCase() {
		// ARRANGE
		WordCountResults results = getStandardResults();
		int N = 2;
		List<String> expectedWordsOfTopNRank = Arrays.asList(TOP_RANK_ITEM, MID_RANK_ITEM);
		
		actAndAssertGetWordsOfTopNRank(results, N, expectedWordsOfTopNRank);
	}
	
	@Test
	public void testGetWordsOfTopNRankNZero() {
		// ARRANGE
		WordCountResults results = getStandardResults();
		int N = 0; 
		List<String> expectedWordsOfTopNRank = Collections.<String>emptyList();
		
		actAndAssertGetWordsOfTopNRank(results, N, expectedWordsOfTopNRank);
	}
	
	@Test
	public void testGetWordsOfTopNRankNGreaterThanNumberOfItems() {
		// ARRANGE
		WordCountResults results = getStandardResults();
		int N = 4; 
		List<String> expectedWordsOfTopNRank = Arrays.asList(TOP_RANK_ITEM, MID_RANK_ITEM, BOTTOM_RANK_ITEM);
		
		actAndAssertGetWordsOfTopNRank(results, N, expectedWordsOfTopNRank);
	}
	
	@Test
	public void testGetWordsOfTopNRankWithTies() {
		// ARRANGE
		WordCountResults results = getTiedResults();
		int N = 2;
		List<String> expectedWordsOfTopNRank = Arrays.asList(
				TIED_TOP_RANK_ITEM, TOP_RANK_ITEM,
				TIED_MID_RANK_ITEM, MID_RANK_ITEM);
		
		actAndAssertGetWordsOfTopNRank(results, N, expectedWordsOfTopNRank);
	}
	
	@Test
	public void testGetWordsOfTopNRankWithTiesNZero() {
		// ARRANGE
		WordCountResults results = getTiedResults();
		int N = 0;
		List<String> expectedWordsOfTopNRank = Collections.<String>emptyList();
		
		actAndAssertGetWordsOfTopNRank(results, N, expectedWordsOfTopNRank);
	}
	
	@Test
	public void testGetWordsOfTopNRankWithTiesNGreaterThanNumberOfItems() {
		// ARRANGE
		WordCountResults results = getTiedResults();
		int N = 4;
		List<String> expectedWordsOfTopNRank = Arrays.asList(
				TIED_TOP_RANK_ITEM, TOP_RANK_ITEM,
				TIED_MID_RANK_ITEM,	MID_RANK_ITEM,
				TIED_BOTTOM_RANK_ITEM, BOTTOM_RANK_ITEM);
		
		actAndAssertGetWordsOfTopNRank(results, N, expectedWordsOfTopNRank);
	} 
	
	
	//= Support ========================================================================================================
	
	//- ARRANGE --------------------------------------------------------------------------------------------------------
	
	private static final Long TOP_RANK = 3L;
	private static final Long MID_RANK = 2L;
	private static final Long BOTTOM_RANK = 1L;
	
	private static final String TOP_RANK_ITEM = "c";
	private static final String TIED_TOP_RANK_ITEM = "A";
	private static final String MID_RANK_ITEM = "a";
	private static final String TIED_MID_RANK_ITEM = "B";
	private static final String BOTTOM_RANK_ITEM = "b";
	private static final String TIED_BOTTOM_RANK_ITEM = "C";
	
	private WordCountResults getStandardResults() {
		WordCountResults results = new WordCountJobResults();
		Map<String, Long> wordCounts = new TreeMap<String, Long>();
		wordCounts.put(MID_RANK_ITEM, MID_RANK);
		wordCounts.put(BOTTOM_RANK_ITEM, BOTTOM_RANK);
		wordCounts.put(TOP_RANK_ITEM, TOP_RANK);
		results.setWordCounts(wordCounts);
		
		return results;
	}
	
	private WordCountResults getTiedResults() {
		WordCountResults results = new WordCountJobResults();
		Map<String, Long> wordCounts = new TreeMap<String, Long>();
		wordCounts.put(TIED_TOP_RANK_ITEM, TOP_RANK);
		wordCounts.put(TIED_MID_RANK_ITEM, MID_RANK);
		wordCounts.put(TIED_BOTTOM_RANK_ITEM, BOTTOM_RANK);
		wordCounts.put(MID_RANK_ITEM, MID_RANK);
		wordCounts.put(BOTTOM_RANK_ITEM, BOTTOM_RANK);
		wordCounts.put(TOP_RANK_ITEM, TOP_RANK);
		results.setWordCounts(wordCounts);
		
		return results;
	}
	
	//- ACT/ASSERT -----------------------------------------------------------------------------------------------------
	
	private void actAndAssertGetWordsOfTopNRank(WordCountResults results, int N, List<String> expectedWordsOfTopNRank) {
		// ARRANGE
		WordCountResultsSampleCalculator calculator = new WordCountResultsSampleCalculator();
		
		// ACT/ASSERT
		List<String> actualWordsOfTopNRank = calculator.getWordsOfTopNRank(results, N);
		assertEquals(expectedWordsOfTopNRank, actualWordsOfTopNRank);
	}

}
