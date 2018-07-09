package datastructure;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 
 * @author jh
 */

public class RedBlackTree<T extends Comparable<T>> {
    private final RedBlackTreeNode<T> root;
    private AtomicLong treeSize = new AtomicLong(0);

    //in overwrite mode, all nodes' values cannot have same value
    //in non-overwrite mode, node can have same value, suggest not using non-overwrite mode.
    private volatile boolean overrideMode = true;

    public RedBlackTree() {
        this.root = new RedBlackTreeNode<T>();
    }

    public RedBlackTree(boolean overrideMode) {
        this();
        this.overrideMode = overrideMode;
    }


    public boolean isOverrideMode() {
        return overrideMode;
    }

    public void setOverrideMode(boolean overrideMode) {
        this.overrideMode = overrideMode;
    }

    /**
     * number of tree number
     * @return
     */
    public long getSize() {
//        return size.get();
        return 0;
    }
    /**
     * get the root node
     * @return
     */
    private RedBlackTreeNode<T> getRoot() {
        return root.getLeft();
    }

    /**
     * add value to a new node,if this value exist in this tree,
     * if value exist,it will return the exist value.otherwise return null
     * if override mode is true,if value exist in the tree,
     * it will override the old value in the tree
     * 
     * @param value
     * @return
     */
    public T addNode(T value){
        RedBlackTreeNode<T> t = new RedBlackTreeNode<T>(value);
        return addNode(t);
    }
    /**
     * find the value by give value(include key,key used for search,
     * other field is not used,@see compare method).if this value not exist return null
     * @param value
     * @return
     */
    public T find(T value){
        RedBlackTreeNode<T> dataRoot = getRoot();
        while(dataRoot!=null){
            int cmp = dataRoot.getValue().compareTo(value);
            if(cmp<0){
                dataRoot = dataRoot.getRight();
            }else if(cmp>0){
                dataRoot = dataRoot.getLeft();
            }else{
                return dataRoot.getValue();
            }
        }
        return null;
    }
    /**
     * remove the node by give value,if this value not exists in tree return null
     * @param value include search key
     * @return the value contain in the removed node
     */
    public T remove(T value){
        RedBlackTreeNode<T> dataRoot = getRoot();
        RedBlackTreeNode<T> parent = root;

        while(dataRoot!=null){
            int cmp = dataRoot.getValue().compareTo(value);
            if(cmp<0){
                parent = dataRoot;
                dataRoot = dataRoot.getRight();
            }else if(cmp>0){
                parent = dataRoot;
                dataRoot = dataRoot.getLeft();
            }else{
                if(dataRoot.getRight()!=null){
                    RedBlackTreeNode<T> min = removeMin(dataRoot.getRight());
                    //x used for fix color balance
                    RedBlackTreeNode<T> x = min.getRight()==null ? min.getParent() : min.getRight();
                    boolean isParent = min.getRight()==null;

                    min.setLeft(dataRoot.getLeft());
                    setParent(dataRoot.getLeft(),min);
                    if(parent.getLeft()==dataRoot){
                        parent.setLeft(min);
                    }else{
                        parent.setRight(min);
                    }
                    setParent(min,parent);

                    boolean curMinIsBlack = min.isBlack();
                    //inherit dataRoot's color
                    min.setRed(dataRoot.isRed());

                    if(min!=dataRoot.getRight()){
                        min.setRight(dataRoot.getRight());
                        setParent(dataRoot.getRight(),min);
                    }
                    //remove a black node,need fix color
                    if(curMinIsBlack){
                        if(min!=dataRoot.getRight()){
                            fixRemove(x,isParent);
                        }else if(min.getRight()!=null){
                            fixRemove(min.getRight(),false);
                        }else{
                            fixRemove(min,true);
                        }
                    }
                }else{
                    setParent(dataRoot.getLeft(),parent);
                    if(parent.getLeft()==dataRoot){
                        parent.setLeft(dataRoot.getLeft());
                    }else{
                        parent.setRight(dataRoot.getLeft());
                    }
                    //current node is black and tree is not empty
                    if(dataRoot.isBlack() && !(root.getLeft()==null)){
                        RedBlackTreeNode<T> x = dataRoot.getLeft()==null 
                                            ? parent :dataRoot.getLeft();
                        boolean isParent = dataRoot.getLeft()==null;
                        fixRemove(x,isParent);
                    }
                }
                setParent(dataRoot,null);
                dataRoot.setLeft(null);
                dataRoot.setRight(null);
                if(getRoot()!=null){
                    getRoot().setRed(false);
                    getRoot().setParent(null);
                }
//                size.decrementAndGet();
                return dataRoot.getValue();
            }
        }
        return null;
    }
    /**
     * fix remove action
     * @param node
     * @param isParent
     */
    private void fixRemove(RedBlackTreeNode<T> node,boolean isParent){
        RedBlackTreeNode<T> cur = isParent ? null : node;
        boolean isRed = isParent ? false : node.isRed();
        RedBlackTreeNode<T> parent = isParent ? node : node.getParent();

        while(!isRed && !isRoot(cur)){
            RedBlackTreeNode<T> sibling = getSibling(cur,parent);
            //sibling is not null,due to before remove tree color is balance

            //if cur is a left node
            boolean isLeft = parent.getRight()==sibling;
            if(sibling.isRed() && !isLeft){//case 1
                //cur in right
                parent.makeRed();
                sibling.makeBlack();
                rotateRight(parent);
            }else if(sibling.isRed() && isLeft){
                //cur in left
                parent.makeRed();
                sibling.makeBlack();
                rotateLeft(parent);
            }else if(isBlack(sibling.getLeft()) && isBlack(sibling.getRight())){//case 2
                sibling.makeRed();
                cur = parent;
                isRed = cur.isRed();
                parent=parent.getParent();
            }else if(isLeft && !isBlack(sibling.getLeft()) 
                                    && isBlack(sibling.getRight())){//case 3
                sibling.makeRed();
                sibling.getLeft().makeBlack();
                rotateRight(sibling);
            }else if(!isLeft && !isBlack(sibling.getRight()) 
                                            && isBlack(sibling.getLeft()) ){
                sibling.makeRed();
                sibling.getRight().makeBlack();
                rotateLeft(sibling);
            }else if(isLeft && !isBlack(sibling.getRight())){//case 4
                sibling.setRed(parent.isRed());
                parent.makeBlack();
                sibling.getRight().makeBlack();
                rotateLeft(parent);
                cur=getRoot();
            }else if(!isLeft && !isBlack(sibling.getLeft())){
                sibling.setRed(parent.isRed());
                parent.makeBlack();
                sibling.getLeft().makeBlack();
                rotateRight(parent);
                cur=getRoot();
            }
        }
        if(isRed){
            cur.makeBlack();
        }
        if(getRoot()!=null){
            getRoot().setRed(false);
            getRoot().setParent(null);
        }

    }
    //get sibling node
    private RedBlackTreeNode<T> getSibling(RedBlackTreeNode<T> node,RedBlackTreeNode<T> parent){
        parent = node==null ? parent : node.getParent();
        if(node==null){
            return parent.getLeft()==null ? parent.getRight() : parent.getLeft();
        }
        if(node==parent.getLeft()){
            return parent.getRight();
        }else{
            return parent.getLeft();
        }
    }

