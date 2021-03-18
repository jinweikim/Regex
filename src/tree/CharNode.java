package tree;

public class CharNode extends LeafNode {

    private final char c;

    public CharNode(char c) {
        this.c = c;
    }

    @Override
    public Node copy() {
        return new CharNode(c);
    }

    @Override
    public String toString() {
        if (c == ' ') {
            return "\\s";
        } else if (c == '\t') {
            return "\\t";
        } else {
            return String.valueOf(c);
        }
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
