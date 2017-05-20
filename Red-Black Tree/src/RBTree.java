/**
 *
 * RBTree
 *
 * An implementation of a Red Black Tree with non-negative, distinct integer
 * keys and values
 *
 */

public class RBTree {
	private RBNode root;
	private int size;
	private RBNode NIL;

	/**
	 * public RBTree()
	 *
	 * constructor
	 *
	 */
	public RBTree() {
		this.size = 0;
		NIL = new RBNode((int) Integer.MIN_VALUE, null);
		NIL.color = 0; // set black color for NIL node (because red color is
						// default)
		RBNode dummyRoot = new RBNode((int) Integer.MAX_VALUE, null);
		dummyRoot.color = 0; // set black color for dummy root
		// The real root is the left child of the (dummy) root
		this.root = dummyRoot;
		this.root.left = NIL;
		this.root.right = NIL;
	}

	// HELP FUNCTION TO PRINT TREE - NEED TO BE REMOVED BEFORE SUBMISSION
	public void printTree() {
		System.out.println("tree:");
		printTreeForReal(root);
	}

	// HELP FUNCTION TO PRINT TREE - NEED TO BE REMOVED BEFORE SUBMISSION
	public void printTreeForReal(RBNode node) {
		if (node == NIL) {
			return;
		}
		printTreeForReal(node.left);
		printTreeForReal(node.right);
		System.out.println("key = " + node.key + " value = " + node.value
				+ " color = " + node.color);
	}

	/**
	 * public boolean empty()
	 *
	 * returns true if and only if the tree is empty (except for Dummy root)
	 *
	 */
	public boolean empty() {
		return (this.size == 0);
	}

	/**
	 * public String search(int k)
	 *
	 * returns the value of an item with key k if it exists in the tree
	 * otherwise, returns null
	 */

	public String search(int k) {
		if (search_For_Node(k) == null)
			return null;
		else
			return search_For_Node(k).value;
	}

	private RBNode search_For_Node(int k) {
		if (this.empty()) {
			return null;
		} else
			return recursiveSearch(this.root.left, k);
	}

	private RBNode recursiveSearch(RBNode node, int k) {
		if (k == node.key) {
			return (node);
		} else if (k > node.key) {
			if (node.right == NIL)
				return null;
			else
				return (recursiveSearch(node.right, k));
		} else if (node.left == NIL)
			return null;
		else
			return recursiveSearch(node.left, k);
	}

	public int insert(int k, String v) {
		if (!this.empty()) {
			RBNode pos = treePosition(root.left, k);
			if (pos == (null)) { // a RBNode with the same key already
									// exist in the tree
				return (-1);
			} else {
				// Create a new red RBNode, with k & v as his characteristic,
				// and pos as his parent
				RBNode newNode = new RBTree.RBNode(k, v);
				newNode.parent = pos;
				newNode.color = 1;
				newNode.left = NIL;
				newNode.right = NIL;
				// decide rather the new RBNode will be left or right son of pos
				if (k < pos.key) { // newNode should be a Left son of pos
					pos.left = (newNode);
				} else
					pos.right = (newNode);
				// Update the number of RBNodes in the RBTree
				this.size++;

				return insertFixup(newNode); // returns the number of
												// color-changes at a tree
												// fix-up
			}
		} else {// Empty tree
			this.size++; // Update the number of RBNodes in the RBTree
			// Create a new black RBNode(was red and changed to be black), with
			// k & v as his characteristic, and Dummy root as his parent
			RBNode newNode = new RBTree.RBNode(k, v);
			newNode.parent = this.root;
			newNode.color = 0;
			newNode.right = NIL;
			newNode.left = NIL;
			this.root.left = newNode;
			return 1; // insert a red RBNode, but because it is the root it
						// changed to be black
		}

	}

