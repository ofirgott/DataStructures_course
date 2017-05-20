import java.util.Random;

public class DHeap_Measurements_Tester_Updated {

	public static void main(String[] args) {
		int[] size = { 1000, 10000, 1000000 };
		int[] dValue = { 2, 3, 4 };
		int[] deltaX = { 1, 100, 1000 };
		double[] results = new double[18];
		for (double result : results) {
			result = 0;
		}
		String[] resultTitles = new String[18];
		Random rand = new Random();
		DHeap_Item item;
		DHeap_Item[] heapArray;
		int[] intArray;
		DHeap heap;

		for (int r = 0; r < 10; r++) {
			int place = 0;

			for (int s : size) {
				heapArray = new DHeap_Item[s];
				intArray = new int[s];

				for (int i = 0; i < s; i++) {
					intArray[i] = rand.nextInt(s);
					item = new DHeap_Item(String.valueOf(s), intArray[i]);
					heapArray[i] = item;
				}

				for (int d : dValue) {

					DHeap.DHeapSort(intArray, d);

					double comparison = DHeap.comparisons;
					//System.out
					//		.format("for m = %d,\t d = %d,\t comparison number = %d %n",
					//				s, d, comparison);
					results[place] += comparison;
					if(r == 0) {
						resultTitles[place] = "for m = " + s + " d = " + d + " comparison number = ";
					}
					place++;
					DHeap.comparisons = 0;

					if (s == 1000000) {

						for (int x : deltaX) {

							heap = new DHeap(d, s); 
							for (int j = 0; j < s; j++) {
								heap.Insert(heapArray[j]); // CHANGED AS PER THE POST FROM aDI TELLING TO INSERT THE ITEMS USING INSERT FUNCTION
							}
							
							for (int j = 0; j < s; j++) {
								heap.Decrease_Key(heapArray[j], x); // this test will no longer work for heapArray[j] because of the change in arrayToHeap to work with duplicated DHeap_Items!!!! relevant post: http://moodle.tau.ac.il/mod/forum/discuss.php?d=73097
							}

							comparison = (double) DHeap.comparisons / (double) s; 
							//System.out
							//		.format("for m = %d,\t d = %d,\t x = %d,\t comparison number = %d %n",
							//				s, d, x, comparison);
							results[place] += comparison;
							if(r == 0) {
								resultTitles[place] = "for m = " + s + " d = " + d + " x = " + x + " comparison number = ";
							}
							place++;
							DHeap.comparisons = 0;
						}
					}
				}
			}
		}
		
		System.out.println("averages:");
		for(int p = 0; p < 18; p++) {
			System.out.print(resultTitles[p]);
			System.out.println(results[p] / 10);
		}
	}

}