    private boolean isBlack(RedBlackTreeNode<T> node){
        return node==null || node.isBlack();
    }
    private boolean isRoot(RedBlackTreeNode<T> node){
        return root.getLeft() == node && node.getParent()==null;
    }
    /**
     * find the successor node
     * @param node current node's right node
     * @return
     */
    private RedBlackTreeNode<T> removeMin(RedBlackTreeNode<T> node){
        //find the min node
        RedBlackTreeNode<T> parent = node;
        while(node!=null && node.getLeft()!=null){
            parent = node;
            node = node.getLeft();
        }
        //remove min node
        if(parent==node){
            return node;
        }

        parent.setLeft(node.getRight());
        setParent(node.getRight(),parent);

        //don't remove right pointer,it is used for fixed color balance
        //node.setRight(null);
        return node;
    }



    private T addNode(RedBlackTreeNode<T> node){
        node.setLeft(null);
        node.setRight(null);
        node.setRed(true);
        setParent(node,null);
        if(root.getLeft()==null){
            root.setLeft(node);
            //root node is black
            node.setRed(false);
//            size.incrementAndGet();
        }else{
            RedBlackTreeNode<T> x = findParentNode(node);
            int cmp = x.getValue().compareTo(node.getValue());

            if(this.overrideMode && cmp==0){
                T v = x.getValue();
                x.setValue(node.getValue());
                return v;
            }else if(cmp==0){
                //value exists,ignore this node
                return x.getValue();
            }

            setParent(node,x);

            if(cmp>0){
                x.setLeft(node);
            }else{
                x.setRight(node);
            }

            fixInsert(node);
//            size.incrementAndGet();
        }
        return null;
    }

    /**
     * find the parent node to hold node x,if parent value equals x.value return parent.
     * @param x
     * @return
     */
    private RedBlackTreeNode<T> findParentNode(RedBlackTreeNode<T> x){
        RedBlackTreeNode<T> dataRoot = getRoot();
        RedBlackTreeNode<T> child = dataRoot;

        while(child!=null){
            int cmp = child.getValue().compareTo(x.getValue());
            if(cmp==0){
                return child;
            }
            if(cmp>0){
                dataRoot = child;
                child = child.getLeft();
            }else if(cmp<0){
                dataRoot = child;
                child = child.getRight();
            }
        }
        return dataRoot;
    }

