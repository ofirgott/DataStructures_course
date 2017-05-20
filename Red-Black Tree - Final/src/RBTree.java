/**
 *
 * RBTree
 *
 * An implementation of a Red Black Tree with
 * non-negative, distinct integer keys and values
 *
 */

public class RBTree {
	private RBNode root;
	private int size;

	/*****************************************************************************
	 *  RBTree methods:
	 *****************************************************************************/
	/**
	 * public RBTree()
	 * constructors 
	 */
	public RBTree() {
		this.root = newDummyRoot();
		this.size = 0;
	}
	
	public RBTree(RBNode root){
		this.root = newDummyRoot(root);
		this.size = 1;
	}
	
	private RBNode newDummyRoot(){
		return newDummyRoot(newNilNode(null));
	}

	private RBNode newDummyRoot(RBNode left){
		RBNode node = new RBNode(Integer.MAX_VALUE,"Dummy",left,null,null);
		node.left.parent = node;
		node.left.color = false;
		return node;
	}
	
	private RBNode newNilNode(RBNode parent){
		RBNode newNode = new RBNode(Integer.MIN_VALUE,"NIL",null,null,parent);
		newNode.color = false;
		return newNode;
	}

	// REMOVE!!! HELP FUNCTION TO PRINT TREE - NEED TO BE REMOVED BEFORE SUBMISSION
	public void printTree() {
		System.out.println("tree:");
		printTreeForReal(root);
	}

	// REMOVE!!!  HELP FUNCTION TO PRINT TREE - NEED TO BE REMOVED BEFORE SUBMISSION
	public void printTreeForReal(RBNode node) {
		if (node.key == Integer.MIN_VALUE) {
			return;
		}
		printTreeForReal(node.left);
		printTreeForReal(node.right);
		System.out.println("key = " + node.key + " value = " + node.value
				+ " color = " + node.color);
	}
	
	// REMOVE!!!  HELP FUNCTION TO PRINT TREE - NEED TO BE REMOVED BEFORE SUBMISSION
	public RBNode getRoot() {
		return (this.root.left);
	}
	// REMOVE!!!  *** PUT IN RBTree CLASS ***
		@Override
	public String toString() {
		return this.root.left.toString();
	}

		/*****************************************************************************
		 *  size, empty, search functions:
		 *****************************************************************************/
		
		/**
		 * 
		 * public boolean empty()
		 * Works in O(1) complexity - because we saved the number of nodes in the tree.
		 * @return "true" if and only if the tree is empty
		 * (except for Dummy root)
		 */
		public boolean empty() {
			return (this.size == 0);
		}
		
		
		/**
		 * public int size()
		 * Works in O(1) complexity - because we saved the number of nodes in the tree.
		 * @Return the number of nodes in the tree.
		 *
		 */
		public int size() {
			return (this.size);
		}


		/**
		 * 
		 * public String search(int k) 
		 * Works in O(log (n)) - as the height of the tree (which
		 * is log(n) cause RB tree is a Binary tree)
		 * 
		 * @return the value of an item with key k if it exists in the tree
		 * otherwise, returns null
		 */

		public String search(int k) {
			if (search_For_Node(k) == null) // didn't find key k, or ,empty tree
				return null;
			else
				return search_For_Node(k).value;
		}

		private RBNode search_For_Node(int k) {
			if (!this.empty()) {
				return recursiveSearch(this.root.left, k);
			} else
				// empty tree so the key isn't there for sure
				return null;
		}

		private RBNode recursiveSearch(RBNode node, int k) {
			if (k == node.key) {
				return (node);
			} else if (k > node.key) {
				if (node.right.key == Integer.MIN_VALUE)
					return null;
				else
					return (recursiveSearch(node.right, k));
			} else if (node.left.key == Integer.MIN_VALUE)
				return null;
			else
				return recursiveSearch(node.left, k);
		}

/*****************************************************************************
		 *  insert & delete functions:
*****************************************************************************/

		
		/**
		 * 
		 * public int insert(int k, String v) O(log n) - as the height of the tree
		 * (which is log(n) cause RB tree is a Binary tree)
		 * 
		 * @param k
		 *            & v, representing a new RBNode to insert. if a node with k as
		 *            his key doesn't exist in the tree, the function will insert it
		 *            as a leaf.
		 * @returns the number of color-changing or (-1) if the node already exist
		 *          in the tree
		 */
		 public int insert(int k, String v) {
			 RBNode pos = treePosition (k,this.root);
			 RBNode newNode = new RBNode(k,v,pos);
			 
			 if (pos == null){ // only DummyRoot exists, its an empty tree
				 this.root.left = newNode;
				 this.root.left.color = false; // the realRoot must be BLACK 
				 this.root.left.parent = this.root; 
				 this.size++; // the new realRoot is the only node in the tree (size=1)
				 return 1; // the realRoot was theoretically RED and become BLACK after insertion
			 }
			 else { 
				 this.size++;
				 if (pos.key == newNode.key){
					 return (-1);
				 }
				 if (pos.key < newNode.key){
					 pos.right = newNode;
				 }
				 else{ // pos.key > newNode.key
					 pos.left = newNode;
				 }
				 return (insertFixup(newNode));
			 }
		 }
		
