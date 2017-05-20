
public class BinomialHeap_noamweinman_orlakritz {

	int n;
	HeapNode first = null;
	HeapNode min = null;

	// ///////////////////// begin help methods ///////////////////////

	/**
	 * public static HeapNode findMinAfterOperetion(BinomialHeap tree) {
	 *
	 * return: node with minimum key in the heap.
	 * 
	 */

	public static HeapNode findMinAfterOperetion(BinomialHeap_noamweinman_orlakritz heap) {
		HeapNode current = heap.first;
		HeapNode min = current;
		while (current != null) { // no more roots to check.
			if (current.key < min.key) {
				min = current;
			}
			current = current.next;
		}
		return min;
	}

	/**
	 * public static HeapNode Link(HeapNode node1, HeapNode node2)
	 *
	 * return: the minimum node after linking the the two nodes according to
	 * key.
	 * 
	 */

	public static HeapNode Link(HeapNode node1, HeapNode node2) {
		if (node1.key > node2.key) { // node1 is the father.
			HeapNode temp = node2.child;
			node2.child = node1;
			node1.next = temp;
			node2.rank++;
			return node2;
		}
		HeapNode temp = node1.child; // node2 is the father.
		node1.child = node2;
		node2.next = temp;
		node1.rank++;
		return node1;
	}

	/**
	 * public static boolean nodeIsValid(HeapNode node, HeapNode father)
	 *
	 * return: recursive help function to isValid(). return true if node and his
	 * successors are valid binomial trees. false otherwise.
	 * 
	 */

	public static boolean nodeIsValid(HeapNode node, HeapNode father) {
		if (node == null) { // father have no children
			return true;
		}
		if (node.key < father.key) { // key order is wrong
			return false;
		}
		HeapNode current = node;
		int lastRank = father.rank - 1; // children goes down in ranks.
		while (current != null) {
			if (current.rank != lastRank) {
				return false;
			}
			if (!nodeIsValid(current.child, current)) { // recursive call to the
														// current child.
				return false;
			}
			lastRank--;
			current = current.next;
		}
		return true;
	}

	/**
	 * public static void meldForInsert(BinomialHeap heap1, HeapNode inserted)
	 *
	 * Meld the heap with the new inserted node (specific case for insert
	 * operation).
	 * 
	 */

	public static void meldForInsert(BinomialHeap_noamweinman_orlakritz heap1, HeapNode inserted) {
		HeapNode node = heap1.first;
		HeapNode newTree = inserted;
		HeapNode temp;
		while (node != null && node.rank == newTree.rank) {
			temp = node.next;
			newTree = Link(node, newTree);
			node = temp;
		}
		newTree.next = node;
		heap1.first = newTree;
		heap1.n++;
		if (heap1.min.key > inserted.key) {
			heap1.min = newTree;
		}
	}

	// ///////////////////// end help methods ///////////////////////

	/**
	 * public boolean empty()
	 *
	 * precondition: none
	 * 
	 * The method returns true if and only if the heap is empty.
	 * 
	 */
	public boolean empty() {
		if (n == 0) {
			return true;
		}
		return false;
	}

	/**
	 * public void insert(int value)
	 *
	 * Insert value into the heap
	 *
	 */

	public void insert(int value) {
		HeapNode node = new HeapNode(value); // creating new node to insert with
												// key- value.
		if (this.empty()) { // if the heap is empty, node is first and minimum
							// in heap.
			this.first = node;
			this.min = node;
			this.n++;
		} else {
			meldForInsert(this, node); // meld the new node with the heap.
		}
	}

