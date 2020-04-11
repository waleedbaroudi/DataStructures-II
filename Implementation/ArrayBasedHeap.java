package code;

import java.util.ArrayList;
import java.util.Comparator;

import given.Entry;
import given.iAdaptablePriorityQueue;

/*
 * AUTHOR: Walid Baroudi
 * Computer Engineering student at Koc University
 * E-mail: wbaroudi18@ku.edu.tr
 * LinkedIn: linkedin.com/in/walid-baroudi
 * Github: github.com/waleedbaroudi
 */

public class ArrayBasedHeap<Key, Value> implements iAdaptablePriorityQueue<Key, Value> {

	protected ArrayList<Entry<Key, Value>> nodes;
	Comparator<Key> comparator;

	public ArrayBasedHeap() {
		nodes = new ArrayList<>();
		comparator = Comparator.comparing(Key::hashCode);
	}

	@Override
	public int size() {
		return nodes.size();
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public void setComparator(Comparator<Key> C) {
		this.comparator = C;
	}

	@Override
	public Comparator<Key> getComparator() {
		return comparator;
	}

	@Override
	public void insert(Key k, Value v) {
		nodes.add(new Entry<Key, Value>(k, v));
		upHeap(size() - 1);
	}

	@Override
	public Entry<Key, Value> pop() {
		if (isEmpty())
			return null;
		Entry<Key, Value> removed = nodes.get(0);
		nodes.set(0, nodes.get(size() - 1));
		nodes.remove(size() - 1);
		downHeap(0);
		return removed;
	}

	@Override
	public Entry<Key, Value> top() {
		if (isEmpty())
			return null;
		return nodes.get(0);
	}

	@Override
	public Value remove(Key k) {
		Entry<Key, Value> current = null;
		for (Entry<Key, Value> e : nodes) {
			if (getComparator().compare(k, e.getKey()) == 0) {
				current = e;
				break;
			}
		}
		if (current == null) {
			return null;
		}
		Value removed = current.getValue();
		int removedInd = nodes.indexOf(current);
		Key oldK = k;
		Key newK = nodes.get(size() - 1).getKey();
		nodes.set(removedInd, nodes.get(size() - 1));
		nodes.remove(size() - 1);
		restoreOrder(oldK, newK, removedInd);
		return removed;

	}

	public void upHeap(int index) {
		if (index == 0)
			return;
		Key self = nodes.get(index).getKey();
		Key parent = nodes.get((index - 1) / 2).getKey();
		int comp = getComparator().compare(self, parent);
		if (comp > 0)
			return;
		swap(index, (index - 1) / 2);
		upHeap((index - 1) / 2);
	}

	public void downHeap(int index) {
		if ((index * 2 + 1) > size() - 1)
			return;
		int childInd;
		if ((index * 2 + 2) > size() - 1)
			childInd = index * 2 + 1;
		else
			childInd = getComparator().compare(nodes.get(index * 2 + 1).getKey(), nodes.get(index * 2 + 2).getKey()) > 0
					? ((index * 2) + 2)
					: ((index * 2) + 1);
		Key self = nodes.get(index).getKey();

		int comp = getComparator().compare(self, nodes.get(childInd).getKey());
		if (comp < 0)
			return;
		swap(index, childInd);
		downHeap(childInd);
	}

	public void swap(int first, int second) {
		Entry<Key, Value> temp = nodes.get(second);
		nodes.set(second, nodes.get(first));
		nodes.set(first, temp);
	}

	@Override
	public Key replaceKey(Entry<Key, Value> entry, Key k) {
		for (Entry<Key, Value> e : nodes) {
			if (e.getValue().equals(entry.getValue()) && (getComparator().compare(entry.getKey(), e.getKey()) == 0)) {
				Key key = e.getKey();
				e.setKey(k);
				restoreOrder(key, k, nodes.indexOf(e));
				return key;
			}
		}
		return null;
	}

	@Override
	public Key replaceKey(Value v, Key k) {
		for (Entry<Key, Value> e : nodes) {
			if (e.getValue().equals(v)) {
				Key key = e.getKey();
				e.setKey(k);
				restoreOrder(key, k, nodes.indexOf(e));
				return key;
			}
		}
		return null;
	}

	public void restoreOrder(Key oldKey, Key newKey, int index) {
		int comp = getComparator().compare(oldKey, newKey);
		if (comp == 0)
			return;
		else if (comp > 0)
			upHeap(index);
		else
			downHeap(index);
	}

	@Override
	public Value replaceValue(Entry<Key, Value> entry, Value v) {
		for (Entry<Key, Value> e : nodes) {
			if (e.getValue().equals(entry.getValue()) && (getComparator().compare(entry.getKey(), e.getKey()) == 0)) {
				Value val = e.getValue();
				e.setValue(v);
				return val;
			}
		}
		return null;
	}

}
