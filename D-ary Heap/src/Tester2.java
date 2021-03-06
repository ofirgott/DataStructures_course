import java.util.Arrays;
import java.util.Random;

public class Tester2 {
	public static void main(String[] args) {
		int d = Integer.parseInt(args[0]);
		 //test2(d,100); // inserting;
		test3(); // test heapify down
		//test4(3,20);
	}
	
	
	public static void test4(int d, int size){
		String orig = "";
		DHeap testHeap = new DHeap(d, size);
		Random rnd = new Random();
		int length;
		for(int i=0; i<15; i++){
			length = rnd.nextInt(150);
			DHeap_Item[] itemArr = createItemArray(size);
			testHeap.arrayToHeap(itemArr);
			System.out.println(testHeap.isHeap());
			System.out.println(testHeap);
			System.out.println();
			
			//printHeap(testHeap);
			System.out.println(testHeap.isHeap());
		}
		
		
	}
	

	private static DHeap_Item[] createItemArray(int length) {
		String orig ="" ;
		DHeap_Item[] itemArr = new DHeap_Item[length];
		Random rnd = new Random();
		int key;
		int[] tosort = new int[length];
		int cnt=0;
		for (int i = 0; i < length; i++) {
			key = rnd.nextInt(20);
			orig = orig +  "," + key;
			tosort[cnt] = key;
			cnt++;
			DHeap_Item item1 = new DHeap_Item("i", key);
			itemArr[i] = item1;
		}
		System.out.println(orig);
		System.out.println("sorted array:   " + Arrays.toString(DHeap.DHeapSort(tosort)));
		return itemArr;
	}


	public static DHeap test2(int d, int size) {
		DHeap testHeap = new DHeap(d, size);
		// insert 20 elements with random keys to an empty heap.
		DHeap_Item item1;
		int index;
		Random rnd = new Random();
		for (int i = 0; i < size; i++) {
			index = rnd.nextInt(20);
			item1 = new DHeap_Item("i", index);
			testHeap.Insert(item1);
		}

		System.out.println("Before: ");
		printHeap(testHeap);
		System.out.println("size: "+testHeap.getSize());
		System.out.println("Is heap? " + testHeap.isHeap());
		return testHeap;

	}

	public static void printHeap(DHeap testHeap) {
		DHeap_Item[] itemArr = testHeap.array;
		for (int i = 0; i < itemArr.length; i++) {
			if (itemArr[i] == null)
				System.out.print(" null ");
			else
				System.out.print(itemArr[i].getKey() + " ");
		}
		System.out.println(" ");
		System.out.println("Actual size: " + testHeap.getSize());
		// System.out.println("min key: "+testHeap.Get_Min().getKey());
	}

	public static void test3() {
		DHeap test = test2(4,43);
		Random rnd = new Random();
		int index;
		for(int i=1; i<=1; i++){
			index = rnd.nextInt(test.getSize());
			System.out.println("try to delete: "+index);
			test.Delete(test.array[index]);
			System.out.println("try to delet min - key:" + );
			System.out.println(test);
			System.out.println("size: "+test.getSize());
			System.out.println("isheap? : "+test.isHeap());
		}
	}

	public static void test1() {
		DHeap testHeap = new DHeap(2, 7);
		System.out.println(testHeap.isHeap());
		DHeap_Item[] itemArr = new DHeap_Item[7];
		for (int i = 0; i < 7; i++) {
			DHeap_Item item = new DHeap_Item("555", i);
			itemArr[i] = item;
		}
		Shuffle(itemArr);
		System.out.println(testHeap.isHeap());
		testHeap.arrayToHeap(itemArr);
		System.out.println(testHeap.isHeap());

	}

	static void Shuffle(DHeap_Item[] arr) {
		Random rnd = new Random();
		for (int i = arr.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			DHeap_Item a = arr[index];
			arr[index] = arr[i];
			arr[i] = a;
		}
	}

}
