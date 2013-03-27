package kpbinc.cs462.driver.model.manage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;

import kpbinc.cs462.driver.model.DeliveryRequest;
import kpbinc.cs462.shared.model.aspect.IDGeneratingIDAccessor;
import kpbinc.cs462.shared.model.aspect.IDGenerator;
import kpbinc.cs462.shared.model.aspect.IncreasingLongIDGeneratorStrategy;
import kpbinc.cs462.shared.model.manage.JsonFileStorePersistentMapStorageManager;
import kpbinc.io.util.JsonFileStore;
import kpbinc.util.PropertyAccessor;
import kpbinc.util.logging.GlobalLogUtils;

public class DeliveryRequestManager 
	extends JsonFileStorePersistentMapStorageManager<Long, DeliveryRequest> {

	//= Initialization =================================================================================================
	
	//- Constructor ----------------------------------------------------------------------------------------------------
	
	public DeliveryRequestManager(String fileStoreRelativePath) {
		super(fileStoreRelativePath);
		GlobalLogUtils.logConstruction(this);
	}

	//- Support --------------------------------------------------------------------------------------------------------
	
	@Override
	protected JsonFileStore<Map<Long, DeliveryRequest>> getJsonFileStore(File file) {
		JsonFileStore<Map<Long, DeliveryRequest>> jsonFileStore =
				new JsonFileStore<Map<Long, DeliveryRequest>>(file,
						new TypeReference<Map<Long, DeliveryRequest>>() {});
		return jsonFileStore;
	}

	@Override
	protected PropertyAccessor<? super DeliveryRequest, Long> initializeKeyAccessor() {
		PropertyAccessor<? super DeliveryRequest, Long> keyAccessor =
				new IDGeneratingIDAccessor<Long>(new IDGenerator<Long>(new IncreasingLongIDGeneratorStrategy(this)));
		return keyAccessor;
	}

	
	//= Interface ======================================================================================================
	
	public Collection<DeliveryRequest> retrieveByUsername(String username) {
		Collection<DeliveryRequest> result = new ArrayList<DeliveryRequest>();
		for (DeliveryRequest request : retrieveAll()) {
			if (StringUtils.equalsIgnoreCase(request.getUsername(), username)) {
				result.add(request);
			}
		}
		return result;
	}
	
	public Collection<DeliveryRequest> retrieveByShopDeliveryId(Long shopDeliveryId) {
		Collection<DeliveryRequest> result = new ArrayList<DeliveryRequest>();
		for (DeliveryRequest request : retrieveAll()) {
			if (request.getShopDeliveryId().equals(shopDeliveryId)) {
				result.add(request);
			}
		}
		return result;
	}
	
	public DeliveryRequest retrieveByUsernameAndShopDeliveryId(String username, Long shopDeliveryId) {
		DeliveryRequest result = null;
		for (DeliveryRequest request : retrieveAll()) {
			if (   StringUtils.equalsIgnoreCase(request.getUsername(), username)
				&& request.getShopDeliveryId().equals(shopDeliveryId)) {
				result = request;
				break;
			}
		}
		return result;
	}
}
