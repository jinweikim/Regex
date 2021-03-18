package tree;

import java.util.*;

public class AST {
    private String regex;
    private boolean itemTerminated;
    private List<Node> nodeList;
    private Stack<Node> nodeStack;

    private Node root;

    public AST(String regex) throws Exception {

        root = null;
        this.regex = regex;
        nodeList = new ArrayList<>();
        itemTerminated = false;

        normalize();
    }

    // 构建语法树
    public void buildTree() {
        OperatingStack operatingStack = new OperatingStack();
        while( !nodeStack.isEmpty()) {
            Node node = nodeStack.pop();
            node.accept(operatingStack);
        }

        try{
            root = operatingStack.pop();
        } catch (EmptyStackException e) {
            e.printStackTrace();
        }
        if (!operatingStack.isEmpyt()) {
            System.out.println("语法错误");
        }
    }



    public void shunt() {
        ShuntingStack shuntingStack = new ShuntingStack();
        for (Node node : nodeList) {
            node.accept(shuntingStack);
        }
        nodeStack = shuntingStack.finish();
    }

    public List<Node> getNodeList() {
        return nodeList;
    }

    public List<Node> getNodeStack() {
        return nodeStack;
    }

    private void normalize() throws Exception {
        int index = 0;
        while( index < regex.length()){
            char ch = regex.charAt(index++);
            switch(ch){
                case '[':{
                    tryConact();
                    boolean isComplementary;
                    if( regex.charAt(index++) == '^'){
                        isComplementary = true;
                    }else{
                        isComplementary = false;
                    }
                    List<Character> allTokenSet =  new ArrayList<>();
                    List<Character> oneTokenSet;

                    // 遍历每个字符，构建字符集
                    for(char next = regex.charAt(index++); next != ']'; next = regex.charAt(index++) ){
                        String token;
                        // \s,\t此类字符以及 .
                        if( next == '\\' || next == '.'){
                            if( next == '\\'){
                                token = new String(new char[]{next,regex.charAt(index++)});
                            }else{
                                token = String.valueOf('.');
                            }
                            oneTokenSet = CommonSets.interpretToken(token);
                            allTokenSet.addAll(oneTokenSet);
                        }else{ // 其他普通字符
                            allTokenSet.add(next);
                        }
                    }
                    // 最小化，去重
                    char[] modifiedCharSet = CommonSets.minium(CommonSets.listToArray(allTokenSet));

                    // 若开头字符为 ^ 则求补集
                    if(isComplementary){
                        modifiedCharSet = CommonSets.complementarySet(modifiedCharSet);
                    }

                    // 添加 '('
                    nodeList.add(new LeftBracketNode());
                    for(int i = 0; i < modifiedCharSet.length; i++){
                        nodeList.add(new CharNode(modifiedCharSet[i]));
                        // 最后一个字符后不加或
                        if( i == modifiedCharSet.length - 1 || modifiedCharSet[i+1] == 0){
                            break;
                        }
                        nodeList.add(new OrNode());
                    }
                    // 添加 ')'
                    nodeList.add(new RightBracketNode());
                    itemTerminated = true;
                    break;
                }
                case '{':{
                    int rightIndex = regex.indexOf('}');
                    String num = regex.substring(index,rightIndex); // 将 {} 中内容取出
                    System.out.println("num: " + num);
                    int commaIndex = num.indexOf(','); // 逗号的位置
                    int least;  // {} 中的第一个数
                    int most;   // {} 中的第二个数
                    if(commaIndex == -1){ // 例{12}
                        least = Integer.parseInt(num);
                        most = -1;
                    }else{
                        least = Integer.parseInt(num.substring(0,commaIndex));
                        if( commaIndex == num.length()-1 ){ // 例{12,}
                            most = least;
                        }else{
                            most = Integer.parseInt(num.substring(commaIndex+1)); // 例{12,15}
                        }
                    }
                    performMany(least, most);
                    itemTerminated = true;
                    break;
                }
                case '(': {
                    tryConact();
                    nodeList.add(new LeftBracketNode());
                    itemTerminated = false;
                    break;
                }
                case ')': {
                    nodeList.add(new RightBracketNode());
                    itemTerminated = true;
                    break;
                }
                case '*': {
                    performMany(0, -1);
                    itemTerminated = true;
                    break;
                }
                case '+': {
                    performMany(1, -1);
                    itemTerminated = true;
                    break;
                }
                case '|': {
                    nodeList.add(new OrNode());
                    itemTerminated = false;
                    break;
                }
                // 普通字符
                default: {
                    tryConact();
                    if(ch == '\\' || ch == '.') {
                        String token;
                        if (ch == '\\') {
                            char next = regex.charAt(index++);
                            token = new String(new char[]{ch, next});
                        } else {
                            token = String.valueOf(ch);
                        }
                        List<Character> tokenSet = CommonSets.interpretToken(token);
                        nodeList.add(new LeftBracketNode());
                        nodeList.add(new CharNode(tokenSet.get(0)));
                        for (int i = 1; i < tokenSet.size(); i++) {
                            nodeList.add(new OrNode());
                            nodeList.add(new CharNode(tokenSet.get(i)));
                        }
                        nodeList.add(new RightBracketNode());
                    } else {
                        nodeList.add(new CharNode(ch));
                    }

                    itemTerminated = true;
                    break;
                }
            }
        }
    }

