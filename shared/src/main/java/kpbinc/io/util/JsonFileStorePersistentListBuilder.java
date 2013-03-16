package kpbinc.io.util;

import java.util.ArrayList;
import java.util.List;

import kpbinc.util.DecoratedList;
import kpbinc.util.ListDecorator;

public class JsonFileStorePersistentListBuilder {

	//= Interface ======================================================================================================
	
	/**
	 * Creates a new List that is set to be persisted to the given JsonFileStore.
	 * 
	 * @param fileStore the json file store, configured to properly read and write the stored list type
	 * @return the persistent List
	 */
	public static <E> List<E> buildWithEmptyDelegate(JsonFileStore<List<E>> fileStore) {
		List<E> delegate = new ArrayList<E>();
		List<E> persistentList = buildPersistentList(fileStore, delegate);
		
		return persistentList;
	}

	/**
	 * Creates a persistent List from the given JsonFileStore. Falls back to buildWithEmptyDelegate if reading from the
	 * file store is unsuccessful.
	 * 
	 * @param fileStore the json file store, configured to properly read and write the stored list type
	 * @return the persistent List
	 */
	public static <E> List<E> buildWithDelegateFromFileStore(JsonFileStore<List<E>> fileStore) {
		List<E> delegate = fileStore.read();
		List<E> persistentList = null;
		if (delegate == null) {
			persistentList = buildWithEmptyDelegate(fileStore);
		}
		else {
			persistentList = buildPersistentList(fileStore, delegate);
		}
		return persistentList;
	}
	
	/**
	 * Creates a persistent List from the given delegate List and JsonFileStore and commits the delegate to the file
	 * store.
	 * 
	 * @param fileStore the json file store, configured to properly read and write the stored list type
	 * @param delegate list content to be made persistent
	 * @return the persistent list
	 */
	public static <E> List<E> buildWithDelegate(JsonFileStore<List<E>> fileStore, List<E> delegate) {
		List<E> persistentList = buildPersistentList(fileStore, delegate);
		
		fileStore.commit(delegate);
		
		return persistentList;
	}
	
	
	//= Support ========================================================================================================
	
	private static <E> List<E> buildPersistentList(JsonFileStore<List<E>> fileStore, List<E> delegate) {
		ListDecorator<E> persistentLogic = new JsonFileStorePersistentListDecorator<E>(fileStore);
		List<E> persistentList = new DecoratedList<E>(delegate, persistentLogic);
		
		return persistentList;
	}
	
}
