package tree;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommonSetsTest {

    CommonSets cs = new CommonSets();
    char[] ch = {'f','a','a','3','5','g','a','6'};
    @org.junit.jupiter.api.Test
    void minium() {
        char[] miniumSet = CommonSets.minium(ch);
        System.out.println(miniumSet);
    }

    @org.junit.jupiter.api.Test
    void complementarySet() {
        char[] complementarySet = CommonSets.complementarySet(ch);
        System.out.println(complementarySet);
    }

    @org.junit.jupiter.api.Test
    void interpretToken() throws Exception {
        String token = "\\D";
        List<Character> list = CommonSets.interpretToken(token);
        System.out.println(list);

    }
}