		 private RBNode treePosition(int k, RBNode node) {
		 if (node.key == Integer.MAX_VALUE){ // node is the DummyRoot
			 return (treePosition (k, node.left)); // try to search in the realRoot 
		 }
		 
		 if (node.key == Integer.MIN_VALUE){ // node is an external leaf
			 if (node.parent.key == Integer.MAX_VALUE){
				 return null; 
			 }
			 else{
				 return node.parent;
			 }
		 }
		 if (k < node.key){
			 return (treePosition(k , node.left));
		 }
		 if (k > node.key){
			 return (treePosition(k , node.right));
		 }
		 return node; // k==node.key
		 }
		
		/**
		 * 
		 * @param pivot
		 *            - the new RBNOde that has been insert to the tree as a leaf
		 * @return the number of color-changing, while fixup the tree
		 */
		private int insertFixup(RBNode pivot) {
			int counter = 0;
			RBNode uncle;
			while (pivot.parent.color != false) { // while the parents color
												// is RED - the red-rule
												// needs to be corrected
				if ((pivot.parent == pivot.parent.parent.left)) { // if
																// the
																// pivot's
																// parent
																// is
																// a
																// left
																// child
					uncle = pivot.parent.parent.right;
					if (uncle.color) { // CASE1
						pivot.parent.color = (false);
						uncle.color = (false);
						pivot.parent.parent.color = (true);
						pivot = pivot.parent.parent;
						counter += 3; // 3 color changing (parent, uncle, grandpa)
					} else { // uncle's color is Black
						if (pivot == pivot.parent.right) { // CASE3
							pivot = pivot.parent;
							leftRotate(pivot);
							// no color changing
						} // CASE2
						pivot.parent.color = false; // parent turns BLACK
						pivot.parent.parent.color = true; // Grandparent turns RED
						rightRotate(pivot.parent.parent);
						counter += 2; // 2 color changing (parent and grandpa)

					}
				} else { // symmetrical opposite - the pivot's parent is a right
							// child
					uncle = pivot.parent.parent.left;
					if (uncle.color) { // CASE1 - uncle's color is RED
						pivot.parent.color = false;
						uncle.color = false;
						pivot.parent.parent.color = true;
						pivot = pivot.parent.parent;
						counter += 3; // 3 color changing (parent, uncle, grandpa)
					} else { // uncle's color is Black 
						if (pivot == pivot.parent.left) { // CASE3
							pivot = pivot.parent;
							rightRotate(pivot);
							// no color changing
						} // CASE2
						pivot.parent.color = (false);
						pivot.parent.parent.color = (true);
						leftRotate(pivot.parent.parent);
						counter += 2;// 2 color changing (parent and grandpa)
					}
				}
			}
			if (this.root.left.color == true) {
				this.root.left.color = (false); // if during the fix-up the real-root
											// become red,
				// we shall put it back to be black (can
				// happen in case 1)
				counter++;
			}
			return counter;
		}

		/**
		 * private void rightRotate(RBNode pivot); makes a right-turn of the arc
		 * between pivot and his left child (pivot becomes his left-child right
		 * child)
		 */
		private void rightRotate(RBNode pivot) {
			RBNode child = pivot.left;
			transplant(pivot, child);
			setAsLeftChild(pivot, child.right);
			setAsRightChild(child, pivot);
		}

		/**
		 * private void leftRotate(RBNode pivot) ; makes a left-turn of the arc
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
		 * private void Transplant(RBNode parent, RBNode child); Replace the subtree
		 * of parent by the subtree of child
		 */
		private void transplant(RBNode pivot, RBNode child) {
			if (pivot == pivot.parent.left) { // pivot is a left child
				setAsLeftChild(pivot.parent, child);
			} else { // pivot is a right child
				setAsRightChild(pivot.parent, child);
			}
		}

