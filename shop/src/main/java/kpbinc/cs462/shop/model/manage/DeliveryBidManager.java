package kpbinc.cs462.shop.model.manage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import kpbinc.cs462.shared.model.aspect.IDGeneratingIDAccessor;
import kpbinc.cs462.shared.model.aspect.IDGenerator;
import kpbinc.cs462.shared.model.aspect.IncreasingLongIDGeneratorStrategy;
import kpbinc.cs462.shared.model.manage.JsonFileStorePersistentMapStorageManager;
import kpbinc.cs462.shop.model.DeliveryBid;
import kpbinc.io.util.JsonFileStore;
import kpbinc.util.PropertyAccessor;
import kpbinc.util.logging.GlobalLogUtils;

import org.apache.commons.lang3.Validate;

import com.fasterxml.jackson.core.type.TypeReference;

public class DeliveryBidManager
	extends JsonFileStorePersistentMapStorageManager<Long, DeliveryBid> {
	
	//= Initialization =================================================================================================
	
	//- Constructors ---------------------------------------------------------------------------------------------------
	
	/**
	 * @see JsonFileStorePersistentMapStorageManager
	 */
	public DeliveryBidManager(String fileStoreRelativePath) {
		super(fileStoreRelativePath);
		GlobalLogUtils.logConstruction(this);
	}
	
	//- Support --------------------------------------------------------------------------------------------------------
	
	@Override
	protected JsonFileStore<Map<Long, DeliveryBid>> getJsonFileStore(File file) {
		JsonFileStore<Map<Long, DeliveryBid>> jsonFileStore = 
				new JsonFileStore<Map<Long, DeliveryBid>>(file, new TypeReference<Map<Long, DeliveryBid>>() {});
		return jsonFileStore;
	}
	
	@Override
	protected PropertyAccessor<? super DeliveryBid, Long> initializeKeyAccessor() {
		PropertyAccessor<? super DeliveryBid, Long> keyAccessor =
				new IDGeneratingIDAccessor<Long>(new IDGenerator<Long>(new IncreasingLongIDGeneratorStrategy(this)));
		return keyAccessor;
	}
	
	
	//= Interface ======================================================================================================

	/**
	 * @param orderID ID of the order to find the bids for
	 * @return the bids for the order with the given ID
	 * 
	 * @throws NullPointerException if orderID is null
	 */
	public Collection<DeliveryBid> retrieveByOrderID(Long orderID) {
		Validate.notNull(orderID, "order ID must not be null");
		
		Collection<DeliveryBid> bids = new ArrayList<DeliveryBid>();
		
		for (DeliveryBid bid : retrieveAll()) {
			if (orderID.equals(bid.getOrderID())) {
				bids.add(bid);
			}
		}
		
		return bids;
	}

}
