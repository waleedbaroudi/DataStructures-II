package code;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import given.iMap;
import given.iBinarySearchTree;

/*
 * AUTHOR: Walid Baroudi
 * Computer Engineering student at Koc University
 * E-mail: wbaroudi18@ku.edu.tr
 * LinkedIn: linkedin.com/in/walid-baroudi
 * Github: github.com/waleedbaroudi
 */

public class BinarySearchTree<Key, Value> implements iBinarySearchTree<Key, Value>, iMap<Key, Value> {

	private BinaryTreeNode<Key, Value> root;
	private Comparator<Key> comparator;
	private int size;

	public BinarySearchTree() {
		root = new BinaryTreeNode<>();
		size = 0;
		comparator = Comparator.comparing(Key::hashCode);
	}

	@Override
	public Value get(Key k) {
		BinaryTreeNode<Key, Value> current = root;
		while (!current.isDummy()) {
			int comp = getComparator().compare(k, current.getKey());
			if (comp == 0)
				return current.getValue();
			else if (comp > 0)
				current = current.getRight();
			else
				current = current.getLeft();
		}
		return null;
	}

	@Override
	public Value put(Key k, Value v) {
		BinaryTreeNode<Key, Value> current = root;
		BinaryTreeNode<Key, Value> prev = null;
		while (!current.isDummy()) {
			int comp = this.getComparator().compare(k, current.getKey());
			if (comp == 0) {
				Value val = current.getValue();
				current.setValue(v);
				return val;
			} else if (comp > 0) {
				prev = current;
				current = current.getRight();
			} else {
				prev = current;
				current = current.getLeft();
			}
		}
		current.makeInternal(k, v);
		current.setParent(prev);
		size++;
		return null;
	}

	@Override
	public Value remove(Key k) {
		BinaryTreeNode<Key, Value> current = getNode(k);
		if (current == null) {
			return null;
		} else {
			size--;
			if (current.getLeft().isDummy() && current.getRight().isDummy()) {
				Value val = current.getValue();
				current.makeDummy();
				return val;
			}
			if (!current.getLeft().isDummy() && current.getRight().isDummy()) {
				Value val = current.getValue();
				replace(current, current.getLeft());
				return val;
			}
			if (current.getLeft().isDummy() && !current.getRight().isDummy()) {
				Value val = current.getValue();
				replace(current, current.getRight());
				return val;
			} else {
				BinaryTreeNode<Key, Value> replace = findPredecessor(current);
				Value val = current.getValue();
				current.setValue(replace.getValue());
				current.setKey(replace.getKey());
				////////////////
				if (replace.getLeft().isDummy()) {
					replace.makeDummy();
				} else if (!replace.getLeft().isDummy()) {
					replace(replace, replace.getLeft());
				}
				////////////////
				return val;
			}
		}
	}

	public BinaryTreeNode<Key, Value> findPredecessor(BinaryTreeNode<Key, Value> node) {
		List<BinaryTreeNode<Key, Value>> nodes = getNodesInOrder();
		for (int i = 0; i < nodes.size() - 1; i++) {
			if (node == nodes.get(i)) {
				return nodes.get(i - 1);
			}
		}
		return null;
	}

	public void replace(BinaryTreeNode<Key, Value> from, BinaryTreeNode<Key, Value> to) {
		if (from == root) {
			to.setParent(null);
			root = to;
			return;
		}
		if (from.isLeftChild()) {
			from.getParent().setLeft(to);
		} else if (from.isRightChild()) {
			from.getParent().setRight(to);
		}
		to.setParent(from.getParent());
	}