    /**
     * 重复匹配
     * * 0或多次 (0,-1)
     * ？0或一次 (0,1)
     * + 一次或多次 (1,-1)
     * @param least
     * @param most
     */

    private void performMany(int least, int most){
        if(!(least == 1 && most == 1)){
            // 0或多次
            if(least == 0 && most == -1){
                nodeList.add(new ManyNode());
                nodeList.add(new NullNode());
            }else{
                List<Node> bag;
                // 以右括号结尾
                if ( last() instanceof RightBracketNode) {
                    bag = new LinkedList<>();
                    bag.add(nodeList.remove(nodeList.size() - 1));
                    int stack = 1;
                    for (int i = nodeList.size() - 1; i >= 0; i--) {
                        Node node = nodeList.remove(i);
                        // 将 () 内的节点放入 bag 中
                        if( node instanceof RightBracketNode) {
                            stack++;
                        } else if (node instanceof LeftBracketNode) {
                            stack--;
                        }
                        bag.add(0, node);
                        if (stack == 0) {
                            break;
                        }
                    }
                } else {
                   bag = Collections.singletonList(nodeList.remove(nodeList.size() - 1));
                }

                if (most == -1) {
                    for (int i = 0; i < least; i++) {
                       nodeList.addAll(copyNodes(bag));
                       nodeList.add(new ConactNode());
                    }
                    nodeList.addAll(copyNodes(bag));
                    nodeList.add(new ManyNode());
                    nodeList.add(new NullNode());
                } else {
                    if (least != most) {
                        nodeList.add(new LeftBracketNode());
                        for (int i = least; i <= most; i++){
                            nodeList.add(new LeftBracketNode());
                            if (i == 0) {
                                nodeList.add(new ClosureNode());
                            } else {
                                for (int j = 0; j < i; j++) {
                                    nodeList.addAll(copyNodes(bag));
                                    if (j != i - 1) {
                                        nodeList.add(new ConactNode());
                                    }
                                }
                            }
                            nodeList.add(new RightBracketNode());
                            if (i != most) {
                                nodeList.add(new OrNode());
                            }
                        }
                        nodeList.add(new RightBracketNode());
                    } else {
                        nodeList.add(new LeftBracketNode());
                        for(int i = 0; i < least; i++) {
                            nodeList.addAll(copyNodes(bag));
                            if(i != least - 1) {
                                nodeList.add(new ConactNode());
                            }
                        }
                        nodeList.add(new RightBracketNode());
                    }
                }
            }
        }

    }

    public List<Node> copyNodes(List<Node> bag) {
        List<Node> result = new ArrayList<>(bag.size());
        for (Node node : bag) {
            result.add(node.copy());
        }
        return result;
    }

    /**
     * 添加 Conact 节点
     */
    private void tryConact() {
        if (itemTerminated) {
            nodeList.add(new ConactNode());
            itemTerminated = false;
        }
    }

    // 返回最后一个节点
    private Node last() {
        return nodeList.get(nodeList.size() - 1);
    }

    public Node getRoot() {
        return root;
    }
}
