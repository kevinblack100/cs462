package kpbinc.cs462.shared.model.manage;

import java.util.Collection;

/**
 * Common interface model object storage management, whether in-memory, file-persistent, or DB persistent.
 * 
 * @author Kevin Black
 * 
 * @param <K> key type (primary key)
 * @param <I> model object item type
 */
public interface StorageManager<K, I> {

	/**
	 * Registers the given model object item with this StorageManager. Will not store null values or values with null
	 * keys.
	 * 
	 * @param item model object to be registered
	 * @return true if the item was registered, false if the item was not registered.
	 */
	boolean register(I item);
	
	/**
	 * Retrieves the model object item with the given key.
	 * 
	 * @param key key of the model object item to be retrieved.
	 * @return the model object item with the given key, or null if there is no registered model object item with such
	 * a key.
	 */
	I retrieve(K key);
	
	/**
	 * Retrieves all of the model object items registered with this StorageManager.
	 * 
	 * @return all of the model object items registered with this StorageManager.
	 */
	Collection<I> retrieveAll();
	
	/**
	 * Determines whether this StorageManager manages the given model object item.
	 * 
	 * @param item model object item to being queries about
	 * @return true if this StorageManager manages the given item, false if it does not.
	 */
	boolean manages(I item);
	
	/**
	 * Determines whether the this StorageManager manages an item with the given key.
	 * 
	 * @param key key of the model object item being queried about
	 * @return true if this StorageManager manages an item with the given key, false if it does not.
	 */
	boolean managesItemWithKey(K key);
	
	/**
	 * Updates the given item as identified by its key. Calling this method is necessary to accommodate storage
	 * strategies that do not enhance/decorate the Java beans to watch for changes (i.e. my JSON managers as opposed
	 * to JPA).  
	 * 
	 * @param item model object item to be updated
	 * @return the model object item previously stored (or the same object as given if the references are the same)
	 */
	I update(I item);
	
	/**
	 * Unregisters (removes) the given model object item from this StorageManager.
	 * 
	 * @param item model object item to be removed
	 * @return true if the item was managed and removed, false if it was not managed.
	 */
	boolean unregister(I item);
	
	/**
	 * Unregisters (removes) the model object item with the given key from this StorageManager.
	 * 
	 * @param key key of the model object item to be removed
	 * @return the model object item that was unregistered if it had been managed, or null if there was no managed
	 * item with the given key.
	 */
	I unregisterItemWithKey(K key);
	
}
