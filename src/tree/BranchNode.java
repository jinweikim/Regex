package tree;

public abstract class BranchNode extends Node {
    public abstract int getPri();

    public void operate(Node left, Node right) {
        setLeft(left);
        setRight(right);
    }

}