		/**
		 * private void setAsLeftChild(RBNode parent, RBNode leftChild); update
		 * leftChild to be parent's left child, and parent to be leftChidl's parent.
		 */
		private void setAsLeftChild(RBNode pivot, RBNode leftChild) {
			pivot.left = (leftChild);
			leftChild.parent = (pivot);
		}

		/**
		 * private void setAsRightChild(RBNode parent, RBNode rightChild); update
		 * rightChild to be parent's right child, and parent to be rightChidl's
		 * parent.
		 */

		private void setAsRightChild(RBNode pivot, RBNode rightChild) {
			pivot.right = (rightChild);
			rightChild.parent = (pivot);
		}

		
	/**
	 * 
	 * public int delete(int k) - Time Complexity - O(log(n))
	 *
	 * deletes node with key k from the tree, if exists (else return -1);
	 * returns number of color switches
	 */
	public int delete(int k)
	{	
		int switches_cnt = 0; 
		RBNode z = search_For_Node(k);
		RBNode x;
		RBNode y = z;
		
		if(z == null || this.empty()){	//node not found - return -1
			return -1;
		}

		
		boolean yWasBlack; //check if y was black at the beginning
		if (!y.color) yWasBlack=true; else yWasBlack=false;
		
		//(z - the node that we want to delete)
		
		if(z.left.key == Integer.MIN_VALUE){		//z.left is NIL	
			x = z.right;
			transplant(z,z.right);
		}else if(z.right.key == Integer.MIN_VALUE){	//z.right is NIL
			x = z.left;
			transplant(z,z.left);
		} else { // z has two children
			y = minNode(z.right); //z.right successor
			if (!y.color) yWasBlack=true; else yWasBlack=false;
			x = y.right;
			if (y.parent == z){
				x.parent = y;
			} else {
				transplant(y,y.right);
				y.right = z.right;
				y.right.parent = y;
			}
			transplant(z,y);
			y.left = z.left;
			y.left.parent = y;
			y.color = z.color;
		}

		if(yWasBlack){	// if y was originally black, we have a problem with the black
			// rule
			switches_cnt = deleteFixup(x);
		}

		this.size--;	// update tree's size
		
		return switches_cnt;
	}

	/**
	 * deleteFixup(RBNode x) - time complexity O(log(n))
	 * fix the RBTree's red and black rules after deleting a single node.
	 * return counter of number of color changes made while fixing the tree
	 */
	private int deleteFixup(RBNode x){
		
		int switches_cnt = 0;	// counter for color switches
		//run until x is the tree root and as long as x is black
		while(x != this.root.left && !x.color){		// do while x is black and not the root of the tree
			if(x == x.parent.left){
				//x is a left child
				RBNode w = x.parent.right;	// w is x's brother
				//case1
				if(w.color){		//w is red, do case1
					w.color = false;	// w becomes black
					x.parent.color = true;	// x.parent becomes red
					leftRotate(x.parent);
					w = x.parent.right;
					switches_cnt += 2;
				}
				//case 2
				if(!w.left.color && !w.right.color){	// both 2 children of w are black - do case2
					
					w.color = true;	// w becomes red
					x = x.parent;
					switches_cnt++;
				} else {
					//case 3 
					if(!w.right.color){			// w.right is red
						// do case3, and then it becomes case4
						w.left.color = false;	// w.left becomes black
						w.color = true;			// w becomes red
						rightRotate(w);	
						w = x.parent.right;
						switches_cnt +=2;
					}
					//case 4
					w.color = x.parent.color;
					x.parent.color = false;		//x.parent become black
					w.right.color = false;		//x.right becomes black
					leftRotate(x.parent);
					x = this.root.left;
					switches_cnt++;
				}
			}
			else{
												//****x is a right child****
				RBNode w = x.parent.left;
				if(w.color){ //w is red
					//case 1
					w.color = false;	//w becomes black
					x.parent.color = true;	//x.parent becomes red
					rightRotate(x.parent);
					w = x.parent.left;
					switches_cnt += 2;
				}if(!w.left.color && !w.right.color){ // both 2 children of w are black - do case2
					//case 2
					w.color = true;	//w becomes red
					x = x.parent;
					switches_cnt++;
				}else
				{	//case3
					if(!w.left.color){ //if w.left is black 
						w.right.color = false;	//w.right becomes black
						w.color = true;			//w becomes red
						leftRotate(w);
						w = x.parent.left;
						switches_cnt +=2;
					}
					//case4
					w.color = x.parent.color;
					x.parent.color = false;		//x.parent becomes black
					w.left.color = false;		//x.left becomes black
					rightRotate(x.parent);
					x = this.root.left;
					switches_cnt++;;
				}
			}
		}
		if (x.color){	// if x is red in the end of the process, we have to make him black
			x.color = false;
			switches_cnt++;
		}

		return switches_cnt;
	}

