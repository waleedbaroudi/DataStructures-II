package code;

import given.Entry;

/*
 * AUTHOR: Walid Baroudi
 * Computer Engineering student at Koc University
 * E-mail: wbaroudi18@ku.edu.tr
 * LinkedIn: linkedin.com/in/walid-baroudi
 * Github: github.com/waleedbaroudi
 */

public class BinaryTreeNode<Key, Value> extends Entry<Key, Value> {

	private BinaryTreeNode<Key, Value> parent, left, right;

	public BinaryTreeNode(Key k, Value v) {
		super(k, v);
		parent = null;
		left = new BinaryTreeNode<>();
		left.setParent(this);
		right = new BinaryTreeNode<>();
		right.setParent(this);

	}

	public BinaryTreeNode() {
		super();
	}

	public void setParent(BinaryTreeNode<Key, Value> parent) {
		this.parent = parent;
	}

	public boolean isDummy() {
		return left == null && right == null && k == null && v == null;
	}

	public boolean isInternal() {
		return (left != null) || (right != null);
	}

	public boolean isRoot() {
		return parent == null;
	}

	public BinaryTreeNode<Key, Value> getLeft() {
		return left;
	}

	public void setLeft(BinaryTreeNode<Key, Value> left) {
		this.left = left;
		left.setParent(this);
	}

	public BinaryTreeNode<Key, Value> getRight() {
		return right;
	}

	public void setRight(BinaryTreeNode<Key, Value> right) {
		this.right = right;
		right.setParent(this);
	}

	public BinaryTreeNode<Key, Value> getParent() {
		return parent;
	}

	public void makeInternal(Key k, Value v) {
		if (isInternal())
			return;
		setKey(k);
		setValue(v);
		BinaryTreeNode<Key, Value> leftDummy = new BinaryTreeNode<>();
		leftDummy.setParent(this);
		BinaryTreeNode<Key, Value> rightDummy = new BinaryTreeNode<>();
		rightDummy.setParent(this);
		setLeft(leftDummy);
		setRight(rightDummy);
	}

	public void makeDummy() {
		if (isDummy())
			return;
		left = null;
		right = null;
		k = null;
		v = null;
	}

	public boolean isLeftChild() {
		if (getParent() == null)
			return false;
		return getParent().getLeft() == this;
	}

	public boolean isRightChild() {
		if (getParent() == null)
			return false;
		return getParent().getRight() == this;
	}

	public boolean hasRight() {
		if (isDummy())
			return false;
		return getRight().isInternal();
	}

	public boolean hasLeft() {
		if (isDummy())
			return false;
		return getLeft().isInternal();
	}

}