	/**
	 * 
	 * @param pivot
	 *            - the new RBNOde that has been insert to the tree as a leaf
	 * @return the number of color-changng, while fixup the tree
	 */
	private int insertFixup(RBNode pivot) {
		int counter = 0;
		RBNode uncle;
		while (pivot.parent.color == 1) { // while the parents color
											// is RED - the red-rule
											// needs to be corrected

			if (pivot.parent == pivot.parent.parent.left) { // if
															// the
															// pivot's
															// parent
															// is
															// a
															// left
															// child
				uncle = pivot.parent.parent.right;
				if (uncle.color == 1) { // CASE1
					insertCase1(pivot, uncle);
					pivot = pivot.parent.parent;
					counter += 3; // 3 color changing (parent, uncle, grandpa)
				} else { // uncle's color is Black (0)
					if (pivot == pivot.parent.right) { // CASE3
						pivot = pivot.parent;
						leftRotate(pivot);
						// no color changing
					} // CASE2
					pivot.parent.color = (0);
					pivot.parent.parent.color = (1);
					rightRotate(pivot.parent.parent);
					counter += 2; // 2 color changing (parent and grandpa)

				}
			} else { // symmetrical opposite - the pivot's parent is a right
						// child
				uncle = pivot.parent.parent.left;
				if (uncle.color == 1) { // CASE1
					insertCase1(pivot, uncle);
					pivot = pivot.parent.parent;
					counter += 3; // 3 color changing (parent, uncle, grandpa)
				} else {
					if (pivot == pivot.parent.left) { // CASE3
						pivot = pivot.parent;
						rightRotate(pivot);
						// no color changing
					} // CASE2
					pivot.parent.color = (0);
					pivot.parent.parent.color = (1);
					leftRotate(pivot.parent.parent);
					counter += 2;// 2 color changing (parent and grandpa)
				}
			}
		}
		if (this.root.left.color == 1) {
			this.root.left.color = (0); // if during the fix-up the real-root
										// become red,
			// we shall put it back to be black (can
			// happen in case 1)
			counter++;
		}
		return counter;
	}

	private void insertCase1(RBNode pivot, RBNode uncle) {
		pivot.parent.color = (0);
		uncle.color = (0);
		pivot.parent.parent.color = (1);
	}

	/**
	 * private void rightRotate(RBNode pivot) makes a right-turn of the arc
	 * between pivot and his left child (pivot becomes his left-child right
	 * child)
	 */
	private void rightRotate(RBNode pivot) {
		RBNode child = pivot.left;
		transplant(pivot, child);
		setAsLeftChild(pivot, child.left);
		setAsRightChild(child, pivot);
	}

	/**
	 * private void leftRotate(RBNode pivot) makes a left-turn of the arc
	 * between pivot and his right child (pivot becomes his right-child left
	 * child)
	 */
	private void leftRotate(RBNode pivot) {
		RBNode child = pivot.right;
		transplant(pivot, child); // Replace the subtree of pivot by the subtree
									// of child
		setAsRightChild(pivot, child.left);
		setAsLeftChild(child, pivot);
	}

	/**
	 * private void Transplant(RBNode parent, RBNode child) Replace the subtree
	 * of parent by the subtree of child
	 */
	private void transplant(RBNode parent, RBNode child) {
		if (parent == parent.parent.left) { // pivot is a left child
			setAsLeftChild(parent.parent, child);
		} else { // pivot is a right child
			setAsRightChild(parent.parent, child);
		}
	}

	/**
	 * private void setAsLeftChild(RBNode parent, RBNode leftChild) update
	 * leftChild to be parent's left child, and parent to be leftChidl's parent.
	 */
	private void setAsLeftChild(RBNode parent, RBNode leftChild) {
		parent.left = (leftChild);
		leftChild.parent = (parent);
	}

	/**
	 * private void setAsRightChild(RBNode parent, RBNode rightChild) update
	 * rightChild to be parent's right child, and parent to be rightChidl's
	 * parent.
	 */

	private void setAsRightChild(RBNode parent, RBNode rightChild) {
		parent.right = (rightChild);
		rightChild.parent = (parent);
	}

	/**
	 * public RBNode treePosition (RBNode root, int key) returns the place where
	 * you should insert the node with the given key If the given key already
	 * exist in the tree - will return null
	 */

	public RBNode treePosition(RBNode node, int key) {
		RBNode pos = node; // initialize
		while (node != NIL) {
			pos = node;
			if (key == node.key) { // the given key already exist in the tree
				return (null);
			} else if (key < node.key) {
				node = node.left;
			} else { // key > node.key
				node = node.right;
			}
		}
		return pos;
	}

