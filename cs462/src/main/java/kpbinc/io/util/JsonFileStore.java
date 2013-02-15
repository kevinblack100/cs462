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

	//==================================================================================================================
	// Member Data
	//==================================================================================================================
	
	private File fileStore;
	
	private TypeReference<T> typeRef;
	
	
	//==================================================================================================================
	// Initialization
	//==================================================================================================================
	
	/**
	 * Constructs a JsonFileStore instance. Only use this method if the type is only composed of basic serialization
	 * types known to Jackson. Use the other constructor if you use custom object types.
	 * 
	 * @param fileStore the file to read/write, must be a file
	 */
	public JsonFileStore(File fileStore) {
		setFileStore(fileStore);
		setTypeReference(new TypeReference<T>() {});
	}
	
	/**
	 * Constructs a JsonFileStore instance. Specifically intended to facilitate the use of custom types, although it
	 * will work for the basic serialization types as well.
	 * 
	 * @param fileStore the file to read/write, must be a file
	 * @param typeRef a type reference describing the custom type
	 */
	public JsonFileStore(File fileStore, TypeReference<T> typeRef) {
		setFileStore(fileStore);
		setTypeReference(typeRef);
	}
	
	private void setFileStore(File fileStore) {
		assert(fileStore != null);
		assert(fileStore.isFile());
		
		this.fileStore = fileStore;
	}
	
	private void setTypeReference(TypeReference<T> typeRef) {
		assert(typeRef != null);
		
		this.typeRef = typeRef;
	}
	
	//==================================================================================================================
	// Interface
	//==================================================================================================================
	
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
		T object = JsonSerializer.deserialize(fileStore, typeRef);
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
	
	public T readRawAlt(TypeReference<T> typeRef) throws IOException {
		T object = JsonSerializer.deserialize(fileStore, typeRef);
		return object;
	}
	
	public T readAlt(TypeReference<T> typeRef) {
		T object = null;
		try {
			object = readRawAlt(typeRef);
		}
		catch (IOException e) {
			// do nothing
		}
		return object;
	}
	
}
