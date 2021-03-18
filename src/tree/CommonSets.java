package tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.CheckedOutputStream;

public class CommonSets {

    private static int TOKEN_LENGTH = 128;

    private static char[] LW; // \w

    static{
        List<Character> chList = new ArrayList<>();
        for(char c = 'a'; c <= 'z'; c++ ){
            chList.add(c);
        }

        for(char c = 'A'; c <= 'Z'; c++ ){
            chList.add(c);
        }

        for(char c = '0'; c <= '9'; c++ ){
            chList.add(c);
        }

        chList.add('_');
        LW = listToArray(chList);
    }

    private static final char[] UW = complementarySet(LW); // \W
    private static final char[] LS = new char[]{' ','\t'};
    private static final char[] US = complementarySet(LS);
    private static final char[] LD = new char[]{'0','1','2','3','4','5','6','7','8','9'};
    private static final char[] UD = complementarySet(LD);
    private static final char[] DOT = complementarySet(new char[]{'\n'});


    public static List<Character> interpretToken(String token) throws Exception {
        List<Character> result;
        if(token.length() == 1){
            if( token.charAt(0) == '.'){
                result = arrayToList(DOT);
            }else{
                result = Collections.singletonList(token.charAt(0));
            }
        }else if(token.length() != 2 || token.charAt(0) != '\\'){
            System.out.println("error in interpretToken");
            throw new Exception();
        }else{
            switch (token.charAt(1)){
                case 'n':
                    result = Collections.singletonList('\n');
                    break;
                case 'r':
                    result = Collections.singletonList('\r');
                    break;
                case 't':
                    result = Collections.singletonList('\t');
                    break;
                case 'w':
                    result = arrayToList(LW);
                    break;
                case 'W':
                    result = arrayToList(UW);
                    break;
                case 's':
                    result = arrayToList(LS);
                    break;
                case 'S':
                    result = arrayToList(US);
                    break;
                case 'd':
                    result = arrayToList(LD);
                    break;
                case 'D':
                    result = arrayToList(UD);
                    break;
                default:
                    result = Collections.singletonList(token.charAt(1));
                    break;
            }
        }
        return result;
    }

    // 初始化记录表，用来记录每个字符是否出现
    public static boolean[] newTable(){
        boolean[] table = new boolean[TOKEN_LENGTH];
        for(boolean t : table){
            t = false;
        }
        return table;
    }

    // 最小化字符集，包括清除重复字符以及重新排序
    public static char[] minium(char[] set){

        boolean[] table = newTable();
        for(char c : set){
            table[c] = true;
        }
        return tableToSet(table,true);

    }

    // 求补集
    public static char[] complementarySet(char[] set){
        boolean[] table = newTable();

        for(char b : set){
            table[b] = true;
        }
        return tableToSet(table,false);

    }

    public static char[] listToArray(List<Character> chList){
        char[] result = new char[chList.size()];
        for(int i=0; i < chList.size(); i++){
            result[i] = chList.get(i);
        }
        return result;
    }

    public static List<Character> arrayToList(char[] charArr){
        List<Character> chList = new ArrayList<>();
        for(char c : charArr){
            chList.add(c);
        }
        return chList;

    }

    // 求补集是 flag 为 false, 最小化时为 true;
    public static char[] tableToSet(boolean[] table, boolean flag){

        char[] resultSet = new char[TOKEN_LENGTH];
        int j = 0;
        for(char i=0; i < table.length; i++){
            if(table[i] == flag){
                resultSet[j++] = i;
            }
        }
        return resultSet;


    }
}
