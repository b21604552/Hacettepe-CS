import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class BST {
	BSTNode root;
    public void put( Integer value ){
        if ( root == null ){
            root = new BSTNode( value );
        }else{
            root.put( value );
        }
    }
    public Integer get( Integer value ){
        return root == null ? null : root.get( value );
    }
    void printInorder(BSTNode node, String file) throws IOException{ 
        if (node == null) 
            return; 
        printInorder(node.left,file);
        Print(node.value + " ",file); 
        printInorder(node.right,file); 
    }
    void printPreorder(BSTNode node,String file) throws IOException{ 
        if (node == null) 
            return; 
        Print(node.value + " ",file);
        printPreorder(node.left,file);
        printPreorder(node.right,file); 
    }
    public Integer height(BSTNode node) {
    	if(node == null)
    		return -1;
    	else{
    		int lheight = height(node.left);
    		int rheight = height(node.right);
    		if(lheight > rheight)
    			return(lheight+1);
    		return(rheight+1);
    	}
    }
    public Integer getMaxWidth(BSTNode node){
    	int maxWidht = 0;
    	for(int i = 1;i<=height(this.root);i++) {
    		int width = getWidth(this.root,i);
    		if(width > maxWidht){
    			maxWidht = width;
    		}
    	}
    	return maxWidht;
    }
    public Integer getWidth(BSTNode node, int level) {
    	if(node == null)
    		return 0;
    	if(level == 1)
    		return 1;
    	else if(level > 1)
    		return(getWidth(node.left, level-1) + getWidth(node.right, level-1));
    	return 0;
    }
    void leavesAsc(BSTNode node,String file) throws IOException{ 
        if (node == null) 
            return;
        if((node.left == null) && (node.right == null)) {
        	Print(node.value + " ",file);
        	return;
        }
        leavesAsc(node.left,file);
        leavesAsc(node.right,file); 
    }
    public int findMin(BSTNode node){
    	if(node.left == null)
    		return node.value;
    	else
    		return findMin(node.left);
    }
    void puth(int power,int level) {
    	int i = 1,least = (int) Math.pow(2,power);
    	while(i <= Math.pow(2,level)){
    		put(least);
    		least = least + (int) Math.pow(2,(power+1));
    		i++;
    	}
    }
    static void Print(String line, String file) throws IOException {
		FileWriter fw = new FileWriter(file, true);
	    BufferedWriter bw = new BufferedWriter(fw);
	    PrintWriter out = new PrintWriter(bw);
	    out.print(line);
	    out.close();
	}
}