	/**
	 * public void deleteMin()
	 *
	 * Delete the minimum value
	 *
	 */
	public void deleteMin() {
		HeapNode node = this.first;
		HeapNode prev = null; // the previous node two the min.
		while (node.key != this.min.key) { // finding the min key and his prev.
			prev = node;
			node = node.next;
		}
		if (prev == null) { // the deleted node is root of first tree.
			this.first = node.next;
		} else {
			prev.next = node.next; // connecting previous node to the older
									// brother of min.
		}
		node = node.child; // from now we are handling the childs of deleted
							// node.
		if (this.first == null && node == null) { // node deleted is the only
													// node in tree
			System.out.println("only 1 tree, 1 node");
			this.n = 0;
			this.min = null;
		} else {
			if (this.first == null && node != null) { // node deleted is the
														// only tree in heap and
														// have children
				this.first = node.reverse(null); // change order of child's.
				this.n--;
				this.min = findMinAfterOperetion(this);
			} else {
				if (this.first != null && node == null) { // node is not the
															// only tree in heap
															// but have no
															// children
					this.n--;
					this.min = findMinAfterOperetion(this);
				} else {// node is not the only tree in heap and
						// have children
					BinomialHeap_noamweinman_orlakritz heap2 = new BinomialHeap_noamweinman_orlakritz(); // creating new
																// heap
																// containing
																// the
																// child's of
																// deleted.
					heap2.first = node.reverse(null); // change order of
														// child's.
					heap2.min = findMinAfterOperetion(heap2); // Determine the
																// fields of new
																// heap
					heap2.n = (int) (Math.pow(2, node.rank + 1) - 1);
					this.n -= (heap2.n + 1); // Determine the fields of old heap
												// after taking out all child's
												// and
												// deleted node.
					this.min = findMinAfterOperetion(this);
					this.meld(heap2); // meld new heap with old one.
				}
			}
		}
	}

	/**
	 * public int findMin()
	 *
	 * Return the minimum value
	 *
	 */

	public int findMin() {
		return this.min.key;
	}

	/**
	 * public void meld (BinomialHeap heap2)
	 *
	 * Meld the heap with heap2
	 *
	 */

	public void meld(BinomialHeap_noamweinman_orlakritz heap2) {
		if (this.empty()) {
			this.first = heap2.first;
			this.min = heap2.min;
			this.n = heap2.n;
		} else {
			if (!heap2.empty()) {
				boolean[] arr1 = this.binaryRep(); // creating arrays to
													// represent
													// ranks
													// trees in heap (like
													// binary
													// numbers).
				boolean[] arr2 = heap2.binaryRep(); // true = 1, false = 0.
				int maxLength = (arr1.length < arr2.length) ? arr2.length
						: arr1.length;
				int minLength = (arr1.length > arr2.length) ? arr2.length
						: arr1.length;
				boolean[] arr3 = new boolean[maxLength + 1]; // will represent
																// carries
																// in vertical
																// addition.
				HeapNode[] newNodes = new HeapNode[arr3.length]; // will get the
																	// new
																	// nodes of
																	// new
																	// heap
																	// before
																	// connecting.
				HeapNode current1 = this.first; // pointer to heap1
				HeapNode current2 = heap2.first; // pointer to heap2.
				HeapNode newNode = null; // pointer to the carry node.
				HeapNode temp1 = null;
				HeapNode temp2 = null;
				for (int i = 0; i < minLength; i++) {
					// 8 cases to add three digits:
					if (!arr1[i] && !arr2[i] && !arr3[i]) {
						continue;
					}
					if (!arr1[i] && !arr2[i] && arr3[i]) {
						newNodes[i] = newNode;
					}
					if (!arr1[i] && arr2[i] && !arr3[i]) {
						newNodes[i] = current2; // adding the carry to the new
												// heap
												// array.
						current2 = current2.next;
					}
					if (!arr1[i] && arr2[i] && arr3[i]) {
						temp2 = current2.next; // keeping the brother because
												// current2
												// is changing in link.
						newNode = Link(current2, newNode);
						arr3[i + 1] = true; // there is a carry to the next
											// check.
						current2 = temp2;
					}
					if (arr1[i] && !arr2[i] && !arr3[i]) {
						newNodes[i] = current1;
						current1 = current1.next;
					}
					if (arr1[i] && !arr2[i] && arr3[i]) {
						temp1 = current1.next;
						newNode = Link(current1, newNode);
						arr3[i + 1] = true;
						current1 = temp1;
					}
					if (arr1[i] && arr2[i] && !arr3[i]) {
						temp1 = current1.next;
						temp2 = current2.next;
						newNode = Link(current1, current2);
						arr3[i + 1] = true;
						current1 = temp1;
						current2 = temp2;
					}
					if (arr1[i] && arr2[i] && arr3[i]) {
						temp1 = current1.next;
						temp2 = current2.next;
						newNodes[i] = newNode;
						newNode = Link(current1, current2);
						arr3[i + 1] = true;
						current1 = temp1;
						current2 = temp2;
					}
				}
				boolean[] arr;
				HeapNode current;
				if (maxLength == arr1.length) { // checking which heap is bigger
												// to
												// proceed with it.
					arr = arr1;
					current = current1;
				} else {
					arr = arr2;
					current = current2;
				}
				for (int j = minLength; j < maxLength; j++) { // handling
																// only
																// carries
																// and max
																// array.
					if (!arr[j] && !arr3[j]) {
						continue;
					}
					if (!arr[j] && arr3[j]) {
						newNodes[j] = newNode;
					}
					if (arr[j] && !arr3[j]) {
						newNodes[j] = current;
						current = current.next;
					}
					if (arr[j] && arr3[j]) {
						temp1 = current.next;
						newNode = Link(current, newNode);
						arr3[j + 1] = true;
						current = temp1;
					}
				}
				if (arr3[arr3.length - 1] == true) { // checking if there is a
														// last
														// carry to add.
					newNodes[newNodes.length - 1] = newNode;
				}
				for (int i = 0; i < newNodes.length; i++) { // finding first
															// rank of
															// the
															// new heap.
					if (newNodes[i] != null) {
						this.first = newNodes[i];
						break;
					}
				}
				HeapNode node = this.first;
				HeapNode min = this.first;
				for (int i = 0; i < newNodes.length; i++) { // connecting all
															// new
															// trees
															// to create the
															// melded
															// heap.
					if (newNodes[i] != null) {
						if (newNodes[i].key < min.key) { // finding the minimum
															// node
															// in
															// the heap.
							min = newNodes[i];
						}
						node.next = newNodes[i];
						node = node.next;
					}
				}
				node.next = null;
				this.n += heap2.n;
				this.min = (this.min.key < heap2.min.key) ? this.min
						: heap2.min;
			}
		}
	}

