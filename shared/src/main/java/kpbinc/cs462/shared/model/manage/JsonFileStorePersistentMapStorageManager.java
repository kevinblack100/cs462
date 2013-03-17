package kpbinc.cs462.shared.model.manage;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import javax.servlet.ServletContext;

import kpbinc.cs462.shared.model.aspect.HasID;
import kpbinc.io.util.JsonFileStore;
import kpbinc.io.util.JsonFileStorePersistentMap;
import kpbinc.util.logging.GlobalLogUtils;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class JsonFileStorePersistentMapStorageManager<I, T extends HasID<I>> implements StorageManager<I, T> {

	//= Member Data ====================================================================================================
	
	@Autowired
	private ServletContext servletContext;
	
	private String fileStoreRelativePath;
	
	private Map<I, T> itemMap;
	
	
	//= Initialization =================================================================================================
	
	//- Constructors ---------------------------------------------------------------------------------------------------
	
	public JsonFileStorePersistentMapStorageManager(String fileStoreRelativePath) {
		GlobalLogUtils.logConstruction(this);
		if (fileStoreRelativePath == null) {
			throw new IllegalArgumentException("file store relative path must not be null");
		}
		this.fileStoreRelativePath = fileStoreRelativePath;
	}
	
	//- Support --------------------------------------------------------------------------------------------------------
	
	protected Map<I, T> getItemMap() {
		if (itemMap == null) {
			String fullPath = servletContext.getRealPath(fileStoreRelativePath);
			File file = new File(fullPath);
			JsonFileStore<Map<I, T>> fileStore = getJsonFileStore(file);
			itemMap = JsonFileStorePersistentMap.<I, T>buildWithDelegateFromFileStore(fileStore);
		}
		return itemMap;
	}
	
	protected abstract JsonFileStore<Map<I, T>> getJsonFileStore(File file);

	
	//= Interface ======================================================================================================
	
	@Override
	public boolean register(T item) {
		boolean result = false;
		I id = item.getID();
		if (   id != null
			&& !managesItemWithID(id)) {
			getItemMap().put(id, item);
			result = true;
		}
		return result;
	}

	@Override
	public T retrieve(I id) {
		T item = getItemMap().get(id);
		return item;
	}

	@Override
	public Collection<T> retrieveAll() {
		Collection<T> items = getItemMap().values();
		return items;
	}

	@Override
	public boolean manages(T item) {
		I id = item.getID();
		boolean result = managesItemWithID(id);
		return result;
	}

	@Override
	public boolean managesItemWithID(I id) {
		boolean result = (   id != null
						  && getItemMap().containsKey(id));
		return result;
	}

	@Override
	public T update(T item) {
		T previousItem = null;
		I id = item.getID();
		if (managesItemWithID(id)) {
			previousItem = getItemMap().put(id, item);
		}
		return previousItem;
	}

	@Override
	public boolean unregister(T item) {
		boolean result = false;
		I id = item.getID();
		if (managesItemWithID(id)) {
			getItemMap().remove(id);
			result = true;
		}
		return result;
	}

	@Override
	public T unregisterItemWithID(I id) {
		T item = null;
		if (id != null) {
			item = getItemMap().remove(id);
		}
		return item;
	}

}
