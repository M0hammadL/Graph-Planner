import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohammad on 8/4/2016.
 */
public class Goals {
    List<Predicate> GoalsList = new ArrayList<Predicate>();

    public Goals(List<Predicate> _goals){
        GoalsList = _goals;
    }
    public Boolean Satisfied(PredicateLayer predicateLayer){
        Boolean flag = true;
        Boolean flag2;
        List<Predicate> tempgoals = new ArrayList<>();
        for ( Predicate predicate : GoalsList){
            flag2 = false;
            for ( Predicate layerpredicate : predicateLayer.getLayerPredicates()){
                if ( predicate.getName().equalsIgnoreCase(layerpredicate.getName()) && predicate.getTruth() == layerpredicate.getTruth() && predicate.getParamString().equals(layerpredicate.getParamString())){
                    tempgoals.add(layerpredicate);
                    flag2 = true;
                    break;
                }
            }
            if ( ! flag2 ){
                flag = false;
                break;
            }
        }

        for ( int i=0;i<tempgoals.size() - 1;i++){
            for ( int j=i+1;i<tempgoals.size();i++){
                if ( predicateLayer.MutexExist(new MutexPredicate(tempgoals.get(i),tempgoals.get(j)))){
                    return false;
                }
            }
        }

        return flag;
    }

    public List<Predicate> getGoalsList() {
        return GoalsList;
    }
}
