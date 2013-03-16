package kpbinc.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Defines pre and post hooks for each method in the standard List interface. Intended to be used with the DecoratedList
 * class. Defined the pre and post hooks in a separate interface so that implementations could be composed together in
 * a chain. 
 * 
 * All of the pre-methods have a boolean return type indicating whether the list operation should be performed or not.
 * The post-methods are called with the result from executing the list operation. 
 * 
 * @author Kevin Black
 *
 * @param <E> list element type
 */
public interface ListDecorator<E> {

	//= Interface ======================================================================================================
	
	//- Pre/Post add(T element) ----------------------------------------------------------------------------------------
	
	/**
	 * @return true if the element should be added to the list, false if it should not
	 */
	boolean preAdd(List<E> list, E element);
	
	void postAdd(List<E> list, E element, boolean added);
	
	//- Pre/Post add(int index, T element) -----------------------------------------------------------------------------
	
	boolean preAdd(List<E> list, int index, E element);
	
	void postAdd(List<E> list, int index, E element);
	
	//- Pre/Post addAll(Collection<? extends T> collection) ------------------------------------------------------------
	
	boolean preAddAll(List<E> list, Collection<? extends E> collection);
	
	void postAddAll(List<E> list, Collection<? extends E> collection, boolean added);
	
	//- Pre/Post addAll(int index, Collection<? extends T> collection) -------------------------------------------------
	
	boolean preAddAll(List<E> list, int index, Collection<? extends E> collection);
	
	void postAddAll(List<E> list, int index, Collection<? extends E> collection, boolean added);
	
	//- Pre/Post clear() -----------------------------------------------------------------------------------------------
	
	boolean preClear(List<E> list);
	
	void postClear(List<E> list);
	
	//- Pre/Post contains(Object object) -------------------------------------------------------------------------------

	boolean preContains(List<E> list, Object object);
	
	void postContains(List<E> list, Object object, boolean contained);
	
	//- Pre/Post containsAll(Collection<?> collection) -----------------------------------------------------------------
	
	boolean preContains(List<E> list, Collection<?> collection);
	
	void postContains(List<E> list, Collection<?> collection, boolean contained);
	
	//- Pre/Post get(int index) ----------------------------------------------------------------------------------------
	
	boolean preGet(List<E> list, int index);
	
	void postGet(List<E> list, int index, E gotten);
	
	//- Pre/Post indexOf(Object object) --------------------------------------------------------------------------------
	
	boolean preIndexOf(List<E> list, Object object);
	
	void postIndexOf(List<E> list, Object object, int index);
	
	//- Pre/Post isEmpty() ---------------------------------------------------------------------------------------------
	
	boolean preIsEmpty(List<E> list);
	
	void postIsEmpty(List<E> list, boolean isEmpty);
	
	//- Pre/Post interator() -------------------------------------------------------------------------------------------
	
	boolean preIterator(List<E> list);
	
	void postIterator(List<E> list, Iterator<E> iterator);
	
	//- Pre/Post lastIndexOf(Object object) ----------------------------------------------------------------------------
	
	boolean preLastIndexOf(List<E> list, Object object);
	
	void postLastIndexOf(List<E> list, Object object, int lastIndex);
	
	//- Pre/Post listIterator() ----------------------------------------------------------------------------------------
	
	boolean preListIterator(List<E> list);
	
	void postListIterator(List<E> list, ListIterator<E> listIterator);
	
	//- Pre/Post listIterator(int index) -------------------------------------------------------------------------------
	
	boolean preListIterator(List<E> list, int index);
	
	void postListIterator(List<E> list, int index, ListIterator<E> listIterator);
	
	//- Pre/Post remove(Object object) ---------------------------------------------------------------------------------
	
	boolean preRemove(List<E> list, Object object);
	
	void postRemove(List<E> list, Object object, boolean removed);
	
	//- Pre/Post remove(int index) -------------------------------------------------------------------------------------
	
	boolean preRemove(List<E> list, int index);
	
	void postRemove(List<E> list, int index, E removed);
	
	//- Pre/Post removeAll(Collection<?> collection) -------------------------------------------------------------------
	
	boolean preRemoveAll(List<E> list, Collection<?> collection);
	
	void postRemoveAll(List<E> list, Collection<?> collection, boolean removed);
	
	//- Pre/Post retainAll(Collection<?> collection) -------------------------------------------------------------------
	
	boolean preRetainAll(List<E> list, Collection<?> collection);
	
	void postRetainAll(List<E> list, Collection<?> collection, boolean retained);
	
	//- Pre/Post set(int index, T element) -----------------------------------------------------------------------------
	
	boolean preSet(List<E> list, int index, E element);
	
	void postSet(List<E> list, int index, E element, E previous);
	
	//- Pre/Post size() ------------------------------------------------------------------------------------------------
	
	boolean preSize(List<E> list);
	
	void postSize(List<E> list, int size);
	
	//- Pre/Post subList(int fromIndex, int toIndex) -------------------------------------------------------------------
	
	boolean preSubList(List<E> list, int fromIndex, int toIndex);
	
	void postSubList(List<E> list, int fromIndex, int toIndex, List<E> subList);
	
	//- Pre/Post toArray() ---------------------------------------------------------------------------------------------
	
	boolean preToArray(List<E> list);
	
	void postToArray(List<E> list, Object[] items);
	
	//- Pre/Post toArray(T[] array) ------------------------------------------------------------------------------------
	
	<T> boolean preToArray(List<E> list, T[] array);
	
	<T> void postToArray(List<E> list, T[] array, T[] resultArray);
	
}
