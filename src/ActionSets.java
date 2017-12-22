import java.util.ArrayList;
import java.util.List;


public class ActionSets {
    private List<Action> ActionList = new ArrayList<Action>();
    private static List<Action> allActionsList = new ArrayList<>();

    public void AddAction(Action act){
        ActionList.add(act);
    }

    public List<Action> getActionList() {
        return ActionList;
    }

    public  void setActionList(List actionList) {
        ActionList = actionList;
    }

    public Action getAction(int index){
        if ( index >= ActionList.size()) {
            return null;
        }else {
            return ActionList.get(index);
        }
    }

    public static List<Action> getAllActionsList() {
        return allActionsList;
    }

    public static void setAllActionsList(List<Action> allActionsList) {
        ActionSets.allActionsList = allActionsList;
    }


}
