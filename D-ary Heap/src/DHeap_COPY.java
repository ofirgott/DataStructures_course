/**
 * D-Heap
 */

public class DHeap_COPY {

	private int size, max_size, d;
	private DHeap_Item[] array;

	// Constructor
	// m_d >= 2, m_size > 0
	DHeap_COPY(int m_d, int m_size) {
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
	 * The function builds a new heap from the given array. Previous data of the
	 * heap should be erased. preconidtion: array1.length() <= max_size
	 * postcondition: isHeap() size = array.length()
	 */
	public void arrayToHeap(DHeap_Item[] array1) {
		array = new DHeap_Item[max_size];
		size = 0;
		for (DHeap_Item item : array1) {
			this.Insert(item);
		}

	}

	/**
	 * public boolean isHeap()
	 *
	 * The function returns true if and only if the D-ary tree rooted at
	 * array[0] satisfies the heap property or size == 0.
	 * 
	 */
	public boolean isHeap() {
		if (size == 0) {
			return true;
		} else {
			for (int i = 0; i < size; i++) {
				if (array[i].getPos() != i)
					return false;
				else {
					for (int j = 1; j <= d; j++) {
						if ((d*i)+j < size || array[i].getKey() > array[(d * i) + j].getKey()) {
							return false;
						}
					}
				}
			}
			return true;
		}
	}

	/**
	 * public int parent(i), child(i,k) (2 methods)
	 *
	 * precondition: i >= 0
	 *
	 * The methods compute the index of the parent and the k-th child of vertex
	 * i in a complete D-ary tree stored in an array. 1 <= k <= d. Note that
	 * indices of arrays in Java start from 0.
	 */
	public int parent(int i) {
		return ((i - 1) / d);
	}

	public int child(int i, int k) {
		return (d * i) + k;
	}

	/**
	 * public void Insert(DHeap_Item item)
	 *
	 * precondition: item != null isHeap() size < max_size
	 * 
	 * postcondition: isHeap()
	 */
	public void Insert(DHeap_Item item) {
		array[size] = item;
		array[size].setPos(size);
		heapify_Up(size);
		size++;	
	}
	
	private void heapify_Up(int i) {
		
		while (i<0 && (array[i].getKey() < array[parent(i)].getKey())){
			switch_Items(i,parent(i));
			i = parent(i);
		}
		
	}



	private void switch_Items(int i, int parent) {
		DHeap_Item temp = new DHeap_Item(array[i].getName(), array[i].getKey());
		temp.setPos(array[i].getPos());
		array[i] = array[parent(i)];
		array[parent(i)] = temp;
		array[i].setPos(i);
		array[parent(i)].setPos(parent(i));
	}

	/**
	 * public void Delete_Min()
	 *
	 * precondition: size > 0 isHeap()
	 * 
	 * postcondition: isHeap()
	 */
	public void Delete_Min() {
		array[0] = array[size-1];
		size--;
		array[0].setPos(0);
		heapify_Down(0);
	}

	private void heapify_Down(int i) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * public String Get_Min()
	 *
	 * precondition: heapsize > 0 isHeap() size > 0
	 * 
	 * postcondition: isHeap()
	 */
	public DHeap_Item Get_Min() {
		return array[0];
	}

	/**
	 * public void Decrease_Key(DHeap_Item item, int delta)
	 *
	 * precondition: item.pos < size; item != null isHeap()
	 * 
	 * postcondition: isHeap()
	 */
	public void Decrease_Key(DHeap_Item item, int delta) {
		return;// should be replaced by student code
	}

	/**
	 * public void Delete(DHeap_Item item)
	 *
	 * precondition: item.pos < size; item != null isHeap()
	 * 
	 * postcondition: isHeap()
	 */
	public void Delete(DHeap_Item item) {
		switch_Items(0, item.getPos());
		array[0].setKey(Integer.MIN_VALUE);
		Delete_Min();
		heapify_Up(size-1);
		size--;
	}

	/**x
	 * Return a sorted array containing the same integers in the input array.
	 * Sorting should be done using the DHeap.
	 */
	public static int[] DHeapSort(int[] array) {
		return;
	}
}
