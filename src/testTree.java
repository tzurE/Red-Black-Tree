

public class testTree {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RBTree tree=new RBTree();
		tree.insert(1,"a");
		tree.insert(2,"a");
		tree.insert(3,"a");
		tree.insert(4,"a");
		tree.insert(5,"a");
		//tree.leftRotate(toRotate)
		tree.insert(8, "H");
		tree.insert(4, "D");
		tree.insert(12, "L");
		tree.insert(2, "B");
		tree.insert(6, "F");
		tree.insert(10, "J");
		tree.insert(14, "N");
		tree.insert(1, "A");
		tree.insert(3, "C");
		tree.delete(6);
		tree.insert(5, "E");
		tree.delete(12);
		tree.insert(7, "G");
		tree.insert(9, "I");
		tree.insert(11, "K");
		tree.insert(13, "M");
		tree.insert(23, "X");
		tree.insert(15, "O");
		RBTreePrinter.printTree(tree);
		System.out.print(tree.max());

	}

}
