package kpbinc.util;

import java.util.List;
import java.util.Map;

public class MapUtils {

	// = Interface =====================================================================================================
	
	/**
	 * @param map
	 * @param keyOptions
	 * @return contained key or null if no key is contained (or null key is contained)
	 */
	public static <K, V> K containsOneOf(Map<K, V> map, List<K> keyOptions) {
		K containedKey = null;
		for (K key : keyOptions) {
			if (map.containsKey(key)) {
				containedKey = key;
				break;
			}
		}
		return containedKey;
	}
	
}
