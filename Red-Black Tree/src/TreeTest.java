import java.util.Arrays;
import java.util.Random;

public class TreeTest {
	public static void main(String[] args) {
		test1();
		test2();
		test3();
		test4();
		test5();

	}

	public static void test1() {
		// Only inserts test
		int[] vals = new int[] { 1, 3, 6, 8, 44, 7, 5, 22, 100, 57, 2 };
		RBTree tree = CreateTree(vals);
		System.out.println("========================================");
		System.out.println("Test1 Results:");
		System.out.println("Keys Inserted: " + Arrays.toString(vals));
		System.out.println("Tree After Insertions:");
		PrintTree(tree);
		System.out.println("========================================");
		System.out.println();
	}

	public static void test2() {
		// From Empty to full and back to empty
		int[] keys = new int[] { 1, 3, 6, 8, 44, 7, 5, 22, 100, 57, 2 };
		RBTree tree = CreateTree(keys);
		PrintTree(tree);
		tree = RemoveFromTree(keys, tree);
		System.out.println("========================================");
		System.out.println("Test2 Results:");
		System.out.println("Tree After process: (Should be empty)");
		PrintTree(tree);
		System.out.println("========================================");
		System.out.println();
	}

	public static void test3() {
		// alternate Inserts and deletes
		int[] keys = new int[] { 4, 8, 12, 16, 20, 24, 28, 32, 36, 40 };
		RBTree tree = CreateTree(keys);
		tree.delete(24);
		tree.delete(8);
		tree.insert(7, RandomString());
		tree.delete(40);
		tree.delete(32);
		tree.insert(11, RandomString());
		tree.delete(11);
		tree.delete(12);
		tree.delete(28);
		tree.delete(20);
		tree.insert(30, RandomString());
		tree.delete(16);
		tree.delete(30);
		System.out.println("========================================");
		System.out.println("Test3 Results:");
		System.out.println("Tree After process: (Should only conatin 4,7,36)");
		PrintTree(tree);
		System.out.println("========================================");
		System.out.println();
	}

	public static void test4() {
		// INITIALIZATION
		RBTree tree = new RBTree();
		final int someSize = 17500;
		int[] arr = new int[someSize];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = i;
		}
		Shuffle(arr);
		for (int i = 0; i < someSize; i++) {
			tree.insert(arr[i], Integer.toString(arr[i]));
		}
		System.out.println("After " + someSize + " insertions Tree size is: "
				+ tree.size() + " Minimum is: " + tree.min()
				+ " And Maximum is: " + tree.max());

		for (int i = 0; i < someSize; i += 500) {
			System.out.print("Tree size is: " + tree.size());
			System.out.println(" Minimum is: " + tree.min()
					+ "  and should be: " + i);
			// keysToArray Test
			int[] testArr = tree.keysToArray();
			for (int k = 0; k < testArr.length - 1; k++) {
				if (testArr[k] > testArr[k + 1])
					System.out
							.println("Error! KeysToArray brought keys in wrong order");
			}
			// valuesToArray Test
			String[] testArr2 = tree.valuesToArray();
			for (int k = 0; k < testArr2.length - 1; k++) {
				if (Integer.parseInt(testArr2[k]) > Integer
						.parseInt(testArr2[k + 1]))
					System.out
							.println("Error! valuesToArray brought keys in wrong order");
			}
			int[] newArr = new int[500];
			for (int j = 0; j < newArr.length; j++) {
				newArr[j] = j + i;
			}
			Shuffle(newArr);

			for (int j = 0; j < newArr.length; j++) {
				int x = tree.delete(newArr[j]);
				if (x == -1)
					System.out.println("Error! deleted node was not in tree");
			}

		}
	}

	public static void test5() {
		RBTree tree = new RBTree();
		final int someSize = 17500;
		int[] arr = new int[someSize];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = i;
		}
		Shuffle(arr);
		for (int i = 0; i < someSize; i++) {
			tree.insert(arr[i], Integer.toString(arr[i]));
		}
		// System.out.println("test result: " + tree.test());
		// Used to test successor and predecessor directly
	}

	public static RBTree CreateTree(int[] keys) {
		// Creates tree from array of integers
		RBTree tree = new RBTree();
		for (int key : keys) {
			tree.insert(key, RandomString());
		}
		return tree;
	}

	public static RBTree RemoveFromTree(int[] keys, RBTree tree) {
		// Remove Nodes in tree with keys=keys
		for (int key : keys) {
			tree.delete(key);
			tree.toString();
		}
		return tree;
	}

	public static String RandomString() {
		Integer x = (int) ((Math.random() * 10000));
		return x.toString();
	}

	public static void PrintTree(RBTree tree) {
		tree.toString();
	}

	static void Shuffle(int[] arr) {
		Random rnd = new Random();
		for (int i = arr.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			int a = arr[index];
			arr[index] = arr[i];
			arr[i] = a;
		}
	}

}
