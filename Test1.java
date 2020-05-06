一棵树是否包含另一棵树的拓扑结构
public class Contains {
    public boolean contains(TreeNode root1,TreeNode root2){
        return check(root1,root2)||contains(root1.left,root2)||contains(root1.right,root2);
    }
    private boolean check(TreeNode root1,TreeNode root2){
        if(root2==null) return true;
        if(root1==null||root1.val!=root2.val) return false;
        return check(root1.left,root2.left)&&check(root1.right,root2.right);
    }
}

是否为平衡二叉树
public class IsBalanceTree {
    public boolean isBalancedTree(TreeNode root){
        return isBalanced(root)>=0;
    }
    private int isBalanced(TreeNode root){
        if(root==null) return 0;
        int left=isBalanced(root.left);
        int right=isBalanced(root.right);
        if(left<0||right<0||Math.abs(left-right)>1){
            return -1;
        }
        return 1+Math.max(left,right);
    }
}

根据后续数组重建二叉搜索树
public class IsPostArray {

public TreeNode transform(int[] arr,int begin,int end){
        if(begin>end) return null;
        TreeNode root=new TreeNode(arr[end]);
        int left=-1;
        int right=end;
        for(int i=begin;i<end;i++){
            if(arr[i]<arr[end]){
                left=i;
            }else{
                right=i;
                break;
            }
        }
        root.left=transform(arr,begin,left);
        root.right=transform(arr,right,end-1);
        return root;
    }
	
    public boolean isPostArray(int[] arr){
        if(arr.length==0) return false;
        return isPostArray(arr,0,arr.length-1);
    }
    private boolean isPostArray(int[] arr,int begin,int end){
        if(begin==end) return true;
        int left=-1;
        int right=end;
        for(int i=begin;i<end;i++){
            if(arr[i]<arr[end]){
                left=i;
            }else{
                right=right==end?i:right;
            }
        }
        if(left==-1||right==end) return isPostArray(arr,begin,end-1);
        if(left!=right-1) return false;
        return isPostArray(arr,begin,left)&&isPostArray(arr,right,end-1);
    }
}

判断是否为二叉搜索树和完全二叉树
public class IsValid {

    //验证搜索树
    public boolean isBST(TreeNode root){
        return isBST(root,Long.MIN_VALUE,Long.MAX_VALUE);
    }
    private boolean isBST(TreeNode root,long min,long max){
        if(root==null) return true;
        if(root.val<=min||root.val>=max){
            return false;
        }
        return isBST(root.left,min,root.val)&&isBST(root.right,root.val,max);
    }


    //验证完全性
    public boolean isCompleteTree(TreeNode root) {
        if(root==null) return true;
        Queue<TreeNode> queue=new LinkedList<>();
        queue.offer(root);
        boolean flag=false;
        while(!queue.isEmpty()){
            TreeNode cur=queue.poll();
            if(flag){
                if(cur!=null) return false;
            }else{
                if(cur==null) flag=true;
                else{
                    queue.offer(cur.left);
                    queue.offer(cur.right);
                }
            }
        }
        return true;
    }
}


在二叉树中找下一个节点
public class GetNextNode {

    public Node getNextNode(Node root){
        if(root==null) return null;
        if(root.right!=null){
            Node cur=root.right;
            while(cur.left!=null){
                cur=cur.left;
            }
            return cur;
        }else{
            Node parent=root.parent;
            while(parent!=null&&parent.left!=root){
                root=parent;
                parent=root.parent;
            }
            return parent;
        }
    }
    private static class Node{
        int val;
        Node left;
        Node right;
        Node parent;
        public Node(int val){
            this.val=val;
        }
    }
}



