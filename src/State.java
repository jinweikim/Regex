import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class State {


    // 状态数量，用于给状态 id 赋值
    protected static int idCount = 0;

    // 唯一标识此状态
    protected int stateId;

    // 代表该状态的类型
    protected String stateType;

    // 在图中与该状态相连的状态集
    protected Map<String,List<State>> next;


    public State(){
        this.stateId = idCount++;
        next = new HashMap<>();
    }

    /**
     * 添加状态
     * @param edge
     * @param nextState
     */
    public void addNext(String edge, State nextState){
        List<State> listHasEdge = next.get(edge);

        // 该边没有状态相连
        if( listHasEdge == null ){
            listHasEdge = new ArrayList<>();
            listHasEdge.add(nextState);
            next.put(edge,listHasEdge);
        }else{ // 该边已经有状态相连
            listHasEdge.add(nextState);
        }
    }




}