	/**
	 * public int delete(int k)
	 *
	 * 
	 * 
	 * 
	 */
	public int delete(int k)
	{	
		int counter = 0;
		RBNode z = search_For_Node(k);
		

		RBNode x;
		RBNode y = z;
		
		if (this.size == 0) { // the RBTree is empty
			return -1;
		}
		
		if (z == null) // node not found
			return -1;
		

		
		
		boolean isBlackOriginalY;
		if (y.color == 0) isBlackOriginalY=true; else isBlackOriginalY = false;

		
		
		//z is the node we want to delete
		if(z.left.key == Integer.MIN_VALUE ){
			x = z.right;
			transplant(z,z.right);
		}else if(z.right.key == Integer.MIN_VALUE){
			x = z.left;
			transplant(z,z.left);
		}else{
			y = minNode(z.right);
			if (y.color == 0) isBlackOriginalY=true; else isBlackOriginalY = false;
			x = y.right;
			if(y.parent == z){
			}else{
				transplant(y,y.right);
				y.right = z.right;
				y.right.parent = y;
			}
			transplant(z,y);
			y.left = z.left;
			y.left.parent = y;
			y.color = z.color;
		}

		if(isBlackOriginalY){
			//we have a problem with the black rule that needs to be fixed
			counter = deleteFixup(x);
		}

		this.size--; // update tree's size
		
		return counter;
	}

	/*
	 * O(log(n))
	 * fix the red and black rules after deleting a node.
	 * @param x - the node to start the fixing from
	 * @return number of color changes made while fixing the tree
	 */
	private int deleteFixup(RBNode x){
		//number of color changes
		int counter = 0;
		//run until x is the tree root and as long as x is black
		while((x != this.root.left) && x!=NIL && x.color == 0){
			if(x == x.parent.left){
				//x is a left child
				RBNode w = x.parent.right;
				if(w.color != 0){
					//case 1
					w.color = 0;
					x.parent.color = 1;
					leftRotate(x.parent);
					w = x.parent.right;
					counter += 2;
				}
				if((w.left.color == 0) && (w.right.color == 0)){
					//case 2
					w.color = 1;
					x = x.parent;
					counter += 1;
				}else 
				{
					if(w.right.color ==0){
						//case 3 
						w.left.color = 0;
						w.color = 1;
						rightRotate(w);
						w = x.parent.right;
						counter +=2;
					}
					//case 4
					w.color = x.parent.color;
					x.parent.color = 0;
					w.right.color = 0;
					leftRotate(x.parent);
					x = this.root.left;
					counter += 1;
				}
			}else{
				//x is a right child
				RBNode w = x.parent.left;
				if(w.color != 0){
					//case 1
					w.color = 0;
					x.parent.color = 1;
					rightRotate(x.parent);
					w = x.parent.left;
					counter += 2;
				}
				if(w.left.color == 0 && w.right.color == 0){
					w.color = 1;
					x = x.parent;
					counter += 1;
				}else 
				{
					if(w.left.color == 0){
						//case 3 
						w.right.color = 0;
						w.color = 1;
						leftRotate(w);
						w = x.parent.left;
						counter +=2;
					}
					//case 4
					w.color = x.parent.color;
					x.parent.color = 0;
					w.left.color = 0;
					rightRotate(x.parent);
					x = this.root.left;
					counter +=1;
				}
			}
		}
		if(x.color == 1){
			x.color = 0;
			counter += 1;
		}

		return counter;
	}

	
	
	
	
	
	
	
	/**
	 * public String min()
	 *
	 * Returns the value of the item with the smallest key in the tree, or null
	 * if the tree is empty
	 */
	public String min() {
		if (this.empty()) {
			return (null);
		} else {
			RBNode pivot = this.root.left;
			while (pivot.left != NIL) {
				pivot = pivot.left;
			}
			return (pivot.value);
		}
	}

	/**
	 * private static RBNode minNode(RBNode node) O(log(n)) Returns the node
	 * with the minimal key in the tree with given root node
	 * 
	 * @param node
	 *            - the root of the tree
	 * @return minimal node of the tree
	 */
	public RBNode minNode(RBNode node) {
		
		if (this.size == 0) { // the RBTree is empty
			return null;
		}
		
		if (node == NIL || node == null) {
			return null;
		}

		// if left node is null so we reached to the min node
		if (node.left == NIL) {
			return node;
		}
		return minNode(node.left);
	}

