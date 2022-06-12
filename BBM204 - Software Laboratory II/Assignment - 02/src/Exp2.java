import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Exp2 {
	public static void main(String[] args) throws IOException {
		FileOutputStream out = new FileOutputStream(args[1]);
        out.close();
		BufferedReader reader;
		reader = new BufferedReader(new FileReader(args[0]));
		String line = reader.readLine();
		BST tree = null;
		while (line != null) {
			String[] tokens = line.split(" ");
			if((int)(tokens[0].compareTo("CreateBST")) == 0) {
				tree = new BST();
				String[] values = tokens[1].split(",");
				for(int i = 0; i < values.length;i++) {
					tree.put(Integer.parseInt(values[i]));
				}
				Print("BST created with elements:",args[1]);
				tree.printInorder(tree.root,args[1]);
				Print("\n",args[1]);
			}else if((int)(tokens[0].compareTo("FindHeight")) == 0) {
				Print("Height:"+tree.height(tree.root),args[1]);
				Print("\n",args[1]);
			}else if((int)(tokens[0].compareTo("FindWidth")) == 0) {
				Print("Width:"+tree.getMaxWidth(tree.root),args[1]);
				Print("\n",args[1]);
			}else if((int)(tokens[0].compareTo("Preorder")) == 0) {
				Print("Preorder:",args[1]);
				tree.printPreorder(tree.root,args[1]);
				Print("\n",args[1]);
			}else if((int)(tokens[0].compareTo("LeavesAsc")) == 0) {
				Print("LeavesAsc:",args[1]);
				tree.leavesAsc(tree.root,args[1]);
				Print("\n",args[1]);
			}else if((int)(tokens[0].compareTo("DelRoot")) == 0) {
				if(tree.root != null) {
					Print("Root Deleted:"+tree.root.value,args[1]);
					tree.root = tree.root.deleteNode(tree.root, tree.root.value);
					Print("\n",args[1]);
				}else {
					Print("error",args[1]);
					Print("\n",args[1]);
				}
			}else if((int)(tokens[0].compareTo("DelRootLc")) == 0) {
				if(tree.root.left != null) {
					Print("Left Child of Root Deleted:"+tree.root.left.value,args[1]);
					tree.root.left = tree.root.left.deleteNode(tree.root.left, tree.root.left.value);
					Print("\n",args[1]);
				}else {
					Print("error",args[1]);
					Print("\n",args[1]);
				}
			}else if((int)(tokens[0].compareTo("DelRootRc")) == 0) {
				if(tree.root.right != null) {
					Print("Right Child of Root Deleted:"+tree.root.right.value,args[1]);
					tree.root.right = tree.root.right.deleteNode(tree.root.right, tree.root.right.value);
					Print("\n",args[1]);
				}else {
					Print("error",args[1]);
					Print("\n",args[1]);
				}
			}else if((int)(tokens[0].compareTo("CreateBSTH")) == 0) {
				int i = Integer.parseInt(tokens[1]);
				if(i >= 0){
					tree = new BST();
					int k = i;
					for(int j = 0; j<=i;j++) {
						tree.puth(k--, j);
					}
					Print("A full BST created with elements:",args[1]);
					tree.printInorder(tree.root,args[1]);
					Print("\n",args[1]);
				}else{
					Print("error",args[1]);
					Print("\n",args[1]);
				}
			}
			line = reader.readLine();
		}
		reader.close();
	}
	static void Print(String line,String file) throws IOException {
		FileWriter fw = new FileWriter(file, true);
	    BufferedWriter bw = new BufferedWriter(fw);
	    PrintWriter out = new PrintWriter(bw);
	    out.print(line);
	    out.close();
	}
}
