

public class Ofir_test {

	public static void main(String[] args) {
		RBTree tree = new RBTree();
		tree.insert(1, "a");
		tree.insert(3, "b");
		tree.insert(2, "c");
		tree.insert(4, "aaa");
		//tree.insert(4, "aaa");
		tree.insert(5, "aaa");
		tree.insert(6, "aaa");
		tree.insert(7, "aaa");
		
		//tree.delete(3);
		tree.delete(2);
		tree.delete(4);
		
		tree.delete(6);
		//tree.delete(7);
		tree.delete(1);
		
		//tree.delete(5);
		//tree.delete(6);
		//System.out.println(tree);

		//  System.out.println(tree.getRoot().getKey());
		//System.out.println(tree.getRoot().getLeft().getLeft().getKey());
//		System.out.println(tree.getRoot().getLeft().getRight().getKey());
//		System.out.println(tree.getRoot().getLeft().getRight().getKey());
//		System.out.println(tree.getRoot().getLeft().getValue());
//		System.out.println(tree.getRoot().getLeft().getLeft().getValue());
//		System.out.println(tree.getRoot().getLeft().getRight().getValue());
		for (int i = 0; i < tree.size(); i++) {
			System.out.println("----" + tree.keysToArray()[i]);
		}
	}
}