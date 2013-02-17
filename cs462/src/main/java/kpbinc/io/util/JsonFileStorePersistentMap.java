package kpbinc.io.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Map decorator that persists the map to a json file any time the map itself is changed. Does not monitor the contents
 * of the map, so the client is responsible to call commit() after modifying the contents if they want the contents
 * persisted to the file store.
 * 
 * @author Kevin Black
 *
 * @param <K> key type
 * @param <V> value type
 */
public class JsonFileStorePersistentMap<K, V> implements Map<K, V> {

	//==================================================================================================================
	// Member Data
	//==================================================================================================================
	
	private Map<K, V> delegate;
	
	private JsonFileStore<Map<K, V>> fileStore;
	
	
	//==================================================================================================================
	// Initialization
	//==================================================================================================================

	/**
	 * Creates a new JsonFileStorePersistentMap, reading the map from the file store or writing the delegate map to the
	 * given file store.
	 * 
	 * @param fileStore the json file store, configured to properly read and write the stored map type
	 * @param readFlag whether to read the initial map from the file store are write the given map to the file store
	 * @param defaultOrDelegate default map if readFlag == true and reading from the file store fails, initial delegate
	 * if readFlag == false
	 */
	public JsonFileStorePersistentMap(
			JsonFileStore<Map<K, V>> fileStore,
			boolean readFlag,
			Map<K, V> defaultOrDelegate) {
		assert(fileStore != null);
		assert(defaultOrDelegate != null);
		
		this.fileStore = fileStore;
		
		if (readFlag) {
			this.delegate = fileStore.read();
			if (this.delegate == null) {
				this.delegate = defaultOrDelegate;
			}
		}
		else {
			this.delegate = defaultOrDelegate;
			commit();
		}
	}
	
	
	//==================================================================================================================
	// Interface
	//==================================================================================================================
	
	/**
	 * Commits the map to the file store.
	 * 
	 * @return true if the commit was successful, false otherwise.
	 */
	public boolean commit() {
		boolean result = fileStore.commit(delegate);
		return result;
	}
	
	@Override
	public boolean equals(Object object) {
		// compare map contents
		boolean result = delegate.equals(object);
		// TODO if contents are the same, compare the file stores if object is a JsonFileStorePersistentMap?
		return result;
	}
	
	@Override
	public String toString() {
		String result = delegate.toString();
		return result;
	}
	
	@Override
	public int size() {
		int result = delegate.size();
		return result;
	}

	@Override
	public boolean isEmpty() {
		boolean result = delegate.isEmpty();
		return result;
	}

	@Override
	public boolean containsKey(Object key) {
		boolean result = delegate.containsKey(key);
		return result;
	}

	@Override
	public boolean containsValue(Object value) {
		boolean result = delegate.containsValue(value);
		return result;
	}

	@Override
	public V get(Object key) {
		V result = delegate.get(key);
		return result;
	}

	@Override
	public V put(K key, V value) {
		V result = delegate.put(key, value);
		commit();
		return result;
	}

	@Override
	public V remove(Object key) {
		V result = delegate.remove(key);
		commit();
		return result;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		delegate.putAll(m);
		commit();		
	}

	@Override
	public void clear() {
		delegate.clear();
		commit();
	}

	@Override
	public Set<K> keySet() {
		Set<K> result = delegate.keySet();
		return result;
	}

	@Override
	public Collection<V> values() {
		Collection<V> result = delegate.values();
		return result;
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		Set<java.util.Map.Entry<K, V>> result = delegate.entrySet();
		return result;
	}

}
