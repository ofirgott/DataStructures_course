/**
 * D-Heap Ronny Sivan (304817471, ronnysivan) Ofir Gottesman (305645806, ofirg1)
 */

public class DHeap {

	private int size, max_size, d;
	private DHeap_Item[] array;

	// Constructor
	// m_d >= 2, m_size > 0
	DHeap(int m_d, int m_size) {
		max_size = m_size;
		d = m_d;
		array = new DHeap_Item[max_size];
		size = 0;
	}

	/**
	 * public int getSize() Getter for the field "size" O(1) complexity
	 */
	public int getSize() {
		return this.size;
	}

	/**
	 * public void arrayToHeap() The function builds a new heap from the given
	 * array.
	 * 
	 * O(n) complexity - cause most of the heapify functions will be O(1) -
	 * leafs, and just few of it will be expensive (root will takes O(log d(n)).
	 * preconidtion: array1.length() <= max_size postcondition: isHeap() size =
	 * array.length()
	 */
	public void arrayToHeap(DHeap_Item[] array1) {
		for (int i = 0; i < array1.length; i++) { // transfer array1 items to
													// this.array
			this.array[i] = new DHeap_Item(array1[i].getName(),
					array1[i].getKey());
			this.array[i].setPos(i);
		}
		for (int j = array1.length; j < this.size; j++) { // update the other
															// cells in
															// this.array to be
															// null
			this.array[j] = null;
		}
		this.size = array1.length; // update this.size
		int child = this.getSize() - 1;
		int pivot = this.parent(child);
		while (pivot > 0) { // heapify down all of the items - from the parent
							// of the leaf towards the root (upwards)
			heapify_Down(pivot);
			child = child - d;
			pivot = this.parent(child);
		}
		heapify_Down(0); // heapify down the root.
	}

	/**
	 * public boolean isHeap()
	 * 
	 * @return true if and only if the D-ary tree rooted at array[0] satisfies
	 *         the heap property or size == 0.
	 * 
	 *         O(n) complexity - cause we go over all of the heap's items.
	 * 
	 */
	public boolean isHeap() {
		if (size == 0) {
			return true;
		} else {
			for (int i = 0; i < size; i++) {
				if (array[i] == null) // if there is hole in the array
					return false;
				if (array[i].getPos() != i)
					return false;
				else { // check for the heap rule
					for (int j = 1; j <= d; j++) {
						if ((child(i, j) < size)
								&& (array[i].getKey() > array[child(i, j)]
										.getKey())) {
							return false;
						}
					}
				}
			}
			return true;
		}
	}

	/**
	 * public int parent(i) O(1) Complexity precondition: i >= 0
	 *
	 * The method compute the index of the parent of vertex i in a complete
	 * D-ary tree stored in an array. 1 <= k <= d. Note that indices of arrays
	 * in Java start from 0.
	 */
	public int parent(int i) {
		return ((i - 1) / d);
	}

	/**
	 * public int child(i,k) O(1) Complexity precondition: i >= 0
	 *
	 * The method compute the index of the k-th child of vertex i in a complete
	 * D-ary tree stored in an array. 1 <= k <= d. Note that indices of arrays
	 * in Java start from 0.
	 */
	public int child(int i, int k) {
		return (d * i + k);
	}

	/**
	 * public void Insert(DHeap_Item item) The method insert a new element to
	 * the end of the heap, and later Heapify it up (to keep the "heap rule").
	 * O(log d (n)) complexity - as the height of the heap (WC - insert a new
	 * root).
	 * 
	 * precondition: item != null isHeap() size < max_size postcondition:
	 * isHeap()
	 */
	public void Insert(DHeap_Item item) {
		array[size] = item; // insert the item in the end oof the array
		array[size].setPos(size);
		this.size++; // update this.size field
		heapify_Up(size - 1); // heapify up the new item - in case the heap rule
								// is broken
	}

	/**
	 * private void heapify_Up(int i) The method switch son-father locations,
	 * until the "heap rule" is ordered. O(log d (n)) complexity - as the height
	 * of the heap.
	 * 
	 * precondition: i>0 isHeap() size < max_size postcondition: isHeap()
	 */

	private void heapify_Up(int i) {
		while ((i > 0) && (array[i].getKey() < array[parent(i)].getKey())) { // if
																				// the
																				// heap
																				// rule
																				// is
																				// broken:
			switch_Items(array[i], array[parent(i)]); // replace places of
														// father and son
			i = parent(i);
		}
	}

	/**
	 * switches 2 DHeap_Items positions in array. O(1) Complexity.
	 */
	private void switch_Items(DHeap_Item item1, DHeap_Item item2) {
		int pos1 = item1.getPos();
		item1.setPos(item2.getPos());
		item2.setPos(pos1);
		array[item1.getPos()] = item1;
		array[item2.getPos()] = item2;
	}

