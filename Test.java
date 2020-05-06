在二叉树中找到累加和为固定值的最大路径
public class GetMaxLength {
    public int getMaxlen(TreeNode root,int sum){
        HashMap<Integer,Integer> sumMap=new HashMap<>();
        sumMap.put(0,0);
        return getLength(root,sum,0,1,0,sumMap);
    }
    private int getLength(TreeNode root, int sum, int preSum, int h, int maxlen,
                          HashMap<Integer,Integer> sumMap){
        if(root==null) return maxlen;
        int curSum=preSum+root.val;
        if(!sumMap.containsKey(curSum)){
            sumMap.put(curSum,h);
        }
        if(sumMap.containsKey(curSum-sum)){
            maxlen=Math.max(maxlen,h-sumMap.get(curSum-sum));
        }
        maxlen=getLength(root.left,sum,curSum,h+1,maxlen,sumMap);
        maxlen=getLength(root.right,sum,curSum,h+1,maxlen,sumMap);
        if(sumMap.get(curSum)==h){
            sumMap.remove(curSum);
        }
        return maxlen;
    }
}


找到二叉树中最大二叉搜索子树
public class GetBiggestBinaryTree {
    public TreeNode getBiggest(TreeNode root){
        int[] record=new int[3];
        return getBiggest(root,record);
    }
    private TreeNode getBiggest(TreeNode root,int[] record){
        if(root==null){
            record[0]=0;
            record[1]=Integer.MAX_VALUE;
            record[2]=Integer.MIN_VALUE;
            return root;
        }
        TreeNode lBST=getBiggest(root.left,record);
        int lsize=record[0];
        int lmin=record[1];
        int lmax=record[2];
        TreeNode rBST=getBiggest(root.right,record);
        int rsize=record[0];
        int rmin=record[1];
        int rmax=record[2];

        record[1]=Math.min(root.val,lmin);
        record[2]=Math.max(root.val,rmax);
        if(root.left==lBST&&root.right==rBST&&root.val>lmax&&root.val<rmin){
            record[0]=lsize+rsize+1;
            return root;
        }
        record[0]=Math.max(lsize,rsize);
        return lsize>rsize?lBST:rBST;
    }
}


在二叉树中找到符合二叉搜索树的最大拓扑结构
public class GetBSTiopo {
    public int getMaxIopo(TreeNode root){
        if(root==null) return 0;
        int max=getMaxIopo(root,root);
        max=Math.max(max,getMaxIopo(root.left));
        max=Math.max(max,getMaxIopo(root.right));
        return max;
    }
    private int getMaxIopo(TreeNode root,TreeNode find){
        if(find != null && isBSTNode(root, find)){
            return 1+getMaxIopo(root,find.left)+getMaxIopo(root,find.right);
        }
        return 0;
    }
    private boolean isBSTNode(TreeNode root,TreeNode find){
        if(root==null){
            return false;
        }
        if(root==find){
            return true;
        }
        return isBSTNode(find.val>root.val?root.right:root.left,find);
    }
	
	
	//方法二:
    private HashMap<TreeNode,Record> map=new HashMap<>();
    public int getIopo(TreeNode root,HashMap<TreeNode,Record> map){
        if(root==null) return 0;
        int leftsize=getIopo(root.left,map);
        int rightsize=getIopo(root.right,map);
        modified(root.left,root.val,map,true);
        modified(root.right,root.val,map,false);
        Record recordleft=map.get(root.left);
        Record recordright=map.get(root.right);
        int curleft=recordleft==null?0:recordleft.left+recordleft.right+1;
        int curright=recordright==null?0:recordright.right+recordright.left+1;
        map.put(root,new Record(curleft,curright));
        return Math.max(curleft+curright+1,Math.max(leftsize,rightsize));
    }
    private int modified(TreeNode root, int val, HashMap<TreeNode,Record> map, boolean flag){
        if(root==null||!map.containsKey(root)){
            return 0;
        }
        Record record=map.get(root);
        if((flag&&root.val>val)||(!flag&&root.val<val)){
            map.remove(root);
            return 1+record.left+record.right;
        }else{
            int minus=modified(flag?root.right:root.left,val,map,flag);
            if(flag){
                record.right=record.right-minus;
            }else{
                record.left=record.left-minus;
            }
            map.put(root,record);
            return minus;
        }
    }

    private static class Record{
        private int left;
        private int right;
        public Record(int left,int right){
            this.left=left;
            this.right=right;
        }
    }
}


锯齿形打印二叉树
public class ZigZag {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        Deque<TreeNode> queue=new LinkedList<>();
        List<List<Integer>> res=new ArrayList<>();
        if(root==null) return res;
        queue.offer(root);
        boolean left=true;
        while(!queue.isEmpty()){
            int size=queue.size();
            List<Integer> row=new ArrayList<>();
            if(left){
                while(size--!=0){
                    TreeNode node=queue.pollFirst();
                    row.add(node.val);
                    if(node.left!=null) queue.offerLast(node.left);
                    if(node.right!=null) queue.offerLast(node.right);
                }
                left=false;
            }else{
                while(size--!=0){
                    TreeNode node=queue.pollLast();
                    row.add(node.val);
                    if(node.right!=null) queue.offerFirst(node.right);
                    if(node.left!=null) queue.offerFirst(node.left);
                }
                left=true;
            }
            res.add(row);
        }
        return res;
    }
}


