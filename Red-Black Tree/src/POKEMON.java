public class POKEMON {
	public static void main(String[] args){
		RBTree tree = new RBTree();
		System.out.println("-------------------------------");
		System.out.println("creating a tree of pokemons according to http://en.wikipedia.org/wiki/List_of_Pok%C3%A9mon");
		System.out.println("printing the tree after each insert");
		
		// insert 7
		System.out.println("-------------------------------");
		System.out.println("insert 7");
		tree.insert(7, "Squirtle");
		tree.printTreeForReal(tree.getRoot());
		System.out.println("the current root is " + tree.getRoot());
				
				
				// insert 1
		System.out.println("-------------------------------");
		System.out.println("insert 1");
		tree.insert(1, "Bulbasaur");
		tree.printTreeForReal(tree.getRoot());
		System.out.println("the current root is " + tree.getRoot().toString());

		
		
		
		// insert 3
		System.out.println("-------------------------------");
		System.out.println("insert 3");
		tree.insert(3, "Venusaur");
		tree.printTreeForReal(tree.getRoot());
		System.out.println("the current root is " + tree.getRoot());
		
		
		
		// insert 5
		System.out.println("-------------------------------");
		System.out.println("insert 5");
		tree.insert(5, "Charmeleon");
		tree.printTreeForReal(tree.getRoot());	
		System.out.println("the current root is " + tree.getRoot());
		
		
		// insert 4
		System.out.println("-------------------------------");
		System.out.println("insert 4");
		tree.insert(4, "Charmander");
		tree.printTreeForReal(tree.getRoot());
		System.out.println("the current root is " + tree.getRoot());
				
				
		// insert 6
		System.out.println("-------------------------------");
		System.out.println("insert 6");
		tree.insert(6, "Charizard");
		tree.printTreeForReal(tree.getRoot());
		System.out.println("the current root is " + tree.getRoot());
		
		// insert 2
		System.out.println("-------------------------------");
		System.out.println("insert 2");
		tree.insert(2, "Ivysaur");
		tree.printTreeForReal(tree.getRoot());
		System.out.println("the current root is " + tree.getRoot());
				
				
		
		
		// insert 8
		System.out.println("-------------------------------");
		System.out.println("insert 8");
		tree.insert(8, "Wartortle");
		tree.printTreeForReal(tree.getRoot());
		System.out.println("the current root is " + tree.getRoot());
		
		
	}

}