package kpbinc.io.util;

import java.util.Collection;
import java.util.List;

import kpbinc.util.BaseListDecorator;
import kpbinc.util.logging.GlobalLogUtils;

public class JsonFileStorePersistentListDecorator<E> extends BaseListDecorator<E> {

	//= Member Data ====================================================================================================
	
	private JsonFileStore<List<E>> fileStore;
	
	
	//= Initialization =================================================================================================
	
	public JsonFileStorePersistentListDecorator(JsonFileStore<List<E>> fileStore) {
		GlobalLogUtils.logConstruction(this);
		
		if (fileStore == null) {
			throw new IllegalArgumentException("file store must not be null");
		}
		this.fileStore = fileStore;
	}

	
	//= Interface ======================================================================================================
	
	@Override
	public void postAdd(List<E> list, E element, boolean added) {
		commit(list);
	}
	
	@Override
	public void postAdd(List<E> list, int index, E element) {
		commit(list);
	}
	
	@Override
	public void postAddAll(List<E> list, Collection<? extends E> collection, boolean added) {
		commit(list);		
	}
	
	@Override
	public void postAddAll(List<E> list, int index, Collection<? extends E> collection, boolean added) {
		commit(list);	
	}
	
	@Override
	public void postClear(List<E> list) {
		commit(list);
	}
	
	@Override
	public void postRemove(List<E> list, Object object, boolean removed) {
		commit(list);
	}
	
	@Override
	public void postRemove(List<E> list, int index, E removed) {
		commit(list);
	}
	
	@Override
	public void postRemoveAll(List<E> list, Collection<?> collection, boolean removed) {
		commit(list);
	}
	
	@Override
	public void postRetainAll(List<E> list, Collection<?> collection, boolean retained) {
		commit(list);
	}
	
	@Override
	public void postSet(List<E> list, int index, E element, E previous) {
		commit(list);
	}
	
	
	//= Support ========================================================================================================

	private void commit(List<E> list) {
		fileStore.commit(list);
	}

}
