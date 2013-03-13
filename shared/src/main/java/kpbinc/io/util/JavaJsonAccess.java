package kpbinc.io.util;

import java.util.Map;

/**
 * Intended to facilitate Json-like access to a Java Map<String, Object> as generated from JsonSerializer.deserialize
 * using Map.class.
 * 
 * @author Kevin Black
 */
public class JavaJsonAccess {

	/**
	 * A null return may indicate a problem with the provided key path, or that the actual value is null.
	 * 
	 * @param object
	 * @param keyPath
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object getValue(Map<String, Object> object, String... keyPath) {
		Object value = null;
		
		Map<String, Object> currentLevel = object;
		for (int i = 0, l = keyPath.length; i < l; i++) {
			String currentKey = keyPath[i];
			if (currentLevel.containsKey(currentKey)) {
				if (i < l - 1) {
					// must be a nested object
					try {
						currentLevel = (Map<String, Object>) currentLevel.get(currentKey);
					}
					catch (ClassCastException e) {
						// wasn't a nested object
						break;
					}
				}
				else {
					// last object so can be anything
					value = currentLevel.get(currentKey);
				}
			}
			else {
				// path doesn't exist
				break;
			}
		}
		
		return value;
	}
	
	/**
	 * @param object
	 * @param keyPath
	 * @throws ClassCastException if the value is not of the specified type
	 * @return
	 */
	public static <T> T getValueAs(Map<String, Object> object, String... keyPath) {
		Object value = getValue(object, keyPath);
		@SuppressWarnings("unchecked")
		T valueAsT = (T) value;
		return valueAsT;
	}
}
