package kpbinc.cs462.shop.model.manage;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import javax.servlet.ServletContext;

import kpbinc.cs462.shop.model.Order;
import kpbinc.io.util.JsonFileStore;
import kpbinc.io.util.JsonFileStorePersistentMap;
import kpbinc.util.logging.GlobalLogUtils;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.type.TypeReference;

public class OrderManager {
	
	//= Class Data =====================================================================================================
	
	private static final Long seedID = 1L;
	
	
	//= Member Data ====================================================================================================
	
	@Autowired
	private ServletContext servletContext;
	
	private String fileStoreRelativePath;
	
	private JsonFileStorePersistentMap<Long, Order> orderIndex;
	
	private Long nextID;
	
	
	//= Initialization =================================================================================================
	
	/**
	 * @param fileStoreRelativePath
	 * @throws IllegalArgumentException if fileStoreRelativePath is null
	 */
	public OrderManager(String fileStoreRelativePath) {
		GlobalLogUtils.logConstruction(this);
		if (fileStoreRelativePath == null) {
			throw new IllegalArgumentException("file store relative path must not be null");
		}
		this.fileStoreRelativePath = fileStoreRelativePath;
		
		this.orderIndex = null;
		
		this.nextID = null;
	}
	
	
	//= Interface ======================================================================================================
	
	private JsonFileStorePersistentMap<Long, Order> getIndex() {
		if (orderIndex == null) {
			String fullPath = servletContext.getRealPath(fileStoreRelativePath);
			File authTokenStoreFile = new File(fullPath);
			JsonFileStore<Map<Long, Order>> fileStore = 
					new JsonFileStore<Map<Long, Order>>(authTokenStoreFile, 
							new TypeReference<Map<Long, Order>>() {});
			orderIndex = JsonFileStorePersistentMap.<Long, Order>buildWithDelegateFromFileStore(fileStore);
		}
		return orderIndex;
	}

	public void register(Long id, Order order) {
		getIndex().put(id, order);
		updateNextID(id);
	}
	
	public Order get(Long id) {
		Order order = getIndex().get(id);
		return order;
	}
	
	public Collection<Order> getAll() {
		Collection<Order> orders = getIndex().values();
		return orders;
	}
	
	public Order update(Long orderID, Order order) {
		Order previous = getIndex().put(orderID, order);
		return previous;
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
