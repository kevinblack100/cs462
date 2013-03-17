package kpbinc.cs462.shared.model.manage;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import javax.servlet.ServletContext;

import kpbinc.io.util.JsonFileStore;
import kpbinc.io.util.JsonFileStorePersistentMap;
import kpbinc.util.PropertyAccessor;
import kpbinc.util.logging.GlobalLogUtils;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class JsonFileStorePersistentMapStorageManager<K, I> implements StorageManager<K, I> {

	//= Member Data ====================================================================================================
	
	@Autowired
	private ServletContext servletContext;
	
	private String fileStoreRelativePath;
	
	private PropertyAccessor<I, K> keyAccessor;
	
	private Map<K, I> itemMap;
	
	
	//= Initialization =================================================================================================
	
	//- Constructors ---------------------------------------------------------------------------------------------------
	
	public JsonFileStorePersistentMapStorageManager(
			String fileStoreRelativePath,
			PropertyAccessor<I, K> keyAccessor) {
		
		if (fileStoreRelativePath == null) {
			throw new IllegalArgumentException("file store relative path must not be null");
		}
		this.fileStoreRelativePath = fileStoreRelativePath;
		
		if (keyAccessor == null) {
			throw new IllegalArgumentException("key accessor must not be null");
		}
		this.keyAccessor = keyAccessor;
			
		GlobalLogUtils.logConstruction(this);
	}
	
	//- Support --------------------------------------------------------------------------------------------------------
	
	protected Map<K, I> getItemMap() {
		if (itemMap == null) {
			String fullPath = servletContext.getRealPath(fileStoreRelativePath);
			File file = new File(fullPath);
			JsonFileStore<Map<K, I>> fileStore = getJsonFileStore(file);
			itemMap = JsonFileStorePersistentMap.<K, I>buildWithDelegateFromFileStore(fileStore);
		}
		return itemMap;
	}
	
	protected abstract JsonFileStore<Map<K, I>> getJsonFileStore(File file);

	
	//= Interface ======================================================================================================
	
	@Override
	public boolean register(I item) {
		boolean result = false;
		K key = keyAccessor.getPropertyValue(item);
		if (   key != null
			&& !managesItemWithKey(key)) {
			getItemMap().put(key, item);
			result = true;
		}
		return result;
	}

	@Override
	public I retrieve(K key) {
		I item = getItemMap().get(key);
		return item;
	}

	@Override
	public Collection<I> retrieveAll() {
		Collection<I> items = getItemMap().values();
		return items;
	}

	@Override
	public boolean manages(I item) {
		K key = keyAccessor.getPropertyValue(item);
		boolean result = managesItemWithKey(key);
		return result;
	}

	@Override
	public boolean managesItemWithKey(K key) {
		boolean result = (   key != null
						  && getItemMap().containsKey(key));
		return result;
	}

	@Override
	public I update(I item) {
		I previousItem = null;
		K key = keyAccessor.getPropertyValue(item);
		if (managesItemWithKey(key)) {
			previousItem = getItemMap().put(key, item);
		}
		return previousItem;
	}

	@Override
	public boolean unregister(I item) {
		boolean result = false;
		K key = keyAccessor.getPropertyValue(item);
		if (managesItemWithKey(key)) {
			getItemMap().remove(key);
			result = true;
		}
		return result;
	}

	@Override
	public I unregisterItemWithKey(K key) {
		I item = null;
		if (key != null) {
			item = getItemMap().remove(key);
		}
		return item;
	}

}
