package tree;

public abstract class Node {

    private Node left;
    private Node right;

    public Node(){
        left = null;
        right = null;
    }

    public boolean hasLeft(){
        return left != null;
    }

    public boolean hasRight(){
        return right != null;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public abstract void accept(ShuntingStack shuntingStack);

    public abstract void accept(OperatingStack operatingStack);

    public abstract Node copy();

    public abstract String toString();


}
