package tree;

public class ManyNode extends BranchNode{

    public ManyNode() {
        super();
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
        return 2;
    }

    @Override
    public Node copy() {
        return new ManyNode();
    }

    @Override
    public String toString() {
        return "[M]";
    }
}
