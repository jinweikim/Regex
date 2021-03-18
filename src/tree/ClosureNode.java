package tree;

public class ClosureNode extends LeafNode {

    @Override
    public Node copy() {
        return null;
    }

    @Override
    public String toString() {
        return "{CLO}";
    }

    @Override
    public void accept(ShuntingStack shuntingStack) {
        shuntingStack.visit(this);
    }

    @Override
    public void accept(OperatingStack operatingStack) {
        operatingStack.visit(this);
    }

}
