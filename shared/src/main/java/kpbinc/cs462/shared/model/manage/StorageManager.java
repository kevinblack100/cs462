package kpbinc.cs462.shared.model.manage;

import java.util.Collection;

import kpbinc.cs462.shared.model.aspect.HasID;

/**
 * Common interface model object storage management, whether in-memory, file-persistent, or DB persistent.
 * 
 * @author Kevin Black
 * 
 * @param <I> ID type (primary key)
 * @param <T> model object item type
 */
public interface StorageManager<I, T extends HasID<I>> {

	/**
	 * Registers the given model object item with this StorageManager. Will not store null values. If the given item
	 * does not already have an ID then will assign an ID to it.
	 * 
	 * @param item model object to be registered
	 * @return true if the item was registered, false if the item was not registered.
	 */
	boolean register(T item);
	
	/**
	 * Retrieves the model object item with the given ID.
	 * 
	 * @param id ID of the model object item to be retrieved.
	 * @return the model object item with the given ID, or null if there is no registered model object item with such
	 * an ID.
	 */
	T retrieve(I id);
	
	/**
	 * Retrieves all of the model object items registered with this StorageManager.
	 * 
	 * @return all of the model object items registered with this StorageManager.
	 */
	Collection<T> retrieveAll();
	
	/**
	 * Determines whether this StorageManager manages the given model object item.
	 * 
	 * @param item model object item to being queries about
	 * @return true if this StorageManager manages the given item, false if it does not.
	 */
	boolean manages(T item);
	
	/**
	 * Determines whether the this StorageManager manages an item with the given ID.
	 * 
	 * @param id ID of the model object item being queried about
	 * @return true if this StorageManager manages an item with the given ID, false if it does not.
	 */
	boolean managesItemWithID(I id);
	
	/**
	 * Updates the given item as identified by its ID. Calling this method is necessary to accommodate storage
	 * strategies that do not enhance/decorate the Java beans to watch for changes (i.e. my JSON managers as opposed
	 * to JPA).  
	 * 
	 * @param item model object item to be updated
	 * @return the model object item previously stored (or the same object as given if the references are the same)
	 */
	T update(T item);
	
	/**
	 * Unregisters (removes) the given model object item from this StorageManager.
	 * 
	 * @param item model object item to be removed
	 * @return true if the item was managed and removed, false if it was not managed.
	 */
	boolean unregister(T item);
	
	/**
	 * Unregisters (removes) the model object item with the given ID from this StorageManager.
	 * 
	 * @param id ID of the model object item to be removed
	 * @return the model object item that was unregistered if it had been managed, or null if there was no managed
	 * item with the given ID.
	 */
	T unregisterItemWithID(I id);
	
}
