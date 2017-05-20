import java.util.Random;

public class TestByKeren {
	public static void main(String[] args) {
		RBTree tree = new RBTree();
		Random random = new Random();
		int countInsert = 0;
		int sumInsert = 0;
		int a;

		int result;
		while (countInsert <= 90000) {
			a = random.nextInt(1000000) + 1;
			result = tree.insert(a, "a");
			if (result != -1) {
				sumInsert += result;
				countInsert += 1;
			}
		}
		int keyToDelete;
		int sumDelete = 0;
		for (int i = 1; i <= 90000; i++) {
			keyToDelete = tree.minNode(tree.getRoot().getLeft()).getKey();
			sumDelete += tree.delete(keyToDelete);
		}
		System.out.println("ColorFlipsInsert " + sumInsert / 90000.0);
		System.out.println("ColorFlipsDelete " + sumDelete / 90000.0);
	}
}
