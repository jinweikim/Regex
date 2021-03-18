package tree;

public class OrNode extends BranchNode {

    @Override
    public void accept(ShuntingStack shuntingStack) {
        shuntingStack.visit(this);
    }

    @Override
    public void accept(OperatingStack operatingStack) {
        operatingStack.visit(this);
    }

    @Override
    public int getPri() {
        return 0;
    }

    @Override
    public Node copy() {
        return new OrNode();
    }

    @Override
    public String toString() {
        return "[O]";
    }
}
