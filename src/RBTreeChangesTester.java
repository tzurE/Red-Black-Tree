import java.util.Random;

public class RBTreeChangesTester {
	public static void main(String[] args) {
		RBTree tree = new RBTree();
		Random r = new Random();
		
		for (int iter = 1; iter < 11; iter++) {
			int insertchanges = 0;

			for (int i = 1; i < (iter*10000)+1; i++) {
				
				int bla = r.nextInt(iter*30000) + 1;
				// if he's in the tree, get a new one
				while (tree.search(bla) != null) {
					bla = r.nextInt(iter*30000) + 1;
				}
				
				insertchanges = insertchanges + tree.insert(bla,"a");
			}
			
			System.out.print(iter + "\t" + (iter*10000) + "\t");
			System.out.print(((double) insertchanges/(10000*iter)) + "\t");
			
			int deletechanges = 0;
			int[] arr = tree.keysToArray();
			int ii = 0;
			while (tree.empty() == false) {
				deletechanges = deletechanges + tree.delete(arr[ii]);
				ii++;
			}
			System.out.println((double) deletechanges/(10000*iter));
		}
	}
}
