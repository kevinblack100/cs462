package kpbinc.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class DecoratedList<E> implements List<E> {
	
	//= Member Data ====================================================================================================
	
	private List<E> delegateList;
	private ListDecorator<E> decorator;
	
	
	//= Initialization =================================================================================================
	
	public DecoratedList(List<E> delegateList, ListDecorator<E> decorator) {
		if (delegateList == null) {
			throw new IllegalArgumentException("delegate list must not be null");
		}
		
		this.delegateList = delegateList;
		
		if (decorator == null) {
			throw new IllegalArgumentException("decorator must not be null");
		}
		
		this.decorator = decorator;
	}
	
	
	//= Interface ======================================================================================================

	@Override
	public boolean equals(Object object) {
		boolean result = false;
		if (   object != null
			&& object instanceof List) {
			result = delegateList.equals(object);
		}
		return result;
	}
	
	@Override
	public int hashCode() {
		int hash = delegateList.hashCode();
		return hash;
	}
	
	@Override
	public boolean add(E element) {
		boolean result = false;
		boolean doOperation = decorator.preAdd(delegateList, element);
		if (doOperation) {
			result = delegateList.add(element);
			decorator.postAdd(delegateList, element, result);
		}
		return result;
	}

	@Override
	public void add(int index, E element) {
		boolean doOperation = decorator.preAdd(delegateList, index, element);
		if (doOperation) {
			delegateList.add(index, element);
			decorator.postAdd(delegateList, index, element);
		}
	}

	@Override
	public boolean addAll(Collection<? extends E> collection) {
		boolean result = false;
		boolean doOperation = decorator.preAddAll(delegateList, collection);
		if (doOperation) {
			result = delegateList.addAll(collection);
			decorator.postAddAll(delegateList, collection, result);
		}
		return result;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> collection) {
		boolean result = false;
		boolean doOperation = decorator.preAddAll(delegateList, index, collection);
		if (doOperation) {
			result = delegateList.addAll(index, collection);
			decorator.postAddAll(delegateList, index, collection, result);
		}
		return result;
	}

	@Override
	public void clear() {
		boolean doOperation = decorator.preClear(delegateList);
		if (doOperation) {
			delegateList.clear();
			decorator.postClear(delegateList);
		}
	}

	@Override
	public boolean contains(Object object) {
		boolean result = false;
		boolean doOperation = decorator.preContains(delegateList, object);
		if (doOperation) {
			result = delegateList.contains(object);
			decorator.postContains(delegateList, object, result);
		}
		return result;
	}

	@Override
	public boolean containsAll(Collection<?> collection) {
		boolean result = false;
		boolean doOperation = decorator.preContains(delegateList, collection);
		if (doOperation) {
			result = delegateList.contains(collection);
			decorator.postContains(delegateList, collection, result);
		}
		return result;
	}

	@Override
	public E get(int index) {
		E result = null;
		boolean doOperation = decorator.preGet(delegateList, index);
		if (doOperation) {
			result = delegateList.get(index);
			decorator.postGet(delegateList, index, result);
		}
		return result;
	}

	@Override
	public int indexOf(Object object) {
		int result = -1;
		boolean doOperation = decorator.preIndexOf(delegateList, object);
		if (doOperation) {
			result = delegateList.indexOf(object);
			decorator.postIndexOf(delegateList, object, result);
		}
		return result;
	}

	@Override
	public boolean isEmpty() {
		boolean result = false;
		boolean doOperation = decorator.preIsEmpty(delegateList);
		if (doOperation) {
			result = delegateList.isEmpty();
			decorator.postIsEmpty(delegateList, result);
		}
		return result;
	}

	@Override
	public Iterator<E> iterator() {
		Iterator<E> result = null;
		boolean doOperation = decorator.preIterator(delegateList);
		if (doOperation) {
			result = delegateList.iterator();
			decorator.postIterator(delegateList, result);
		}
		return result;
	}

	@Override
	public int lastIndexOf(Object object) {
		int result = -1;
		boolean doOperation = decorator.preLastIndexOf(delegateList, object);
		if (doOperation) {
			result = delegateList.lastIndexOf(object);
			decorator.postLastIndexOf(delegateList, object, result);
		}
		return result;
	}

	@Override
	public ListIterator<E> listIterator() {
		ListIterator<E> result = null;
		boolean doOperation = decorator.preListIterator(delegateList);
		if (doOperation) {
			result = delegateList.listIterator();
			decorator.postListIterator(delegateList, result);
		}
		return result;
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		ListIterator<E> result = null;
		boolean doOperation = decorator.preListIterator(delegateList, index);
		if (doOperation) {
			result = delegateList.listIterator(index);
			decorator.postListIterator(delegateList, index, result);
		}
		return result;
	}

	@Override
	public boolean remove(Object object) {
		boolean result = false;
		boolean doOperation = decorator.preRemove(delegateList, object);
		if (doOperation) {
			result = delegateList.remove(object);
			decorator.postRemove(delegateList, object, result);
		}
		return result;
	}

	@Override
	public E remove(int index) {
		E result = null;
		boolean doOperation = decorator.preRemove(delegateList, index);
		if (doOperation) {
			result = delegateList.remove(index);
			decorator.postRemove(delegateList, index, result);
		}
		return result;
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		boolean result = false;
		boolean doOperation = decorator.preRemoveAll(delegateList, collection);
		if (doOperation) {
			result = delegateList.removeAll(collection);
			decorator.postRemoveAll(delegateList, collection, result);
		}
		return result;
	}

	@Override
	public boolean retainAll(Collection<?> collection) {
		boolean result = false;
		boolean doOperation = decorator.preRetainAll(delegateList, collection);
		if (doOperation) {
			result = delegateList.retainAll(collection);
			decorator.postRetainAll(delegateList, collection, result);
		}
		return result;
	}

	@Override
	public E set(int index, E element) {
		E result = null;
		boolean doOperation = decorator.preSet(delegateList, index, element);
		if (doOperation) {
			result = delegateList.set(index, element);
			decorator.postSet(delegateList, index, element, result);
		}
		return result;
	}

	@Override
	public int size() {
		int result = -1;
		boolean doOperation = decorator.preSize(delegateList);
		if (doOperation) {
			result = delegateList.size();
			decorator.postSize(delegateList, result);
		}
		return result;
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		List<E> result = null;
		boolean doOperation = decorator.preSubList(delegateList, fromIndex, toIndex);
		if (doOperation) {
			result = delegateList.subList(fromIndex, toIndex);
			decorator.postSubList(delegateList, fromIndex, toIndex, result);
		}
		return result;
	}

	@Override
	public Object[] toArray() {
		Object[] result = null;
		boolean doOperation = decorator.preToArray(delegateList);
		if (doOperation) {
			result = delegateList.toArray();
			decorator.postToArray(delegateList, result);
		}
		return result;
	}

	@Override
	public <T> T[] toArray(T[] array) {
		T[] result = null;
		boolean doOperation = decorator.preToArray(delegateList, array);
		if (doOperation) {
			result = delegateList.toArray(array);
			decorator.postToArray(delegateList, array, result);
		}
		return result;
	}

}
