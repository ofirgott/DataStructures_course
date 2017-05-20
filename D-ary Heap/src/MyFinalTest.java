package ex2;


import java.util.Random;


public class MyFinalTest {

	public static void main(String[] args) {
		System.out.println("######## FIRST TEST ########");
		Random r = new Random();
		for (int i = 3; i < 6; i++) {
			int m = (int) Math.pow(10, i);
			int [][] arraysArr = new int[10][m];
			// int[] array = new int[m];
			for (int[] arr : arraysArr){
				for (int j = 0; j < m; j++) {
					arr[j] = r.nextInt(1001);
				}
			}
			for (int d = 2; d < 5; d++) {
				double sum = 0;
				for (int[] array : arraysArr){
					DHeapTest.DHeapSort(array, d);
					sum += DHeapTest.downCounter;
				}
				System.out.println("*************************");
				System.out.println("m = " + m);
				System.out.println("d = " + d);
				System.out.println("Average number of comparisons: " + sum/10.0);
				System.out.println("*************************\n");
			}
		}
		System.out.println("######## SECOND TEST ########");
		int[] deltas = new int[]{1,100,1000};
		int m = 100000;
		for (int delta : deltas) {
			int [][] arraysArr2 = new int[10][m];
			for (int[] arr : arraysArr2){
				for (int j = 0; j < m; j++) {
					arr[j] = r.nextInt(1001);
				}
			}
			for (int d = 2; d < 5; d++) {
				double sum = 0;
				for (int[] arr : arraysArr2){
					DHeap_Item[] array = new DHeap_Item[m];
					for (int j = 0; j < m; j++) {
						DHeap_Item item = new DHeap_Item(null, arr[j]);
						array[j] = item;
					}
					DHeap heap = new DHeap(d,m);
					for (DHeap_Item item : array){
						heap.Insert(item);
					}
					heap.upCounter = 0;
					for (DHeap_Item item : array){
						heap.Decrease_Key(item, delta);
					}
					sum += heap-.upCounter;
					
					
				}
				
				double averageComparisonsNumber = sum/10.0;
				System.out.println("*************************");
				System.out.println("delta = " + delta);
				System.out.println("d = " + d);
				System.out.println("Average number of comparisons: " + averageComparisonsNumber);
				System.out.println("*************************\n");
				
			}
		}
		
	}
}
	
	


