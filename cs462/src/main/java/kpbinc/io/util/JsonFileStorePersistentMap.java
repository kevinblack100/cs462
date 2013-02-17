package kpbinc.io.util;

import java.util.Collection;
import java.util.HashMap;
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
	 * Creates a new JsonFileStorePersistentMap with the given file store and delegate.
	 * 
	 * @param fileStore the json file store, configured to properly read and write the stored map type
	 * @param delegate map decorated by this JsonFileStorePersistentMap
	 */
	public JsonFileStorePersistentMap(JsonFileStore<Map<K, V>> fileStore, Map<K, V> delegate) {
		assert(fileStore != null);
		assert(delegate != null);
		
		this.fileStore = fileStore;
		this.delegate = delegate;
	}
	
	/**
	 * Creates a new JsonFileStorePersistentMap with the given file store and an empty delegate map.
	 * 
	 * @param fileStore the json file store, configured to properly read and write the stored map type
	 * @return the constructed JsonFileStorePersistentMap
	 */
	public static <K, V> JsonFileStorePersistentMap<K, V> buildWithEmptyDelegate(JsonFileStore<Map<K, V>> fileStore) {
		Map<K, V> delegate = new HashMap<K, V>();
		JsonFileStorePersistentMap<K, V> result = new JsonFileStorePersistentMap<K, V>(fileStore, delegate);
		return result;
	}

	/**
	 * Creates a new JsonFileStorePersistentMap with the given file store, reading the delegate map from the file store.
	 * Falls back to buildWithEmptyDelegate if reading the initial delegate is unsuccessful.
	 * 
	 * @param fileStore the json file store, configured to properly read and write the stored map type
	 * @return the constructed JsonFileStorePersistentMap
	 */
	public static <K, V> JsonFileStorePersistentMap<K, V> buildWithDelegateFromFileStore(JsonFileStore<Map<K, V>> fileStore) {
		Map<K, V> delegate = fileStore.read();
		JsonFileStorePersistentMap<K, V> result = null;
		if (delegate == null) {
			result = buildWithEmptyDelegate(fileStore);
		}
		else {
			result = new JsonFileStorePersistentMap<K, V>(fileStore, delegate);
		}
		return result;
	}
	
	/**
	 * Creates a new JsonFileStorePersistentMap with the given file store and delegate and commits the delegate.
	 * 
	 * @param fileStore the json file store, configured to properly read and write the stored map type
	 * @param delegate map decorated by this JsonFileStorePersistentMap
	 * @return the constructed JsonFileStorePersistentMap
	 */
	public static <K, V> JsonFileStorePersistentMap<K, V> buildWithDelegate(JsonFileStore<Map<K, V>> fileStore, Map<K, V> delegate) {
		JsonFileStorePersistentMap<K, V> result = new JsonFileStorePersistentMap<K, V>(fileStore, delegate);
		result.commit();
		return result;
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
