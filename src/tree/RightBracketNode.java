package tree;

public class RightBracketNode extends BranchNode{

    @Override
    public Node copy() {
        return new RightBracketNode();
    }

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
        return 3;
    }

    @Override
    public String toString() {
        return "[)]";
    }
}
