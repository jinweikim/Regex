package tree;

public class LeftBracketNode extends BranchNode{

    @Override
    public Node copy() {
        return new LeftBracketNode();
    }

    @Override
    public void accept(ShuntingStack shuntingStack) {
        shuntingStack.visit(this);
    }

    @Override
    public void accept(OperatingStack operatingStack) {
        operatingStack.visit(this);
    }

    // 优先级
    @Override
    public int getPri() {
        return -1;
    }

    @Override
    public String toString() {
        return "[(]";
    }
}
