
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class insertion_counter {

	public static void main(String[] args) {
		int n = 5;
		int m;

		for (int i = 1; i < 11; i++) {
			RBTree tree = new RBTree();
			m = i * n;
			List<Integer> arr_k = new ArrayList<Integer>(); // set a list of keys in length m
			String[] arr_v = new String[m]; // set a list of values of length
			for (int j = 0; j < m; j++) { // fill both arrays
				arr_k.add(j);
				String str = Integer.toString(j);
				arr_v[j] = str; // the values are not important
			}
			Collections.shuffle(arr_k); // shuffle the list of keys 

			int[] insertColorReplacements = new int[m]; // set an array of color replacements for each insert
			int in_cr = 0;
			int sum = 0;
			for (int k = 0; k < m ; k++) {
				in_cr = tree.insert(arr_k.get(k), arr_v[k]);
				insertColorReplacements[k] = in_cr;
				sum+=in_cr;
			}
			System.out.println("Number of color replacements in the " + i+ "th insertion, is : " + sum);  
			
//			int[] deleteColorReplacements = new int[m]; // set an array of color replacements for each delete
//			int del_cr = 0;
//			Collections.sort(arrk); // sort the list of keys because nodes should be deleted in order
//			for (int f = 0; f < m; f++) {
//				del_cr = tree.delete(arrk.get(f));
//				deleteColorReplacements[f] = del_cr;
//			}
//
//			double insert_arvg = 0; // calculate the average color replacements in m insertions and deletions
//			double delete_arvg = 0;
//			for (int g = 0; g < m; g++) {
//				insert_arvg = insert_arvg + insertColorReplacements[g];
//				delete_arvg = delete_arvg + deleteColorReplacements[g];
//			}
//			insert_arvg = insert_arvg / m;
//			delete_arvg = delete_arvg / m;
//			System.out.println("the average number of color replacements in " + m + " insertions is :" + insert_arvg);
//			System.out.println("the average number of color replacements in " + m + " deletions is :" + delete_arvg);
		}
	}

	
}
