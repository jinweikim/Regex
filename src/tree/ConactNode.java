package tree;

public class ConactNode extends BranchNode {

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
        return 1;
    }

    @Override
    public Node copy() {
        return new ConactNode();
    }

    @Override
    public String toString() {
        return "[C]";
    }
}
