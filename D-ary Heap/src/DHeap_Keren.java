import java.util.Arrays;

/**
 * D-Heap
 */

public class DHeap {

	private int size, max_size, d;
	private DHeap_Item[] array;
	public static int   comparisons=0;

	// Constructor
	// m_d >= 2, m_size > 0
	DHeap(int m_d, int m_size) {
		max_size = m_size;
		d = m_d;
		array = new DHeap_Item[max_size];
		size = 0;

		
	}

	// Getter for size
	public int getSize() {
		return size;
	}

	/**
	 * public void arrayToHeap()
	 *
	 * The function builds a new heap from the given array. Previous data of the heap should be erased. preconidtion: array1.length() <= max_size postcondition:
	 * isHeap() size = array.length()
	 */
	public void arrayToHeap(DHeap_Item[] array1) {
		int i = 0;
		while (i < array1.length) { // update the positions of the DHeap_Items to their index in the array
			this.array[i] = new DHeap_Item(array1[i].getName(), array1[i].getKey());
			this.array[i].setPos(i);
			i++;
		}

		while (i < this.getSize()) { // clear the rest of the array of the old DHeap_Items of the original heap
			this.array[i] = null;
			i++;
		}

		this.size = array1.length; // update the heap's size to the length of array1
		int child = this.getSize() - 1; // set the fist child to be the DHeap_Item in the last index of the array
		int handled = this.parent(child); // set the hanlded node to be the child's parent
		while (handled > 0) { // heapify down the parents of each series of children from the bottom to the top
			Heapify_Down(handled);
			child = child - d; // after d DHeap_Items, we will encounter a child from another group of children
			handled = this.parent(child);
		}
		Heapify_Down(0); // lastly, heapify down the root 
	}

	/**
	 * public boolean isHeap()
	 *
	 * The function returns true if and only if the D-ary tree rooted at array[0] satisfies the heap property or size == 0.
	 * 
	 */
	public boolean isHeap() {
		if (this.size == 0) {
			return true;
		}

		if (array[0].getPos() != 0) {
			return false;
		}

		for (int i = 0; i < size - 1; i++) {
			// cond 1 - cell i should contain a DHeap_Item
			// cond 2 - calls help method that checks whether the current node is smaller than it's children
			if ((array[i] == null) || (!this.isParentLegal(i))) {
				return false;
			}
		}

		for (int j = size; j < max_size; j++) {
			if (array[j] != null) {
				return false;
			}
		}

		return true;
	}

	// checks whether all the children of parent are bigger than him
	// and whether their "pos" value == actual position in the array
	private boolean isParentLegal(int parent) {
		for (int j = 1; j < d; j++) {
			int jChild = child(parent, j);
			if (jChild < size) {
				if ((array[jChild].getPos() != jChild) || (array[jChild].getKey() < array[parent].getKey())) {
					return false;
				}
			} else { // parent has no more children
				break;
			}
		}
		return true;
	}

	/**
	 * public int parent(i), child(i,k) (2 methods)
	 *
	 * precondition: i >= 0
	 *
	 * The methods compute the index of the parent and the k-th child of vertex i in a complete D-ary tree stored in an array. 1 <= k <= d. Note that indices of
	 * arrays in Java start from 0.
	 */
	public int parent(int i) {
		if (i == 0) {
			return 0;
		}
		return (int) Math.floor((i - 1) / d);
	}

	public int child(int i, int k) {
		return (d * i + (k));
	}

	/**
	 * public void Insert(DHeap_Item item)
	 *
	 * precondition: item != null isHeap() size < max_size
	 * 
	 * postcondition: isHeap()
	 */
	public void Insert(DHeap_Item item) {
		this.array[this.getSize()] = item; // insert the DHeap_Item at the end of the array
		item.setPos(this.getSize()); // update the position of the DHeap_Item accordingly
		this.size++; // raise the Heap's size by 1
		this.Heapify_Up(this.getSize() - 1); // fix the Heap by heapifying up the inserted DHeap_Item
	}

	/**
	 * public void Delete_Min()
	 *
	 * precondition: size > 0 isHeap()
	 * 
	 * postcondition: isHeap()
	 */
	public void Delete_Min() {
		if (size == 1) {
			array[0] = null;
			size = 0;
		} else {
			array[0] = array[size - 1];
			array[size - 1] = null;
			array[0].setPos(0);
			size--;
			Heapify_Down(0);
		}
	}

	/**
	 * public String Get_Min()
	 *
	 * precondition: heapsize > 0 isHeap() size > 0
	 * 
	 * postcondition: isHeap()
	 */
	public DHeap_Item Get_Min() {
		return this.array[0]; // the mimimum item is always the root of the heap - which is pointed to by the first index in the array accordingly
	}

	/**
	 * public void Decrease_Key(DHeap_Item item, int delta)
	 *
	 * precondition: item.pos < size; item != null isHeap()
	 * 
	 * postcondition: isHeap()
	 */
	public void Decrease_Key(DHeap_Item item, int delta) {
		item.setKey(item.getKey() - delta); // set the item's key to be the former key - delta
		this.Heapify_Up_for_dec(item.getPos()); // heapify up the node with the decreased key
	}

	private void Heapify_Up_for_dec(int nodePosition) {
		DHeap_Item node = this.array[nodePosition];
		DHeap_Item parent = this.array[this.parent(nodePosition)]; // find the parent of the problematic node
		//comparisons = comparisons + 1; // JUST FOR MEASUREMENTS!!
		if ((nodePosition == 0))
			return;
		comparisons = comparisons + 1; // JUST FOR MEASUREMENTS!!
		while ((parent.getKey() > node.getKey())) {
			
			swap(node, parent);
			parent = this.array[this.parent(node.getPos())]; // update the parent of the node accordingly
			comparisons = comparisons + 1; // JUST FOR MEASUREMENTS!!
		}
	}


