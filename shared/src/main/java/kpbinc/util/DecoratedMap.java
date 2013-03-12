package kpbinc.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class DecoratedMap<K, V> implements Map<K, V> {	
	
	//= Member Data ====================================================================================================
	
	private Map<K, V> delegateMap;
	
	// TODO store a MapDecorator
	
	
	//= Initialization =================================================================================================
	
	public DecoratedMap(Map<K, V> delegateMap) {
		if (delegateMap == null) {
			throw new IllegalArgumentException("delegate map must not be null");
		}
		
		this.delegateMap = delegateMap;
	}
	
	// TODO constructor that takes a delegateMap and a MapDecorator

	
	//= Interface ======================================================================================================
	
	@Override
	public boolean equals(Object object) {
		boolean result = delegateMap.equals(object);
		return result;
	}
	
	@Override
	public int hashCode() {
		int hash = delegateMap.hashCode();
		return hash;
	}
	
	@Override
	public void clear() {
		delegateMap.clear();		
	}

	@Override
	public boolean containsKey(Object key) {
		boolean result = delegateMap.containsKey(key);
		return result;
	}

	@Override
	public boolean containsValue(Object value) {
		boolean result = delegateMap.containsValue(value);
		return result;
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		Set<java.util.Map.Entry<K, V>> entries = delegateMap.entrySet();
		return entries;
	}

	@Override
	public V get(Object key) {
		V value = delegateMap.get(key);
		return value;
	}

	@Override
	public boolean isEmpty() {
		boolean result = delegateMap.isEmpty();
		return result;
	}

	@Override
	public Set<K> keySet() {
		Set<K> keys = delegateMap.keySet();
		return keys;
	}

	@Override
	public V put(K key, V value) {
		V prevValue = delegateMap.put(key, value);
		return prevValue;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		delegateMap.putAll(m);
	}

	@Override
	public V remove(Object key) {
		V value = delegateMap.remove(key);
		return value;
	}

	@Override
	public int size() {
		int result = delegateMap.size();
		return result;
	}

	@Override
	public Collection<V> values() {
		Collection<V> allValues = delegateMap.values();
		return allValues;
	}
	
}
