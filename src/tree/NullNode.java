package tree;

public class NullNode extends LeafNode{

    @Override
    public void accept(ShuntingStack shuntingStack) {
        shuntingStack.visit(this);
    }

    @Override
    public void accept(OperatingStack operatingStack) {
        operatingStack.visit(this);
    }

    @Override
    public Node copy() {
        return new NullNode();
    }

    @Override
    public String toString() {
        return "{N}";
    }
}
