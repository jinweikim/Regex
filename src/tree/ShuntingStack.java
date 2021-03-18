package tree;

import java.util.EmptyStackException;
import java.util.Stack;

public class ShuntingStack { // shunting yard algorithm, 将中缀表达式转化为后缀表达式
    private Stack<Node> outputStack; // 输出栈
    private Stack<BranchNode> operaStack; // 运算符栈

    public ShuntingStack() {
        outputStack = new Stack<>();
        operaStack = new Stack<>();
    }

    // 访问者模式
    public void visit(LeftBracketNode leftBracketNode) {

        // 左括号直接压如运算符栈
        operaStack.push(leftBracketNode);

    }

    public void visit(RightBracketNode rightBracketNode){

        // 遇到右括号， 弹出操作符栈顶层元素，直到遇到左括号
        try {
            for (Node node = operaStack.pop(); !(node instanceof LeftBracketNode); node = operaStack.pop()) {
                outputStack.push(node);
            }
        } catch (EmptyStackException e) {
            // throw new Exception(e);
            e.printStackTrace();
        }
    }

    public void visit(LeafNode leafNode) {

        // 非操作符节点直接入栈
        outputStack.push(leafNode);

    }

    public void visit(BranchNode branchNode) {

        // 遇到操作符，若此时操作符栈顶端有元素 e，且 branchNode 优先级小于栈顶元素，则弹出栈顶元素到输出栈，直到遇到优先级更小的元素
        while(!operaStack.isEmpty() && branchNode.getPri() != -1 && branchNode.getPri() <= operaStack.peek().getPri()) {
            outputStack.push(operaStack.pop());
        }
        operaStack.push(branchNode);
    }

    // 将操作符栈中剩余元素依次弹出到输出栈，最后将输出栈元素反转顺序
    public Stack<Node> finish() {
        while (!operaStack.isEmpty()) {
            outputStack.push(operaStack.pop());
        }

        Stack<Node> reversedStack = new Stack<>();
        while (!outputStack.isEmpty()) {
            reversedStack.push(outputStack.pop());
        }

        return reversedStack;
    }

    @Override
    public String toString() {
        return "outputStack" + outputStack;
    }
}
