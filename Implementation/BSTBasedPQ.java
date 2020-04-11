package code;

import given.Entry;
import given.iAdaptablePriorityQueue;

/*
 * AUTHOR: Walid Baroudi
 * Computer Engineering student at Koc University
 * E-mail: wbaroudi18@ku.edu.tr
 * LinkedIn: linkedin.com/in/walid-baroudi
 * Github: github.com/waleedbaroudi
 */

public class BSTBasedPQ<Key, Value> extends BinarySearchTree<Key, Value>
		implements iAdaptablePriorityQueue<Key, Value> {

	@Override
	public void insert(Key k, Value v) {
		super.put(k, v);
	}

	@Override
	public Entry<Key, Value> pop() {
		if (isEmpty())
			return null;
		Entry<Key, Value> min = top();
		Entry<Key, Value> minTemp = new Entry<Key, Value>(min.getKey(), min.getValue());
		remove(min.getKey());
		return minTemp;
	}

	@Override
	public Entry<Key, Value> top() {
		if (isEmpty())
			return null;
		BinaryTreeNode<Key, Value> current = getRoot();
		while (current.hasLeft())
			current = current.getLeft();
		return current;
	}

	@Override
	public Key replaceKey(Entry<Key, Value> entry, Key k) {
		Entry<Key, Value> foundNode = getNode(entry.getKey());
		if (foundNode == null) {
			return null;
		}
		if (!foundNode.getValue().equals(entry.getValue()))
			return null;
		Key key = foundNode.getKey();
		Value val = foundNode.getValue();
		remove(key);
		put(k, val);
		return key;
	}

	@Override
	public Key replaceKey(Value v, Key k) {
		Entry<Key, Value> foundNode = findByValue(v);
		if (foundNode == null) {
			return null;
		}
		Key key = foundNode.getKey();
		Value val = foundNode.getValue();
		remove(key);
		put(k, val);
		return key;
	}

	public Entry<Key, Value> findByValue(Value v) {
		if (isEmpty())
			return null;
		for (Entry<Key, Value> e : getNodesInOrder()) {
			if (e.getValue().equals(v))
				return e;
		}
		return null;
	}

	@Override
	public Value replaceValue(Entry<Key, Value> entry, Value v) {
		Entry<Key, Value> node = getNode(entry.getKey());
		if (node == null) {
			return null;
		}
		if (!node.getValue().equals(entry.getValue()))
			return null;
		Value val = node.getValue();
		node.setValue(v);
		return val;
	}

}
