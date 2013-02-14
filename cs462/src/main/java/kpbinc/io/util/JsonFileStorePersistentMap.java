package kpbinc.io.util;

import java.io.File;
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
	 * Creates a new JsonFileStorePersistentMap, reading the delegate map from the given file. If the delegate cannot
	 * be read then defaults to an empty HashMap.
	 * 
	 * @param fileStoreFile file to read the map from and persist the map to
	 */
	public JsonFileStorePersistentMap(File fileStoreFile) {
		this.fileStore = new JsonFileStore<Map<K,V>>(fileStoreFile);
		this.delegate = fileStore.read();
		if (this.delegate == null) {
			this.delegate = new HashMap<K, V>();
		}
	}
	
	/**
	 * Creates a new JsonFileStorePersistentMap, writing the delegate map to the given file.
	 * 
	 * @param fileStoreFile file to persist the map to
	 */
	public JsonFileStorePersistentMap(Map<K, V> delegate, File fileStoreFile) {
		assert(delegate != null);
		
		this.delegate = delegate;
		this.fileStore = new JsonFileStore<Map<K, V>>(fileStoreFile);
		
		commit();
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
