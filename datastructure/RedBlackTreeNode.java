package datastructure;

/**
 * 
 * @author jh
 */

public class RedBlackTreeNode<T extends Comparable<T>> {
    private T value;
    private RedBlackTreeNode<T> left;
    private RedBlackTreeNode<T> right;
    private RedBlackTreeNode<T> parent;
    private boolean red;

    public RedBlackTreeNode() {}
    
    public RedBlackTreeNode(T value) {
        this.value = value;
    }
    
    public RedBlackTreeNode(T value, boolean isRed) {
        this.value=value;
        this.red = isRed;
    }

    public T getValue() {
        return value;
    }

    void setValue(T value) {
        this.value = value;
    }

    RedBlackTreeNode<T> getLeft() {
        return left;
    }

    void setLeft(RedBlackTreeNode<T> left) {
        this.left = left;
    }

    RedBlackTreeNode<T> getRight() {
        return right;
    }

    void setRight(RedBlackTreeNode<T> right) {
        this.right = right;
    }

    RedBlackTreeNode<T> getParent() {
        return parent;
    }

    void setParent(RedBlackTreeNode<T> parent) {
        this.parent = parent;
    }

    boolean isRed() {
        return red;
    }

    boolean isBlack(){
        return !red;
    }
    
    boolean isLeaf(){
        return left == null && right == null;
    }

    void setRed(boolean red) {
        this.red = red;
    }

    void makeRed() {
        red = true;
    }

    void makeBlack() {
        red = false;
    }
    
    @Override
    public String toString() {
        return "RedBlackTreeNode: " + value.toString() + ", " + (red ? "red" : "black");
    }
}
