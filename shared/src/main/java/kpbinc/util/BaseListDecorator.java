package kpbinc.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import kpbinc.util.logging.GlobalLogUtils;

public class BaseListDecorator<E> implements ListDecorator<E> {

	//= Initialization =================================================================================================
	
	public BaseListDecorator() {
		GlobalLogUtils.logConstruction(this);
	}
	
	
	//= Interface ======================================================================================================

	@Override
	public boolean preAdd(List<E> list, E element) {
		return true;
	}

	@Override
	public void postAdd(List<E> list, E element, boolean added) {
		// do nothing
	}

	@Override
	public boolean preAdd(List<E> list, int index, E element) {
		return true;
	}

	@Override
	public void postAdd(List<E> list, int index, E element) {
		// do nothing
	}

	@Override
	public boolean preAddAll(List<E> list, Collection<? extends E> collection) {
		return true;
	}

	@Override
	public void postAddAll(List<E> list, Collection<? extends E> collection, boolean added) {
		// do nothing		
	}

	@Override
	public boolean preAddAll(List<E> list, int index, Collection<? extends E> collection) {
		return true;
	}

	@Override
	public void postAddAll(List<E> list, int index, Collection<? extends E> collection, boolean added) {
		// do nothing		
	}

	@Override
	public boolean preClear(List<E> list) {
		return true;
	}

	@Override
	public void postClear(List<E> list) {
		// do nothing		
	}

	@Override
	public boolean preContains(List<E> list, Object object) {
		return true;
	}

	@Override
	public void postContains(List<E> list, Object object, boolean contained) {
		// do nothing
	}

	@Override
	public boolean preContains(List<E> list, Collection<?> collection) {
		return true;
	}

	@Override
	public void postContains(List<E> list, Collection<?> collection, boolean contained) {
		// do nothing
	}

	@Override
	public boolean preGet(List<E> list, int index) {
		return true;
	}

	@Override
	public void postGet(List<E> list, int index, E gotten) {
		// do nothing
	}

	@Override
	public boolean preIndexOf(List<E> list, Object object) {
		return true;
	}

	@Override
	public void postIndexOf(List<E> list, Object object, int index) {
		// do nothing
	}

	@Override
	public boolean preIsEmpty(List<E> list) {
		return true;
	}

	@Override
	public void postIsEmpty(List<E> list, boolean isEmpty) {
		// do nothing		
	}

	@Override
	public boolean preIterator(List<E> list) {
		return true;
	}

	@Override
	public void postIterator(List<E> list, Iterator<E> iterator) {
		// do nothing		
	}

	@Override
	public boolean preLastIndexOf(List<E> list, Object object) {
		return true;
	}

	@Override
	public void postLastIndexOf(List<E> list, Object object, int lastIndex) {
		// do nothing
	}

	@Override
	public boolean preListIterator(List<E> list) {
		return true;
	}

	@Override
	public void postListIterator(List<E> list, ListIterator<E> listIterator) {
		// do nothing
	}

	@Override
	public boolean preListIterator(List<E> list, int index) {
		return true;
	}

	@Override
	public void postListIterator(List<E> list, int index, ListIterator<E> listIterator) {
		// do nothing
	}

	@Override
	public boolean preRemove(List<E> list, Object object) {
		return true;
	}

	@Override
	public void postRemove(List<E> list, Object object, boolean removed) {
		// do nothing
	}

	@Override
	public boolean preRemove(List<E> list, int index) {
		return true;
	}

	@Override
	public void postRemove(List<E> list, int index, E removed) {
		// do nothing
	}

	@Override
	public boolean preRemoveAll(List<E> list, Collection<?> collection) {
		return true;
	}

	@Override
	public void postRemoveAll(List<E> list, Collection<?> collection, boolean removed) {
		// do nothing
	}

	@Override
	public boolean preRetainAll(List<E> list, Collection<?> collection) {
		return true;
	}

	@Override
	public void postRetainAll(List<E> list, Collection<?> collection, boolean retained) {
		// do nothing
	}

	@Override
	public boolean preSet(List<E> list, int index, E element) {
		return true;
	}

	@Override
	public void postSet(List<E> list, int index, E element, E previous) {
		// do nothing
	}

	@Override
	public boolean preSize(List<E> list) {
		return true;
	}

	@Override
	public void postSize(List<E> list, int size) {
		// do nothing
	}

	@Override
	public boolean preSubList(List<E> list, int fromIndex, int toIndex) {
		return true;
	}

	@Override
	public void postSubList(List<E> list, int fromIndex, int toIndex, List<E> subList) {
		// do nothing
	}

	@Override
	public boolean preToArray(List<E> list) {
		return true;
	}

	@Override
	public void postToArray(List<E> list, Object[] items) {
		// do nothing
	}

	@Override
	public <T> boolean preToArray(List<E> list, T[] array) {
		return true;
	}

	@Override
	public <T> void postToArray(List<E> list, T[] array, T[] resultArray) {
		// do nothing
	}

}
