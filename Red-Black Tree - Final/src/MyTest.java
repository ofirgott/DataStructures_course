

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MyTest {

	public static void main(String[] args) {
		System.out.println("*****TESTER INSTRUCTIONS******");
		System.out.println("This tester does two tests:");
		System.out.println("The first test generates a random tree of up to 10 nodes.\n"
				+ "After the tree is full, you'll be able to see a visualization of it,\n"
				+ "using the toString methods below (put them in the corresponding classes).\n"
				+ "You also get some more information about the tree (size, min, max, etc.).\n"
				+ "After that, the nodes are deleted one by one randomly.\n"
				+ "After each deletion you can see which node has been deleted and the new tree.\n\n"
				+ "The second test is basically the same, except that it inserts the nodes from 1 to 10\n"
				+ "in their natural order, and deletes them randomly.\n"
				+ "Enjoy!\n\n\n");
		
		RBTree tree = generateTree();
		System.out.println(tree.toString());
		int[] keysIntArr = tree.keysToArray();
		String keysArr = Arrays.toString(keysIntArr);
		System.out.println(keysArr);
		String valuesArr = Arrays.toString(tree.valuesToArray());
		System.out.println(valuesArr);
		if (tree.empty())
			System.out.println("empty tree");
		else
			System.out.println("not an empty tree");
		System.out.println("tree min val: " + tree.min() + "\ntree max val: "
				+ tree.max() + "\nsize: " + tree.size());
		Random rand = new Random();
		int length = keysIntArr.length;
		List<Integer> intList = new ArrayList<>();
		for (int k : keysIntArr)
			intList.add(k);
		Collections.shuffle(intList);
		for (int i = 0; i < length; i++) {
			int k = intList.get(i);
			System.out.println("deleting: " + k);
			tree.delete(k);
			System.out.println(tree.toString());
		}
		System.out.println("\n\n\n");
		RBTree tree2 = new RBTree();
		System.out.println(tree2.toString());
		for (int i = 1; i < 11; i++) {
			System.out.println("\n\n\n");
			if (tree2.empty())
				System.out.println("tree is empty");
			else
				System.out.println("tree is not empty");
			int charIndex = rand.nextInt(26);
			String ch = "abcdefghijklmnopqrstuvwxyz".substring(charIndex,
					charIndex + 1);
			System.out.println("inserting: " + i + ", " + ch);
			tree2.insert(i, ch);
			System.out.println(tree2.toString());
			System.out.println("tree min val: " + tree2.min()
					+ "\ntree max val: " + tree2.max() + "\nsize: "
					+ tree2.size());
			System.out.println("keys: " + Arrays.toString(tree2.keysToArray()));
			System.out.println("values: "
					+ Arrays.toString(tree2.valuesToArray()));
		}

		intList = new ArrayList<>();
		for (int i = 1; i < 11; i++)
			intList.add(i);
		Collections.shuffle(intList);
		for (int i = 1; i < 11; i++) {
			int k = intList.get(0);
			intList.remove(0);
			System.out.println("\n\n\n");
			if (tree2.empty())
				System.out.println("tree is empty");
			else
				System.out.println("tree is not empty");
			System.out.println("deleting: " + k);
			tree2.delete(k);
			System.out.println(tree2.toString());
			System.out.println("tree min val: " + tree2.min()
					+ "\ntree max val: " + tree2.max() + "\nsize: "
					+ tree2.size());
			System.out.println("keys: " + Arrays.toString(tree2.keysToArray()));
		}

	}

	private static RBTree generateTree() {
		RBTree tree = new RBTree();
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			int k = random.nextInt(20) + 1;
			int charIndex = random.nextInt(26);
			String ch = "abcdefghijklmnopqrstuvwxyz".substring(charIndex,
					charIndex + 1);
			System.out.println(k + ", " + ch);
			tree.insert(k, ch);
		}
		return tree;

	}
	
// ***  PUT IN RBTree CLASS ***		
//	@Override
//	public String toString() {
//		return this.root.left.toString();
//	}
	
	
// ***  PUT IN RBNode CLASS ***	
//	@Override
//	public String toString() {
//		if (this == NULL_NODE)
//			return "null";
//		String color;
//		if (this.isRed())
//			color = "RED";
//		else
//			color = "BLACK";
//		return "[ " + this.left.toString() + " <- " + this.key + ", "
//				+ this.value + ", " + color + " -> "
//				+ this.right.toString() + " ]";
//
//	}

}