	/*****************************************************************************
	 *  min & max functions:
	 *****************************************************************************/
	
	/**
	 * public String min()
	 * O(log(n)) complexity - as the height of a Binary tree,
	 * 
	 * @return minimal node value 
	 * or null if the tree is empty
	 */
	public String min() {
		if (this.empty()) {
			return (null);
		} else {
			RBNode pivot = this.root.left;
			while (pivot.left.key != Integer.MIN_VALUE) {
				pivot = pivot.left;
			}
			return (pivot.value);
		}
	}

	/**
	 * private static RBNode minNode(RBNode node) 
	 * O(log(n)) complexity
	 * 
	 * @param node
	 *            - the root of the tree
	 * @return minimal node of the tree
	 */
	public RBNode minNode(RBNode node) {
		if (node.key == Integer.MIN_VALUE) {
			return null;
		}

		// if left node is null so we reached to the min node
		if (node.left.key == Integer.MIN_VALUE) {
			return node;
		}
		return minNode(node.left);
	}

	/**
	 * public String max()
	 * O(log(n)) complexity - as the height of a Binary tree,
	 * 
	 * @return maximal node value
	 * or null if the tree is empty
	 */
	public String max() {
		if (this.empty()) {
			return (null);
		} else {
			RBNode pivot = this.root.left;
			while (pivot.right.key != Integer.MIN_VALUE) {
				pivot = pivot.right;
			}
			return (pivot.value);
		}
	}


	/*****************************************************************************
	 *  keys&values to array functions:
	 *****************************************************************************/

	/**
	 * public int[] keysToArray()
	 *
	 * @Return a sorted array which contains all keys in the tree, or an empty
	 * array if the tree is empty.
	 * 
	 * Works in O(n) complexity: O(log n) for finding the min + 
	 * O(n) to go over the tree (with successors) and copy the nodes' keys to an array 
	 */
	public int[] keysToArray() {
		if (this.empty())
			return new int[0];
		else {
			int[] arr = new int[size];
			arr[0] = minNode(this.root.left).key;
			RBNode pos = minNode(root);
			for (int i = 1; i < this.size(); i++) {
				pos = successor(pos);
				arr[i] = pos.key;
			}
			return arr;
		}
	}


	/**
	 * public String[] valuesToArray()
	 *
	 * @Return an array which contains all values in the tree, sorted by their
	 * respective keys, or an empty array if the tree is empty.
	 * 
	 * Works in O(n) complexity: O(log n) for finding the min +  
	 * O(n) to go over the tree (with successors) and copy the nodes' values to an array
	 */
	public String[] valuesToArray() {
		if (this.size == 0) {
			return new String[0];
		} else {
			String[] arr = new String[this.size()];
			arr[0] = minNode(this.root).value;
			RBNode pos = minNode(root);
			for (int i = 1; i < this.size(); i++) {
				pos = successor(pos);
				arr[i] = pos.value;
			}
			return arr;
		}
	}

	/**
	 * public RBNode successor(RBNode node)
	 *
	 * @Return the successor node of "node" in the tree, if its exist,
	 * or null if it is an empty tree
	 * 
	 * Works in O(log n) complexity
	 */
	public RBNode successor(RBNode node) {
		if (node.right.key != Integer.MIN_VALUE) {
			return (minNode(node.right));
		}
		RBNode parent = node.parent;
		while (parent.key != Integer.MIN_VALUE && node == parent.right) {
			node = parent;
			parent = node.parent;
		}
		return parent;
	}

	/*****************************************************************************
	 *  RBNode Class:
	 *****************************************************************************/
	
	public class RBNode {
		private int key;
		private String value;
		private RBNode left;
		private RBNode right;
		private RBNode parent;
		private boolean color; // black = false , red = true
		
//		public RBNode(int key, String value) {
//			this.key = key;
//			this.value = value;
//			this.left = null;
//			this.right = null;
//			this.parent = null;
//			this.color = true;
//		}
		
		public RBNode(int key, String value , RBNode parent){
			this(key, value, null, null, parent);
			this.left = newNilNode(this);
			this.right = newNilNode(this);
		}
		
		public RBNode(int key, String value, RBNode left, RBNode right, RBNode parent){
			this.key = key;
			this.value = value;
			this.left = left;
			this.right = right;
			this.parent = parent;
			this.color = true; //true = red (a leaf)
				
		}

		
	}

}
