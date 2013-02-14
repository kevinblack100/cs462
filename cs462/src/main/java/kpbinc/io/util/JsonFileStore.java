package kpbinc.io.util;

import java.io.File;
import java.io.IOException;

/**
 * Handles serialization to and deserialization from a particular file using json.
 * 
 * @author Kevin Black
 * 
 * @param <T> type to serialize
 */
public class JsonFileStore<T> {

	private File fileStore;
	
	public JsonFileStore(File fileStore) {
		assert(fileStore != null);
		assert(fileStore.isFile());
		
		this.fileStore = fileStore;
	}
	
	/**
	 * Commits (serializes) the given object to the filestore. 
	 */
	public void commit(T object) throws IOException {
		
	}
	
	/**
	 * Reads (deserializes) the contents of the filestore and returns the deserialized object.
	 * 
	 * @return deserialized object
	 */
	public T read() throws IOException {
		return null;
	}
	
}
