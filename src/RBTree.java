
/**
 * 
 * RBTree
 * 
 * An implementation of a Red Black Tree with non-negative, distinct integer
 * keys and values
 * 
 */
//***************************************************************************
//submitted by: assaf oren    -   username: assaforen    -  ID: 301750956
//				tzur elyiahu  -   username: tzurelyiahu  -  ID: 300647799
//***************************************************************************
public class RBTree {




	// the sentinel is a dummy node at the top of the tree.
	// the sentinel's left child is the "real" root of the tree.
	// min, max and size are saved in order to make min(), max() and size()'s running time O(1).
	// nullNode is the external leaf (NIL) as seen in the lecture.

	private RBNode nullNode = new RBNode(Integer.MIN_VALUE, "",
			RBNode.BLACK_COLOUR);
	
	private RBNode sentinel = new RBNode(Integer.MAX_VALUE, "",
			RBNode.BLACK_COLOUR);

	private int tSize = 0;
	private RBNode tMin = new RBNode(Integer.MAX_VALUE, "min",
			RBNode.BLACK_COLOUR);
	private RBNode tMax = new RBNode(Integer.MIN_VALUE, "max",
			RBNode.BLACK_COLOUR);

	

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
		//no node was found with the k key
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

		// If tree is empty, enter as black root (as defined in the lecture, the left child of the dummy/sentinel)
		if (this.empty()) {
			setLeftRelation(sentinel, toInsert);
			setRightRelation(sentinel, nullNode);
			toInsert.setColour(0);
			tMax = toInsert;	//the only key is the max
			tMin = toInsert;	//the only key is the min
			tSize = 1;
			return 1;
		}

		// If key already in tree - do nothing and return -1
		if (this.search(toInsert.getKey()) != null)
			return -1;

		// Else, insert item and colour it red
		treeInsert(toInsert);