    /**
     * red black tree insert fix.
     * @param x
     */
    private void fixInsert(RedBlackTreeNode<T> x){
        RedBlackTreeNode<T> parent = x.getParent();

        while(parent!=null && parent.isRed()){
            RedBlackTreeNode<T> uncle = getUncle(x);
            if(uncle==null){//need to rotate
                RedBlackTreeNode<T> ancestor = parent.getParent();
                //ancestor is not null due to before before add,tree color is balance
                if(parent == ancestor.getLeft()){
                    boolean isRight = x == parent.getRight();
                    if(isRight){
                        rotateLeft(parent);
                    }
                    rotateRight(ancestor);

                    if(isRight){
                        x.setRed(false);
                        parent=null;//end loop
                    }else{
                        parent.setRed(false);
                    }
                    ancestor.setRed(true);
                }else{
                    boolean isLeft = x == parent.getLeft();
                    if(isLeft){
                        rotateRight(parent);
                    }
                    rotateLeft(ancestor);

                    if(isLeft){
                        x.setRed(false);
                        parent=null;//end loop
                    }else{
                        parent.setRed(false);
                    }
                    ancestor.setRed(true);
                }
            }else{//uncle is red
                parent.setRed(false);
                uncle.setRed(false);
                parent.getParent().setRed(true);
                x=parent.getParent();
                parent = x.getParent();
            }
        }
        getRoot().makeBlack();
        getRoot().setParent(null);
    }
    /**
     * get uncle node
     * @param node
     * @return
     */
    private RedBlackTreeNode<T> getUncle(RedBlackTreeNode<T> node){
        RedBlackTreeNode<T> parent = node.getParent();
        RedBlackTreeNode<T> ancestor = parent.getParent();
        if(ancestor==null){
            return null;
        }
        if(parent == ancestor.getLeft()){
            return ancestor.getRight();
        }else{
            return ancestor.getLeft();
        }
    }

    private void rotateLeft(RedBlackTreeNode<T> node){
        RedBlackTreeNode<T> right = node.getRight();
        if(right==null){
            throw new java.lang.IllegalStateException("right node is null");
        }
        RedBlackTreeNode<T> parent = node.getParent();
        node.setRight(right.getLeft());
        setParent(right.getLeft(),node);

        right.setLeft(node);
        setParent(node,right);

        if(parent==null){//node pointer to root
            //right  raise to root node
            root.setLeft(right);
            setParent(right,null);
        }else{
            if(parent.getLeft()==node){
                parent.setLeft(right);
            }else{
                parent.setRight(right);
            }
            //right.setParent(parent);
            setParent(right,parent);
        }
    }

    private void rotateRight(RedBlackTreeNode<T> node){
        RedBlackTreeNode<T> left = node.getLeft();
        if(left==null){
            throw new java.lang.IllegalStateException("left node is null");
        }
        RedBlackTreeNode<T> parent = node.getParent();
        node.setLeft(left.getRight());
        setParent(left.getRight(),node);

        left.setRight(node);
        setParent(node,left);

        if(parent==null){
            root.setLeft(left);
            setParent(left,null);
        }else{
            if(parent.getLeft()==node){
                parent.setLeft(left);
            }else{
                parent.setRight(left);
            }
            setParent(left,parent);
        }
    }


    private void setParent(RedBlackTreeNode<T> node,RedBlackTreeNode<T> parent){
        if(node!=null){
            node.setParent(parent);
            if(parent==root){
                node.setParent(null);
            }
        }
    }
    /**
     * debug method,it used print the given node and its children nodes,
     * every layer output in one line
     * @param root
     */
    public void printTree(RedBlackTreeNode<T> root){
        java.util.LinkedList<RedBlackTreeNode<T>> queue =new java.util.LinkedList<RedBlackTreeNode<T>>();
        java.util.LinkedList<RedBlackTreeNode<T>> queue2 =new java.util.LinkedList<RedBlackTreeNode<T>>();
        if(root==null){
            return ;
        }
        queue.add(root);
        boolean firstQueue = true;

        while(!queue.isEmpty() || !queue2.isEmpty()){
            java.util.LinkedList<RedBlackTreeNode<T>> q = firstQueue ? queue : queue2;
            RedBlackTreeNode<T> n = q.poll();

            if(n!=null){
                String pos = n.getParent()==null ? "" : ( n == n.getParent().getLeft() 
                                                                        ? " LE" : " RI");
                String pstr = n.getParent()==null ? "" : n.getParent().toString();
                String cstr = n.isRed()?"R":"B";
                cstr = n.getParent()==null ? cstr : cstr+" ";
                System.out.print(n+"("+(cstr)+pstr+(pos)+")"+"\t");
                if(n.getLeft()!=null){
                    (firstQueue ? queue2 : queue).add(n.getLeft());
                }
                if(n.getRight()!=null){
                    (firstQueue ? queue2 : queue).add(n.getRight());
                }
            }else{
                System.out.println();
                firstQueue = !firstQueue;
            }
        }
    }


    public static void main(String[] args) {
        RedBlackTree<String> bst = new RedBlackTree<String>();
        bst.addNode("d");
        bst.addNode("d");
        bst.addNode("c");
        bst.addNode("c");
        bst.addNode("b");
        bst.addNode("f");

        bst.addNode("a");
        bst.addNode("e");

        bst.addNode("g");
        bst.addNode("h");


        bst.remove("c");

        bst.printTree(bst.getRoot());
    }
}