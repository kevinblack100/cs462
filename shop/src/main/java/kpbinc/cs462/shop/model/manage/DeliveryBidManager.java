package kpbinc.cs462.shop.model.manage;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import javax.servlet.ServletContext;

import kpbinc.cs462.shop.model.DeliveryBid;
import kpbinc.io.util.JsonFileStore;
import kpbinc.io.util.JsonFileStorePersistentMap;
import kpbinc.util.logging.GlobalLogUtils;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.type.TypeReference;

public class DeliveryBidManager {

	//= Class Data =====================================================================================================
	
	private static final Long seedID = 1L;
	
	
	//= Member Data ====================================================================================================
	
	@Autowired
	private ServletContext servletContext;
	
	private String fileStoreRelativePath;
	
	private JsonFileStorePersistentMap<Long, DeliveryBid> bidIndex;
	
	private Long nextID;
	
	
	//= Initialization =================================================================================================
	
	/**
	 * @param fileStoreRelativePath
	 * @throws IllegalArgumentException if fileStoreRelativePath is null
	 */
	public DeliveryBidManager(String fileStoreRelativePath) {
		GlobalLogUtils.logConstruction(this);
		if (fileStoreRelativePath == null) {
			throw new IllegalArgumentException("file store relative path must not be null");
		}
		this.fileStoreRelativePath = fileStoreRelativePath;
		
		this.bidIndex = null;
		
		this.nextID = null;
	}
	
	
	//= Interface ======================================================================================================
	
	private JsonFileStorePersistentMap<Long, DeliveryBid> getIndex() {
		if (bidIndex == null) {
			String fullPath = servletContext.getRealPath(fileStoreRelativePath);
			File authTokenStoreFile = new File(fullPath);
			JsonFileStore<Map<Long, DeliveryBid>> fileStore = 
					new JsonFileStore<Map<Long, DeliveryBid>>(authTokenStoreFile, 
							new TypeReference<Map<Long, DeliveryBid>>() {});
			bidIndex = JsonFileStorePersistentMap.<Long, DeliveryBid>buildWithDelegateFromFileStore(fileStore);
		}
		return bidIndex;
	}

	public void register(Long id, DeliveryBid bid) {
		getIndex().put(id, bid);
		updateNextID(id);
	}
	
	public DeliveryBid get(Long id) {
		DeliveryBid bid = getIndex().get(id);
		return bid;
	}
	
	public Collection<DeliveryBid> getAll() {
		Collection<DeliveryBid> orders = getIndex().values();
		return orders;
	}
	
	public Long getNextID() {
		if (nextID == null) {
			// find the max ID
			for (Long id : getIndex().keySet()) {
				updateNextID(id);
			}
			
			if (nextID == null) {
				nextID = seedID;
			}
		}
		return nextID;
	}
	
	
	//= Support ========================================================================================================
	
	private void updateNextID(Long candidateID) {
		if (   nextID == null
			|| nextID <= candidateID) {
			nextID = new Long(candidateID.longValue() + 1L);
		}
	}

}