	/**
	 * public void Delete_Min() Replace the root with the last item of the
	 * array, and Heapify it down. Complexity O(log d (n)) - as the height of
	 * the heap.
	 * 
	 * precondition: size > 0 isHeap() postcondition: isHeap()
	 */
	public void Delete_Min() {
		array[0] = array[this.size - 1]; // pus the last item in the root's
											// place
		array[this.size - 1] = null; // delete the last item
		this.size--; // update the size field
		heapify_Down(0); // heapifyDown the new root - to keep the heap rule
	}

	/**
	 * private void heapify_Down (int i) The method switch father - minimal-son
	 * locations, until the "heap rule" is ordered. O(d * log d (n)) complexity
	 * - cause we need to find the minimal son in every level of the height of
	 * the heap (and there is log d (n) levels).
	 * 
	 * precondition: i>=0 isHeap() size < max_size postcondition: isHeap()
	 */
	private void heapify_Down(int i) {
		int min_Child_Pos = findMinChild(i);
		if (min_Child_Pos == -1) { // if no children for i item - it is a leaf -
									// no need to heapify.
			return;
		}
		while ((i < size)
				&& (array[min_Child_Pos].getKey() < array[i].getKey())) { // if
																			// the
																			// heap
																			// rule
																			// is
																			// broken
			switch_Items(array[i], array[min_Child_Pos]); // replace the father
															// item with its
															// minimal child
			i = min_Child_Pos;
			min_Child_Pos = findMinChild(i); // find the new minimal child
			if (min_Child_Pos == -1) // i is a leaf.
				return;
		}
	}

	/**
	 * private int findMinChild (int i)
	 * 
	 * @return the pos of the minimal child of the i'th element. O(d) complexity
	 *         - as the number of sons to be compared.
	 * 
	 *         precondition: i>=0 isHeap() size < max_size postcondition:
	 *         isHeap()
	 */

	private int findMinChild(int i) {
		if (child(i, 1) >= this.size) { // if there's no 1st child for i - it is
										// a leaf!
			return (-1);
		}
		int min_Key = array[child(i, 1)].getKey();
		int min_pos = child(i, 1);
		for (int j = 2; j <= d; j++) {
			if (child(i, j) < size && array[child(i, j)].getKey() < min_Key) {
				min_Key = array[child(i, j)].getKey();
				min_pos = child(i, j);
			}
		}
		return min_pos;
	}

	/**
	 * public DHeap_Item Get_Min()
	 * 
	 * @return the first item in the array - the minimum item, the root of the
	 *         heap. Complexity: O(1).
	 * 
	 *         precondition: heapsize > 0 isHeap() size > 0 postcondition:
	 *         isHeap()
	 * 
	 */
	public DHeap_Item Get_Min() {
		return array[0];
	}

	/**
	 * public void Decrease_Key(DHeap_Item item, int delta) Decrease the item
	 * key by delta, and if its not the root - w'll Heapify it Up. Complexity:
	 * O(log d(n)) as the height of the tree (can reach to the root eventually).
	 * 
	 * precondition: item.pos < size; item != null isHeap() postcondition:
	 * isHeap()
	 */
	public void Decrease_Key(DHeap_Item item, int delta) {
		item.setKey(item.getKey() - delta); // reduce the key value by delta
		if (item.getPos() > 0) { // if the item is not the root
			this.heapify_Up(item.getPos());
		}
	}

	/**
	 * public void Delete(DHeap_Item item) We'll replace the element we want to
	 * delete with the last item in the array, and Heapify down it. Complexity:
	 * O(d * log d(n)); in every level of the tree (log d (n) height) - we need
	 * to choose from d optional sons.
	 * 
	 * precondition: item.pos < size; item != null isHeap() postcondition:
	 * isHeap()
	 */

	public void Delete(DHeap_Item item) {
		int pos = item.getPos();
		switch_Items(array[this.size - 1], array[pos]);
		this.array[this.size - 1] = null;
		this.size--;
		heapify_Down(pos);
	}

	/**
	 * public static int[] DHeapSort(int[] array) We'll "throw" the items in to
	 * a heap, and in a series of n Delete_Min actions we will get an ordered
	 * array of keys. Complexity: O(n * log d(n)) complexity - n times
	 * Delete_min
	 * 
	 * @return a sorted array containing the same integers in the input array.
	 */

	public static int[] DHeapSort(int[] array) {
		DHeap dheap = new DHeap(2, array.length); // create a new DHeap

		for (int i = 0; i < array.length; i++) { // create a Dheap_item array
													// for the heap
			DHeap_Item item = new DHeap_Item(null, array[i]);
			item.setPos(i);
			dheap.array[i] = item;
		}
		dheap.arrayToHeap(dheap.array); // insert the DHeap_item array to the
										// heap, in the correct order
		int[] ret = new int[dheap.size];
		int pivot = 0;
		while (dheap.size > 0) {
			ret[pivot] = dheap.Get_Min().getKey();
			dheap.Delete_Min();
			pivot++;
		}
		return ret;
	}

}