	@Override
	public Iterable<Key> keySet() {
		return getNodesInOrder().stream().map(x -> (x.getKey())).collect(Collectors.toSet());
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public BinaryTreeNode<Key, Value> getRoot() {
		return root;
	}

	@Override
	public BinaryTreeNode<Key, Value> getParent(BinaryTreeNode<Key, Value> node) {
		return node.getParent();
	}

	@Override
	public boolean isInternal(BinaryTreeNode<Key, Value> node) {
		return node.isInternal();
	}

	@Override
	public boolean isExternal(BinaryTreeNode<Key, Value> node) {
		if (node == null)
			return true;
		return node.isDummy();
	}

	@Override
	public boolean isRoot(BinaryTreeNode<Key, Value> node) {
		return node.isRoot();
	}

	@Override
	public BinaryTreeNode<Key, Value> getNode(Key k) {
		if (isEmpty())
			return null;
		BinaryTreeNode<Key, Value> current = root;
		while (current.isInternal()) {
			int comp = getComparator().compare(k, current.getKey());
			if (comp == 0) {
				return current;
			} else if (comp > 0)
				current = current.getRight();
			else
				current = current.getLeft();
		}
		return null;
	}

	@Override
	public Value getValue(Key k) {
		return get(k);
	}

	@Override
	public BinaryTreeNode<Key, Value> getLeftChild(BinaryTreeNode<Key, Value> node) {
		return node.getLeft();
	}

	@Override
	public BinaryTreeNode<Key, Value> getRightChild(BinaryTreeNode<Key, Value> node) {
		return node.getRight();
	}

	@Override
	public BinaryTreeNode<Key, Value> sibling(BinaryTreeNode<Key, Value> node) {
		if (getParent(node) == null)
			return null;
		return node.isLeftChild() ? node.getParent().getRight() : node.getParent().getLeft();
	}

	@Override
	public boolean isLeftChild(BinaryTreeNode<Key, Value> node) {
		return node.isLeftChild();
	}

	@Override
	public boolean isRightChild(BinaryTreeNode<Key, Value> node) {
		return node.isRightChild();
	}

	@Override
	public List<BinaryTreeNode<Key, Value>> getNodesInOrder() {
		List<BinaryTreeNode<Key, Value>> nodes = new ArrayList<>();
		if (size == 0)
			return nodes;
		fillListInOrder(nodes, getRoot());
		return nodes;
	}

	public void fillListInOrder(List<BinaryTreeNode<Key, Value>> nodes, BinaryTreeNode<Key, Value> node) {
		if (node.getLeft().isInternal())
			fillListInOrder(nodes, node.getLeft());
		nodes.add(node);
		if (node.getRight().isInternal())
			fillListInOrder(nodes, node.getRight());
	}

	@Override
	public void setComparator(Comparator<Key> C) {
		comparator = C;
	}

	@Override
	public Comparator<Key> getComparator() {
		return comparator;
	}

	@Override
	public BinaryTreeNode<Key, Value> ceiling(Key k) {
		List<BinaryTreeNode<Key, Value>> nodes = getNodesInOrder();
		if (nodes.isEmpty()) {
			return null;
		}
		int comp = getComparator().compare(nodes.get(nodes.size() - 1).getKey(), k);
		if (comp < 0)
			return null;
		for (int i = 0; i < nodes.size() - 1; i++) {
			comp = getComparator().compare(nodes.get(i).getKey(), k);
			if (comp >= 0)
				return nodes.get(i);
		}
		return nodes.get(0);
	}

	@Override
	public BinaryTreeNode<Key, Value> floor(Key k) {
		List<BinaryTreeNode<Key, Value>> nodes = getNodesInOrder();
		if (nodes.isEmpty()) {
			return null;
		}
		int comp = getComparator().compare(nodes.get(0).getKey(), k);
		if (comp > 0)
			return null;
		for (int i = 1; i < nodes.size() - 1; i++) {
			comp = getComparator().compare(nodes.get(i).getKey(), k);
			if (comp == 0)
				return nodes.get(i);
			if (comp > 0)
				return nodes.get(i - 1);
		}
		return nodes.get(nodes.size() - 1);
	}
}
