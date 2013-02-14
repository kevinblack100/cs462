package kpbinc.io.util;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * Handles serialization to and deserialization from a given file using json.
 * 
 * @author Kevin Black
 * 
 * @param <T> type to serialize
 */
public class JsonFileStore<T> {

	private File fileStore;
	
	/**
	 * @param fileStore the file to read/write, must exist
	 */
	public JsonFileStore(File fileStore) {
		assert(fileStore != null);
		assert(fileStore.isFile());
		
		this.fileStore = fileStore;
	}
	
	/**
	 * Commits (serializes) the given object to the filestore. Does not handle IOExceptions that occur while trying to
	 * write to the filestore (hence the "raw").
	 */
	public void commitRaw(T object) throws IOException {
		if (!fileStore.getParentFile().exists()) {
			// in case the file was deleted
			fileStore.getParentFile().mkdirs();
		}
		JsonSerializer.serialize(object, fileStore);
	}
	
	/**
	 * Commits (serializes) the given object to the filestore. If the filestore cannot be written to then returns false.
	 * 
	 * @return true if the commit was successful, false otherwise.
	 */
	public boolean commit(T object) {
		boolean flag = true;
		try {
			commitRaw(object);
		}
		catch (IOException e) {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * Reads (deserializes) the contents of the filestore and returns the deserialized object. Does not handle
	 * IOExceptions that occur while trying to write to the filestore (hence the "raw").
	 * 
	 * @return deserialized object
	 */
	public T readRaw() throws IOException {
		T object = JsonSerializer.deserialize(fileStore, new TypeReference<T>() {});
		return object;
	}
	
	/**
	 * Reads (deserializes) the contents of the filestore and returns the deserialized object. Returns null if the
	 * filestore is empty or cannot be read.
	 * 
	 * @return deserialized object, or null if the filestore is empty or cannot be read
	 */
	public T read() {
		T object = null;
		try {
			object = readRaw();
		}
		catch (IOException e) {
			// do nothing
		}
		return object;
	}
	
}