	/**
	 * public void Delete(DHeap_Item item)
	 *
	 * precondition: item.pos < size; item != null isHeap()
	 * 
	 * postcondition: isHeap()
	 */
	public void Delete(DHeap_Item item) {
		int itemPos = item.getPos();
		if(itemPos == size - 1) {
			array[size - 1] = null;
			size--;
		} else {
			array[itemPos] = array[size - 1];
			array[size - 1] = null;
			array[itemPos].setPos(itemPos);
			size--;
			
			if(array[itemPos].getKey() < array[parent(itemPos)].getKey()) {
				Heapify_Up(itemPos);
			} else {
				Heapify_Down(itemPos);
			}
		}
	}

	public void Delete2(DHeap_Item item) {
		item.setKey(Integer.MIN_VALUE);
		this.Heapify_Up(item.getPos());
		this.Delete_Min();
	}

	/**
	 * Return a sorted array containing the same integers in the input array. Sorting should be done using the DHeap.
	 */
	public static int[] DHeapSort(int[] array, int d) { // NEED TO CHANGE D TO 2 AND CHANGE SIGNATURE
		DHeap heap = new DHeap(d, array.length); // create a new DHeap, where d = 2
		DHeap_Item[] heapArray = new DHeap_Item[array.length]; // create a new arrat of DHeap_Items, in which the keys of the items are the values of the given
																// array
		for (int i = 0; i < array.length; i++) {
			heapArray[i] = new DHeap_Item("dummyName", array[i]);
		}

		heap.arrayToHeap(heapArray); // turn the created array into an array of a legal 2Heap
		int[] sortedArray = new int[array.length];
		int candidate = 0;
		for (int j = 0; j < array.length; j++) { // for each index j in the new sorted array
			candidate = heap.Get_Min().getKey(); // get the key of the minimum item in the heap
			sortedArray[j] = candidate; // insert it to the sorted array and the current index j
			heap.Delete_Min(); // delete the minimum item, so the next minimum item in the heap will be updated accordigly
		}
		return sortedArray;
	}

	/**
	 * HELP FUNCTIONS
	 */

	/**
	 * Given a problematic DHeap_Item's position -> climb up the DHeap until the DHeap_Item is in a legal position
	 */
	private void Heapify_Up(int nodePosition) {
		DHeap_Item node = this.array[nodePosition];
		DHeap_Item parent = this.array[this.parent(nodePosition)]; // find the parent of the problematic node
		//comparisons = comparisons + 1; // JUST FOR MEASUREMENTS!!
		if ((nodePosition == 0))
			return;
		//comparisons = comparisons + 1; // JUST FOR MEASUREMENTS!!
		while ((nodePosition >= 0) && (parent.getKey() > node.getKey())) {
			
			swap(node, parent);
			parent = this.array[this.parent(node.getPos())]; // update the parent of the node accordingly
			//comparisons = comparisons + 1; // JUST FOR MEASUREMENTS!!
		}
	}

	/**
	 * Given a problematic DHeap_Item's position -> go down the DHheap until the DHeap_Item is in a legal position
	 */
	private void Heapify_Down(int nodePosition) {
		int k = 1;
		DHeap_Item node = this.array[nodePosition];
		if (node != null) {
			boolean replaced = false;
			DHeap_Item child = null;
			DHeap_Item smallest = this.array[nodePosition];

			while (k <= this.d) { // go over all the node's children, and update the smallest node's found in the variable "smallest"
				int childIndex = this.child(nodePosition, k);
				if (childIndex >= this.size) {
					break;
				}
				child = this.array[childIndex];
				if (child != null) {
					if ((child.getPos() < this.getSize()) && (smallest.getKey() > child.getKey())) {
						smallest = child;
						replaced = true;
					}
					//comparisons = comparisons + 1; // JUST FOR MEASUREMENTS!!
					
				}
				k++;
			}
			if (replaced) { // if the smallest node is indeed not the parent -> swap the parent with the smallest node and heapift down
														// the
				// smallest (which is now the paret)
				swap(node, smallest);
				Heapify_Down(node.getPos());
			}
		}
	}

	/**
	 * Given two DHeap_Items, swap their positions
	 */
	private void swap(DHeap_Item node1, DHeap_Item node2) {
		int pos1 = node1.getPos(); // swap the nodes' positions
		node1.setPos(node2.getPos());
		node2.setPos(pos1);
		this.array[node1.getPos()] = node1; // update the DHeap's array
		this.array[node2.getPos()] = node2;
	}

	// MY ADDITION FOR TESTING
	public DHeap_Item[] getArray() {
		return this.array;
	}

	public void heapPrint() {
		DHeap_Item[] heapArr = getArray();
		if (this.size != 0) {
			for (int i = 0; i < size; i++) {
				System.out.format("pos = %d, key = %d, name = %s%n", heapArr[i].getPos(), heapArr[i].getKey(), heapArr[i].getName());
			}
		} else {
			System.out.println("the heap is empty");
		}
	}

	public void printChildren(int index) {
		DHeap_Item[] children = new DHeap_Item[this.d];
		for (int i = 1; i <= d; i++) {
			int ci = this.child(index, i);
			if (ci < this.getSize()) {
				children[i - 1] = this.getArray()[this.child(index, i)];
			}
		}
		System.out.println(Arrays.toString(children));
	}

	public int getD() {
		return d;
	}

}