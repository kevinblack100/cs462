package kpbinc.cs462.shared.model.aspect;

import org.apache.commons.lang3.Validate;

public class IDGenerator<V> {

	//= Class Data =====================================================================================================
	
	public static interface Strategy<T> {
		
		/**
		 * @return the initial "next" ID for the generator (not null).
		 */
		T getInitialID();
		
		/**
		 * @param claimedID the ID that was claimed
		 * @return the new "next" ID for the generator (not null).
		 */
		T advanceGenerator(T claimedID);
		
	}
	
	
	//= Member Data ====================================================================================================
	
	private Strategy<V> strategy;
	private V nextID;
	
	
	//= Initialization =================================================================================================
	
	/**
	 * @param strategy strategy to use for generating IDs
	 * 
	 * @throws NullPointerException if strategy is null
	 */
	public IDGenerator(Strategy<V> strategy) {
		Validate.notNull(strategy, "strategy must not be null");
		this.strategy = strategy;
		this.nextID = null;
	}
	
	
	//= Interface ======================================================================================================
	
	/**
	 * Returns the next ID available to be claimed without causing the next ID to be updated.
	 * 
	 * @return the next ID available to be claimed.
	 */
	public V getNextID() {
		if (nextID == null) {
			nextID = strategy.getInitialID();
		}
		return nextID;
	}
	
	/**
	 * Returns the next ID available to be claimed and then causes the next ID to be updated.
	 * 
	 * @return the claimed ID.
	 */
	public V claimNextID() {
		V claimedID = getNextID();
		claimID(claimedID);
		return claimedID;
	}
	
	/**
	 * @param id ID to be claimed
	 * 
	 * @throws NullPointerException if id is null
	 */
	public void claimID(V id) {
		Validate.notNull(id, "ID must not be null");
		nextID = strategy.advanceGenerator(id);
	}
}