	/**
	 * public int size()
	 *
	 * Return the number of elements in the heap
	 * 
	 */
	public int size() {
		return this.n;
	}

	/**
	 * public int minTreeRank()
	 *
	 * Return the minimum rank of a tree in the heap.
	 * 
	 */

	public int minTreeRank() {
		if (this.empty()) {
			return -1;
		}
		return this.first.rank;
	}

	/**
	 * public boolean[] binaryRep()
	 *
	 * Return an array containing the binary representation of the heap.
	 * 
	 */

	public boolean[] binaryRep() {
		String binaryNum = Integer.toString(this.n, 2); // representing number
														// of nodes as a binary
														// number.
		boolean[] result = new boolean[binaryNum.length()];
		for (int i = 0; i < binaryNum.length(); i++) {
			if (binaryNum.charAt(binaryNum.length() - i - 1) == '1') {
				// if char is '1' so heap contains a tree in this rank.
				result[i] = true;
			}
		}
		return result;
	}

	/**
	 * public void arrayToHeap()
	 *
	 * Insert the array to the heap. Delete previous elemnts in the heap.
	 * 
	 */
	public void arrayToHeap(int[] array) {
		this.first = null;
		this.min = null;
		this.n = 0;
		for (int x : array) {
			this.insert(x);
		}
	}

	/**
	 * public boolean isHeap()
	 *
	 * Returns true if and only if the heap is valid.
	 * 
	 */
	public boolean isValid() {
		HeapNode current = this.first;
		int lastRank = -1;
		while (current != null) {
			if (current.rank < lastRank) { // checking order of childs ranks.
				return false;
			}
			if (!nodeIsValid(current.child, current)) { // calling recursive
														// help function that
														// determine if child is
														// a valid binomial
														// tree.
				return false;
			}
			lastRank = current.rank;
			current = current.next;
		}
		return true;
	}

	/**
	 * public class HeapNode
	 * 
	 * If you wish to implement classes other than BinomialHeap (for example
	 * HeapNode), do it in this file, not in another file
	 * 
	 */
	public class HeapNode {

		int key;
		HeapNode child = null;
		HeapNode next = null;

		public HeapNode(int key) { // constructor
			this.key = key;
		}

		// /////////////// begin help methods of HeapNode /////////////////
		public HeapNode reverse(HeapNode brother) {
			HeapNode temp;
			if (this.next != null) { // if not last brother so we call function
										// again.
				temp = this.next.reverse(this);
			} else {
				temp = this;
			}
			this.next = brother;
			return temp;
		}

		// /////////////// end help methods of HeapNode /////////////////
	}

}