	/**
	 * public String max()
	 *
	 * Returns the value of the item with the largest key in the tree, or null
	 * if the tree is empty
	 */
	public String max() {
		if (this.empty()) {
			return (null);
		} else {
			RBNode pivot = this.root.left;
			while (pivot.right != NIL) {
				pivot = pivot.right;
			}
			return (pivot.value);
		}
	}

	/**
	 * public int[] keysToArray()
	 *
	 * Returns a sorted array which contains all keys in the tree, or an empty
	 * array if the tree is empty.
	 */
	public int[] keysToArray() {
		if (this.empty())
			return new int[0];
		else {
			int[] arr = new int[size];
			arr[0] = minNode(this.root.left).key;
			RBNode pos = minNode(this.root.left);
			for (int i = 1; i < this.size(); i++) {
				pos = successor(pos);
				arr[i] = pos.key;
			}
			return arr;
		}
	}

	public RBNode successor(RBNode node) {
		if (node.right != NIL) {
			return (minNode(node.right));
		}
		RBNode parent = node.parent;
		while (parent != NIL && node == parent.right) {
			node = parent;
			parent = node.parent;
		}
		return parent;
	}

	/**
	 * public String[] valuesToArray()
	 *
	 * Returns an array which contains all values in the tree, sorted by their
	 * respective keys, or an empty array if the tree is empty.
	 */
	public String[] valuesToArray() {
		if (this.size == 0) {
			return new String[0];
		} else {
			String[] arr = new String[this.size()];
			arr[0] = minNode(this.root.left).value;
			RBNode pos = minNode(this.root.left);
			for (int i = 1; i < this.size(); i++) {
				pos = successor(pos);
				arr[i] = pos.value;
			}
			return arr;
		}
	}

	/**
	 * public int size()
	 *
	 * Returns the number of nodes in the tree.
	 *
	 * precondition: none postcondition: none
	 */
	public int size() {
		return (this.size);
	}

	/**
	 * public RBNode getRoot()
	 *
	 * Returns the (real) root of the tree.
	 *
	 * precondition: real root exists postcondition: none
	 */
	public RBNode getRoot() {
		return (this.root.left);
	}

	/**
	 * public class RBNode
	 *
	 * If you wish to implement classes other than RBTree (for example RBNode),
	 * do it in this file, not in another file. This is an example which can be
	 * deleted if no such classes are necessary.
	 */

	public class RBNode {
		private String value;
		private int key;
		private RBNode left;
		private RBNode right;
		private RBNode parent;
		private int color; // black = 0, red = 1

		public RBNode(int key, String value) {
			this.value = value;
			this.key = key;
			this.left = null;
			this.right = null;
			this.parent = null;
			this.color = 1;
		}

		public String getValue() {
			return (this.value);
		}

		public int getKey() {
			return (this.key);
		}

		public int getColor() {
			return (this.color);
		}

		public void setColor(int newColor) {
			this.color = newColor;
		}

		public RBNode getLeft() {
			return this.left;
		}

		public void setLeft(RBNode newLeft) {
			this.left = newLeft;
		}

		public RBNode getRight() {
			return this.right;
		}

		public void setRight(RBNode newRight) {
			this.right = newRight;
		}

		public RBNode getParent() {
			return (this.parent);
		}

		public void setParent(RBNode newParent) {
			this.parent = newParent;
		}

		// HELP METHOD TO PRINT RBNodes - NEEDS TO BE REMOVED BEFORE SUBMISSION
		public String toString() {
			String str = "( key: " + this.key + " , value: " + this.value
					+ " , color: " + this.getColor() + " , parent: "
					+ this.parent.key + " , right child : " + this.right.key
					+ " , left child : " + this.left.key + " )";
			return str;
		}

		// *** PUT IN RBNode CLASS ***
		// @Override
		/*
		 * public String toString() { if (this == NIL) return "null"; String
		 * color; if (this.color == 1) color = "RED"; else color = "BLACK";
		 * return "[ " + this.left.toString() + " <- " + this.key + ", " +
		 * this.value + ", " + color + " -> " + this.right.toString() + " ]";
		 */
	}

	// }

	// *** PUT IN RBTree CLASS ***
	@Override
	public String toString() {
		if (this.size == 0) { // the RBTree is empty
			return null;
		}
		return this.root.left.toString();
	}

}