		// Fix the tree
		return rbInsertFixup(toInsert);

	}

	public int rbInsertFixup(RBNode node) {
		int colourChanges = 0;
		RBNode currentNode = node;

		while (currentNode.getParent().getColour() == RBNode.RED_COLOUR) {

			// If dad is a left child
			if (currentNode.getParent().equals(
					currentNode.getParent().getParent().getLeftChild())) {
				// Case 1
				if (currentNode.getParent().getParent().getRightChild()
						.getColour() == RBNode.RED_COLOUR) {
					currentNode.getParent().setColour(RBNode.BLACK_COLOUR);
					currentNode.getParent().getParent().getRightChild()
							.setColour(RBNode.BLACK_COLOUR);
					currentNode.getParent().getParent()
							.setColour(RBNode.RED_COLOUR);
					colourChanges = colourChanges + 3;
					currentNode = currentNode.getParent().getParent();

				} else {
					// Case 2
					if (currentNode == currentNode.getParent().getRightChild()) {
						currentNode = currentNode.getParent();
						leftRotate(currentNode);
					}
					// Case 3
					currentNode.getParent().setColour(RBNode.BLACK_COLOUR);
					currentNode.getParent().getParent()
							.setColour(RBNode.RED_COLOUR);
					rightRotate(currentNode.getParent().getParent());
					colourChanges = colourChanges + 2;

				}
			}
			// If dad is a right child
			else {
				// Case 1
				if (currentNode.getParent().getParent().getLeftChild()
						.getColour() == RBNode.RED_COLOUR) {
					currentNode.getParent().setColour(RBNode.BLACK_COLOUR);
					currentNode.getParent().getParent().getLeftChild()
							.setColour(RBNode.BLACK_COLOUR);
					currentNode.getParent().getParent()
							.setColour(RBNode.RED_COLOUR);
					colourChanges = colourChanges + 3;
					currentNode = currentNode.getParent().getParent();

				} else {
					// Case 2
					if (currentNode == currentNode.getParent().getLeftChild()) {
						currentNode = currentNode.getParent();
						rightRotate(currentNode);
					}
					// Case 3
					currentNode.getParent().setColour(RBNode.BLACK_COLOUR);
					currentNode.getParent().getParent()
							.setColour(RBNode.RED_COLOUR);
					leftRotate(currentNode.getParent().getParent());
					colourChanges = colourChanges + 2;

				}
			}
		}

		// If root is red, change it
		if (sentinel.getLeftChild().getColour() == RBNode.RED_COLOUR) {
			sentinel.getLeftChild().setColour(RBNode.BLACK_COLOUR);
			colourChanges++;
		}

		return colourChanges;
	}
	
	// treeInsert is a method who helps us by just inserting the item to a non-empty tree
	// so that the code will still look elegant and understandable for outside readers
	public void treeInsert(RBNode toInsert) {

		RBNode prev = null;
		RBNode current = this.sentinel.getLeftChild();

		// Find where to insert new node
		while (!current.equals(nullNode)) {
			prev = current;
			if (toInsert.getKey() < current.getKey())
				current = current.getLeftChild();
			else
				current = current.getRightChild();
		}

		// Set corresponding parent and child links
		toInsert.setParent(prev);

		if (toInsert.getKey() < prev.getKey())
			prev.setLeftChild(toInsert);
		else
			prev.setRightChild(toInsert);

		this.tSize = this.tSize + 1;

		// Update tree Min and Max
		if (toInsert.getKey() < tMin.getKey())
			tMin = toInsert;
		if (toInsert.getKey() > tMax.getKey())
			tMax = toInsert;
	}

	// as seen in lecture
	public void leftRotate(RBNode toRotate) {
		RBNode child = toRotate.getRightChild();
		transplant(toRotate, child);
		setRightRelation(toRotate, child.getLeftChild());
		setLeftRelation(child, toRotate);
	}

	// as seen in lecture
	public void rightRotate(RBNode toRotate) {
		RBNode child = toRotate.getLeftChild();
		transplant(toRotate, child);
		setLeftRelation(toRotate, child.getRightChild());
		setRightRelation(child, toRotate);
	}

	// as seen in lecture, used for the insert method
	public static void transplant(RBNode x, RBNode y) {
		if (x.equals(x.getParent().getLeftChild()))
			setLeftRelation(x.getParent(), y);
		else
			setRightRelation(x.getParent(), y);
	}
	
	// a bit different transplant, used for the delete method
	public void rbtransplant(RBNode x, RBNode y) {
		if (x.getParent().equals(this.sentinel))
			this.sentinel.setLeftChild(y);
		else if (x.equals(x.getParent().getLeftChild()))
			x.getParent().setLeftChild(y);
		else
			x.getParent().setRightChild(y);
		y.setParent(x.getParent());
	}


	// Sets dad's left child to child, and child's parent to dad
	// Same as the "Left-Child" method from lecture
	public static void setLeftRelation(RBNode dad, RBNode child) {
		dad.setLeftChild(child);
		child.setParent(dad);
	}

	// Sets dad's right child to child, and child's parent to dad
	// Same as the "Right-Child" method from lecture
	public static void setRightRelation(RBNode dad, RBNode child) {
		dad.setRightChild(child);
		child.setParent(dad);
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
		int colourChanges = 0;
		// if the tree is empty - do nothing and return -1
		if (this.empty())
			return -1;
		
		// search for the node we need. using regular search won't help because
		// the search method returns a String
		RBNode delNode = this.getRoot();
		while (!delNode.equals(nullNode)) {
			if (k == delNode.getKey()) {
				break;
			} else if (k > delNode.getKey())
				delNode = delNode.getRightChild();
			else
				delNode = delNode.getLeftChild();
		}
		
		// if node doesn't exist, return -1
		if (delNode.equals(nullNode))
			return -1;
		

		RBNode Toreplace = delNode;
		RBNode Temp;
		int delNodeOrigColor = delNode.getColour();
		
		if (delNode.getLeftChild().isDummy()) {
			Temp = delNode.getRightChild();
			rbtransplant(delNode, delNode.getRightChild());
		}
		else if (delNode.getRightChild().isDummy()) {
			Temp = delNode.getLeftChild();
			rbtransplant(delNode, delNode.getLeftChild());
		}
		else {
			Toreplace = minSubTree(delNode.getRightChild());
			delNodeOrigColor = Toreplace.getColour();
			Temp = Toreplace.getRightChild();
			if (Toreplace.getParent().equals(delNode))
				Temp.setParent(Toreplace);
			else {
				rbtransplant(Toreplace, Toreplace.getRightChild());
				Toreplace.setRightChild(delNode.getRightChild());
				Toreplace.getRightChild().setParent(Toreplace);
			}
			rbtransplant(delNode, Toreplace);
			Toreplace.setLeftChild(delNode.getLeftChild());
			Toreplace.getLeftChild().setParent(Toreplace);
			Toreplace.setColour(delNode.getColour());
		}

		
		// updates if it's now an empty tree
		tSize--; // the size of the tree is down by one
		if (this.getRoot().equals(nullNode)) {
			tMin = new RBNode(Integer.MAX_VALUE, "", RBNode.BLACK_COLOUR);// updates
																			// min
			tMax = new RBNode(Integer.MIN_VALUE, "", RBNode.BLACK_COLOUR);// updates
																			// max

		} 

		else {
			if (k == tMin.getKey())// find a new min in case we deleted the current min
				tMin = minSubTree(this.getRoot());
			if (k == tMax.getKey())// find a new max in case we deleted the current max
				tMax = maxSubTree(this.getRoot());

			// fix the tree
			if (delNodeOrigColor == RBNode.BLACK_COLOUR)
				colourChanges = deleteFixUp(Temp);
		}

		

		return colourChanges;
	}
	
	// the Delete FixUp method as seen in the lecture
	public int deleteFixUp(RBNode node) {
		RBNode sibling;
		int colourChanges = 0;
		// node isn't the root but node is black
		while ((!node.equals(this.getRoot()))
				&& node.getColour() == RBNode.BLACK_COLOUR) {
			// if node is the left child of his parent
			if (node.equals(node.getParent().getLeftChild())) {
				sibling = node.getParent().getRightChild();
				// case 1 as seen in the lecture
				if (sibling.getColour() == RBNode.RED_COLOUR) {
					sibling.setColour(RBNode.BLACK_COLOUR);
					colourChanges++;
					if(node.getParent().getColour()==RBNode.BLACK_COLOUR){
						node.getParent().setColour(RBNode.RED_COLOUR);
						colourChanges++;
					}
					leftRotate(node.getParent());
					sibling = node.getParent().getRightChild();
				}

				// case 2 as seen in the lecture
				if ((sibling.getLeftChild().getColour() == RBNode.BLACK_COLOUR)
						&& sibling.getRightChild().getColour() == RBNode.BLACK_COLOUR) {
					if(sibling.getColour()==RBNode.BLACK_COLOUR){
						sibling.setColour(RBNode.RED_COLOUR);
						colourChanges++;
					}
					node = node.getParent();
				}

				// case 3
				else {
					RBNode delNodeParent = node.getParent();
					if (sibling.getRightChild().getColour() == RBNode.BLACK_COLOUR) {
						if(sibling.getLeftChild().getColour()==RBNode.RED_COLOUR){
							sibling.getLeftChild().setColour((RBNode.BLACK_COLOUR));
							colourChanges++;
						}
						if(sibling.getColour()==RBNode.RED_COLOUR){
							sibling.setColour((RBNode.RED_COLOUR));
							colourChanges++;
						}
						
						rightRotate(sibling);
						sibling = delNodeParent.getRightChild();

					}
					// case 4
					if(sibling.getColour()!=delNodeParent.getColour()){
						sibling.setColour(delNodeParent.getColour());
						colourChanges++;
					}
					if(delNodeParent.getColour()==RBNode.RED_COLOUR){
						delNodeParent.setColour(RBNode.BLACK_COLOUR);
						colourChanges++;
					}
					if(sibling.getRightChild().getColour()==RBNode.RED_COLOUR){
						sibling.getRightChild().setColour(RBNode.BLACK_COLOUR);
						colourChanges++;
					}
					leftRotate(delNodeParent);
					node = this.getRoot();
				}

			} else { // if node is the right child of his parent
				sibling = node.getParent().getLeftChild();
				// case 1
				if (sibling.getColour() == RBNode.RED_COLOUR) {
					sibling.setColour(RBNode.BLACK_COLOUR);
					colourChanges++;
					if(node.getParent().getColour()==RBNode.BLACK_COLOUR){
						node.getParent().setColour(RBNode.RED_COLOUR);
						colourChanges++;
					}
					rightRotate(node.getParent());
					sibling = node.getParent().getLeftChild();

				}
				// case 2
				if ((sibling.getLeftChild().getColour() == RBNode.BLACK_COLOUR)
						&& (sibling.getRightChild().getColour() == RBNode.BLACK_COLOUR)) {
					if(sibling.getColour()==RBNode.BLACK_COLOUR){
						sibling.setColour((RBNode.RED_COLOUR));
						colourChanges++;	
					}
					node = node.getParent();
				}
				// case 3
				else {
					RBNode delNodeParent  = node.getParent();
					if (sibling.getLeftChild().getColour() == RBNode.BLACK_COLOUR) {
						if(sibling.getRightChild().getColour()==RBNode.RED_COLOUR){
							sibling.getRightChild().setColour(RBNode.BLACK_COLOUR);
							colourChanges++;
						}
						if(sibling.getColour()==RBNode.BLACK_COLOUR){
							sibling.setColour(RBNode.RED_COLOUR);
							colourChanges++;	
						}
						leftRotate(sibling);
						sibling = delNodeParent.getLeftChild();
					}
					// case 4
					if(sibling.getColour()!=delNodeParent.getColour()){
						sibling.setColour(delNodeParent.getColour());
						colourChanges++;
					}
					if(delNodeParent.getColour()==RBNode.RED_COLOUR){
						delNodeParent.setColour(RBNode.BLACK_COLOUR);
						colourChanges++;
					}
					if(sibling.getLeftChild().getColour()==RBNode.RED_COLOUR){
						sibling.getLeftChild().setColour(RBNode.BLACK_COLOUR);
						colourChanges++;
					}
					rightRotate(delNodeParent);
					node = getRoot();
				}

			}
		}
		if(node.getColour()==RBNode.RED_COLOUR){
			node.setColour(RBNode.BLACK_COLOUR);
			colourChanges++;
		}

		return colourChanges;
	}



	// find the min in the given tree or subtree

	// return RBNode which is the minimum value in a tree or sub tree
	// used in order to replace a minimum deleted node (since the min and max values are
	// kept in a constant field
	public RBNode minSubTree(RBNode node) {
		while (!node.getLeftChild().equals(nullNode)) {
			node = node.getLeftChild();
		}
		return node;
	}

	// return RBNode which is the maximum value in a tree or sub tree
	// used in order to replace a maximum deleted node (since the min and max values are
	// kept in a constant field
	public RBNode maxSubTree(RBNode node) {
		while (!node.getRightChild().equals(nullNode)) {
			node = node.getRightChild();
		}
		return node;
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
		int[] result = new int[this.size()];
		keysToArrayRecursive(result,0, this.getRoot());
		return result;
	}

	/**
	 * public static int keysToArrayRecursive(int[] arr, int position, RBNode current)
	 * 
	 * Recursively updates "arr" with "current"'s subtree's keys (including "current"'s).
	 * Starts updating in "position".
	 * Returns "position" + "current"'s subtree size (including "current", if he is not nullNode).
	 */
	public static int keysToArrayRecursive(int[] arr, int position, RBNode current) {
		
		if (current.isDummy())
			return position;
		
		// Insert left subtree and update position
		position = keysToArrayRecursive(arr, position, current.getLeftChild()); 
		// Add current node's key
		arr[position] = current.getKey();
		// Add right subtree and update position
		position = keysToArrayRecursive(arr, position + 1,current.getRightChild()); 
		
		return position;
	}
	
	
	/**
	 * public String[] valuesToArray()
	 * 
	 * Returns an array which contains all values in the tree, sorted by their
	 * respective keys, or an empty array if the tree is empty.
	 */
	public String[] valuesToArray() {
		String[] result = new String[this.size()];
		valuesToArrayRecursive(result,0, this.getRoot());
		return result;
	}
	
	
	/**
	 * public static int valuesToArrayRecursive(int[] arr, int position, RBNode current)
	 * 
	 * Recursively updates "arr" with "current"'s subtree's keys (including "current"'s).
	 * Starts updating in "position".
	 * Returns "position" + "current"'s subtree size (including "current", if not nullNode).
	 */
	public static int valuesToArrayRecursive(String[] arr, int position, RBNode current) {
		
		if (current.isDummy())
			return position;
		
		// Insert left subtree and update position
		position = valuesToArrayRecursive(arr, position, current.getLeftChild()); 
		// Add current node's key
		arr[position] = current.getValue();
		// Add right subtree and update position
		position = valuesToArrayRecursive(arr, position + 1,current.getRightChild()); 
		
		return position;
	}


	/**
	 * public int size()
	 * 
	 * Returns the number of nodes in the tree.
	 * 
	 * precondition: none postcondition: none
	 */
	public int size() {
		return this.tSize;
	}

	public RBNode getRoot() {
		return this.sentinel.getLeftChild();
	}

	/**
	 * public class RBNode
	 * 
	 * If you wish to implement classes other than RBTree (for example RBNode),
	 * do it in this file, not in another file. This is an example which can be
	 * deleted if no such classes are necessary.
	 */
	public class RBNode {
		public static final int RED_COLOUR = 1;	//define a red color
		public static final int BLACK_COLOUR = 0; //define a black color
		//all the required field in order for the Nodes to work perfectly
		private int nkey;
		private String nvalue;
		private int ncolour;
		private RBNode nParent;
		private RBNode nLeft;
		private RBNode nRight;

		RBNode(int key, String value, int colour) {	//builder
			this.nkey = key;
			this.nvalue = value;
			this.ncolour = colour;
			this.nParent = nullNode;
			this.nLeft = nullNode;
			this.nRight = nullNode;

		}
		//simple but important getter and setter methods 
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

		public boolean isBlack() {
			return (this.ncolour == 0);
		}

		public boolean isDummy() {
			return (this.getKey() == Integer.MIN_VALUE);
		}
	}

}