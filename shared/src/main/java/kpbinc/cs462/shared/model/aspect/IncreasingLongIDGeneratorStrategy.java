package kpbinc.cs462.shared.model.aspect;

import org.apache.commons.lang3.Validate;

import kpbinc.cs462.shared.model.manage.StorageManager;
import kpbinc.util.logging.GlobalLogUtils;

public class IncreasingLongIDGeneratorStrategy implements IDGenerator.Strategy<Long> {

	//= Member Data ====================================================================================================
	
	// TODO require a more generic construct to get the initial set of IDs?
	private StorageManager<Long, ? extends HasId<Long>> storageManager;
	private Long seedID;
	
	
	//= Initialization =================================================================================================
	
	public IncreasingLongIDGeneratorStrategy(
			StorageManager<Long, ? extends HasId<Long>> storageManager,
			Long seedID) {
		GlobalLogUtils.logConstruction(this);
		
		Validate.notNull(storageManager, "storage manager must not be null");
		this.storageManager = storageManager;
		
		Validate.notNull(seedID, "seed ID must not be null");
		this.seedID = seedID;
	}

	
	//= Interface ======================================================================================================
	
	@Override
	public Long getInitialID() {
		Long maxID = seedID;
		for (HasId<Long> hasID : storageManager.retrieveAll()) {
			Long id = hasID.getId();
			if (   id != null
				&& maxID <= id) {
				maxID = id + 1L;
			}
		}
		return maxID;
	}

	@Override
	public Long advanceGenerator(Long claimedID) {
		Long nextID = claimedID + 1L;
		return nextID;
	}
	
}
