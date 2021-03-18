package tree;

import static org.junit.jupiter.api.Assertions.*;

class ASTTest {

    @org.junit.jupiter.api.Test
    void partTest() {
        String regex = "123{12}";
        int index = regex.indexOf('{')+1;
        int rightIndex = regex.indexOf('}');
        String num = regex.substring(index,rightIndex); // 将 {} 中内容取出
        System.out.println("num: " + num);
        int commaIndex = num.indexOf(','); // 逗号的位置
        String least;  // {} 中的第一个数
        String most;   // {} 中的第二个数
        if(commaIndex == -1){
            least = num;
            most = "-1";
        }else{
            least = num.substring(0,commaIndex);
            if( commaIndex == num.length()-1 ){ // 例{12,}
                most = least;
            }else{
                most = num.substring(commaIndex+1); // 例{12,15}
            }
        }
        System.out.println(least);
        System.out.println(most);

        regex = "(a*b|ab*)";
        AST ast = null;
        try {
            ast = new AST(regex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(ast.getNodeList());
        ast.shunt();
        System.out.println(ast.getNodeStack());
        ast.buildTree();
        System.out.println(ast.getRoot());
        System.out.println();

    }

}