
public class RBTreePrinter
{
	public static void main(String[] args){
		RBTree tree = new RBTree();
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
		printTree(tree);
	}
	
	
	/**
	 * Prints the tree
	 * 
	 * Things to do before running:
	 * 1. Add the following functions to RBTree:
	 * 		a. getRoot(): returns root (RBNode object)
	 * 2. Add the following functions to RBNode:
	 * 		a. getRightChild(): Returns right child (RBNode object)
	 * 		b. getLeftChild(): Returns left child (RBNode object)
	 * 		c. getKey(): Returns the key of the node
	 * 		d. getColor(): Returns the color of the node
	 * 
	 * Assumptions:
	 * 1. NIL.getRightChild() == null
	 * 2. NIL.getLeftChild() == null
	 * 3. NIL.getKey() == -1  (If something else - change #1)
	 * 4. node.getColor() - returns the string "BLACK" or "RED" (If something else - change #2)
	 */
	
	public static void printTree(RBTree tree) {
		if (tree.getRoot().getRightChild() != null) {
			printTreeLine(tree.getRoot().getRightChild(), true, "");
		}
		printNodeValue(tree.getRoot());
		if (tree.getRoot().getLeftChild() != null) {
			printTreeLine(tree.getRoot().getLeftChild(), false, "");
		}
	}
	
	private static void printNodeValue(RBTree.RBNode node) {
		if (node.getKey() == Integer.MIN_VALUE)	// #1 Change if the key of NIL != -1
			System.out.println("NIL");
		else {
			if (node.getColour() == 1) {	// #2 Change if getColor() returns something else than "RED" if color is red
				try { Thread.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }
				System.err.print(node.getKey());
				try { Thread.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }
				System.out.println();
			}
			else
				System.out.println(node.getKey());
		}
	}
	
	private static void printTreeLine(RBTree.RBNode node, boolean isRight, String indent) {
		if (node.getRightChild() != null)
			printTreeLine(node.getRightChild(), true, indent + (isRight ? "        " : " |      "));
		System.out.print(indent);
		if (isRight)
			System.out.print(" /");
		else
			System.out.print(" \\");
		System.out.print("----- ");
		printNodeValue(node);
		if (node.getLeftChild() != null) {
			printTreeLine(node.getLeftChild(), false, indent + (isRight ? " |      " : "        "));
		}
	}
	
}
