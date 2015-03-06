/**
 * 
 * RBTree
 * 
 * An implementation of a Red Black Tree with non-negative, distinct integer
 * keys and values
 * 
 */

public class RBTree {

	// the sentinel is a dummy node at the top of the tree.
	// the sentinel's left child is the "real" root of the tree.
	private RBNode sentinel = new RBNode(Integer.MAX_VALUE, "",
			RBNode.BLACK_COLOUR);

	private RBNode nullNode = new RBNode(Integer.MIN_VALUE, "",
			RBNode.BLACK_COLOUR);
	private int tSize = 0;
	private RBNode tMin = nullNode;
	private RBNode tMax = nullNode;

	/**
	 * public boolean empty()
	 * 
	 * returns true if and only if the tree is empty
	 * 
	 */
	public boolean empty() {
		return (this.tSize == 0);
	}

	/**
	 * public String search(int k)
	 * 
	 * returns the value of an item with key k if it exists in the tree
	 * otherwise, returns null
	 */
	public String search(int k) {
		RBNode x = this.sentinel;
		while (!x.equals(nullNode) && x.getKey() != k) {
			if (k < x.getKey())
				x = x.getLeftChild();
			else
				x = x.getRightChild();
		}
		if (x.equals(nullNode))
			return null;
		else
			return x.getValue();
	}

	/**
	 * public int insert(int k, String v)
	 * 
	 * inserts an item with key k and value v to the red black tree. the tree
	 * must remain valid (keep its invariants). returns the number of color
	 * switches, or 0 if no color switches were necessary. returns -1 if an item
	 * with key k already exists in the tree.
	 */
	public int insert(int k, String v) {
		RBNode toInsert = new RBNode(k, v, 1);
		if (this.search(toInsert.getKey()) != null)
			return -1;
		if (this.empty()) {
			sentinel.setLeftChild(toInsert);
			toInsert.setParent(sentinel);
			toInsert.setColour(0);
			tMax = toInsert;
			tMin = toInsert;
			tSize = 1;
			return 1;
		}
		treeInsert(toInsert);
		return 0;
	}

	public void treeInsert(RBNode toInsert) {
		// Insert to a non-empty tree
		RBNode prev = null;
		RBNode current = this.sentinel.getLeftChild();
		while (!current.equals(nullNode)) {
			prev = current;
			if (toInsert.getKey() < current.getKey())
				current = current.getLeftChild();
			else
				current = current.getRightChild();
		}
		
		toInsert.setParent(prev);
		
		if (toInsert.getKey() < prev.getKey())
			prev.setLeftChild(toInsert);
		else
			prev.setRightChild(toInsert);

		this.tSize = this.tSize + 1;

		if (toInsert.getKey() < tMin.getKey())
			tMin = toInsert;
		if (toInsert.getKey() > tMax.getKey())
			tMax = toInsert;
	}

	public static void transplant(RBNode x, RBNode y) {
		// if (x.equals(x.getParent.getLeftChild)) {

		// }
	}

	/**
	 * public int delete(int k)
	 * 
	 * deletes an item with key k from the binary tree, if it is there; the tree
	 * must remain valid (keep its invariants). returns the number of color
	 * switches, or 0 if no color switches were needed. returns -1 if an item
	 * with key k was not found in the tree.
	 */
	public int delete(int k) {
		return 42; // to be replaced by student code
	}

	/**
	 * public String min()
	 * 
	 * Returns the value of the item with the smallest key in the tree, or null
	 * if the tree is empty
	 */
	public String min() {
		if (this.empty() == true) {
			return null;
		}
		return this.tMin.getValue();
	}

	/**
	 * public String max()
	 * 
	 * Returns the value of the item with the largest key in the tree, or -1 if
	 * the tree is empty
	 */
	public String max() {
		if (this.empty() == true) {
			return null;
		}
		return this.tMax.getValue();
	}

	/**
	 * public int[] keysToArray()
	 * 
	 * Returns a sorted array which contains all keys in the tree, or an empty
	 * array if the tree is empty.
	 */
	public int[] keysToArray() {
		int[] arr = new int[42]; // to be replaced by student code
		return arr; // to be replaced by student code
	}

	/**
	 * public String[] valuesToArray()
	 * 
	 * Returns an array which contains all values in the tree, sorted by their
	 * respective keys, or an empty array if the tree is empty.
	 */
	public String[] valuesToArray() {
		String[] arr = new String[42]; // to be replaced by student code
		return arr; // to be replaced by student code
	}

	/**
	 * public int size()
	 * 
	 * Returns the number of nodes in the tree.
	 * 
	 * precondition: none postcondition: none
	 */
	public int size() {
		return 42; // to be replaced by student code
	}

	/**
	 * public class RBNode
	 * 
	 * If you wish to implement classes other than RBTree (for example RBNode),
	 * do it in this file, not in another file. This is an example which can be
	 * deleted if no such classes are necessary.
	 */
	public class RBNode {
		public static final int RED_COLOUR = 1;
		public static final int BLACK_COLOUR = 0;
		private int nkey;
		private String nvalue;
		private int ncolour;
		private RBNode nParent;
		private RBNode nLeft;
		private RBNode nRight;

		RBNode(int key, String value, int colour) {
			this.nkey = key;
			this.nvalue = value;
			this.ncolour = colour;
			this.nParent = nullNode;
			this.nLeft = nullNode;
			this.nRight = nullNode;

		}

		public void setColour(int colour) {
			this.ncolour = colour;
		}

		public int getColour() {
			return this.ncolour;
		}

		public void setParent(RBNode parent) {
			this.nParent = parent;
		}

		public RBNode getParent() {
			return this.nParent;
		}

		public void setRightChild(RBNode Right) {
			this.nRight = Right;
		}

		public void setLeftChild(RBNode Left) {
			this.nLeft = Left;
		}

		public RBNode getRightChild() {
			return this.nRight;
		}

		public RBNode getLeftChild() {
			return this.nLeft;
		}

		public void setValue(String Value) {
			this.nvalue = Value;
		}

		public String getValue() {
			return this.nvalue;
		}

		public void setKey(int key) {
			this.nkey = key;
		}

		public int getKey() {
			return this.nkey;
		}

		private boolean equals(RBNode other) {
			return (this.getKey() == other.getKey());
		}
	}

}
