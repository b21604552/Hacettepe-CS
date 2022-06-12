
public class BSTNode {
    Integer value;
    BSTNode left, right;
    public BSTNode( Integer value ){
        this.value = value;
        this.left = null;
        this.right = null;
    }
    public void put( Integer value ){
        if (value < this.value){
            if (left != null) {
                left.put(value);             
            }else{
                left = new BSTNode(value);             
            }         
        }else if (value > this.value){
            if (right != null){
                right.put(value);
            }else{
                right = new BSTNode(value);
            }
        }
    }
    public Integer get(Integer value){
        if (this.value.equals(value)){
            return value;
        }if (value.compareTo(this.value) < 0){
            return left == null ? null : left.get(value);
        }else{
            return right == null ? null : right.get(value);
        }
    } 
    public BSTNode deleteNode(BSTNode root, int value) { 
        if (root == null)
        	return root; 
        if (value < root.value) 
            root.left = deleteNode(root.left, value); 
        else if (value > root.value) 
            root.right = deleteNode(root.right, value);
        else
        {
            if (root.left == null) 
                return root.right; 
            else if (root.right == null) 
                return root.left; 
            root.value = findMin(root.right);
            root.right = deleteNode(root.right, root.value); 
        } 
        return root; 
    }
    int findMin(BSTNode node) { 
    	if(node.left == null)
    		return node.value;
    	else
    		return findMin(node.left);
    }
}
