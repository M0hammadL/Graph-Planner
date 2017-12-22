import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;
import java.util.List;


public class PredicateLayer implements Layer{
    private List<Predicate> LayerPredicates= new ArrayList<Predicate>();
    private ActionLayer previouslayer;
    private List<MutexPredicate> Mutexlist = new ArrayList<MutexPredicate>();

    public PredicateLayer(List<Predicate> predicates , ActionLayer _previouslayer ) {
        LayerPredicates = predicates;
        previouslayer = _previouslayer;

    }

    public PredicateLayer(List<Predicate> predicates ) {
        LayerPredicates = predicates;
        previouslayer = null;
    }
    @Override
    public ActionLayer MakeNextLayer() {
        List<Action> PossibleActions = new ArrayList<Action>();
        List<Action> actionlist = new ActionSets().getAllActionsList();
        for(Action action : actionlist) {
            boolean flag1 = true;
            boolean flag2 = false;
            //System.out.println(action.getName());
                for (Predicate pred : action.getPreCondition()) {
                    flag2 = false;
                    for (Predicate currentpreds : LayerPredicates) {
                        if (pred.getName().equalsIgnoreCase(currentpreds.getName()) && pred.getTruth() == currentpreds.getTruth() && pred.getParamString().equals(currentpreds.getParamString())) {
                            flag2 = true;
                            break;
                        }
                    }
                    if (!flag2) {
                        flag1 = false;
                        break;
                    }
                }
                if (flag1) {


                    Action newact = new Action(action.getName(), action.getNumpar() , action.getParametrs(), action.getPreCondition(),action.getDeleteEffects(), action.getAddEffects() );

                    PossibleActions.add(newact);
                }

            //}
        }
        ActionLayer NextActionLayer = new ActionLayer(PossibleActions,this);
        return NextActionLayer;
    }


    public void checkmutex() {

        for ( int i=0;i<LayerPredicates.size()-1;i++){
            for (int j=i+1;j<LayerPredicates.size();j++){

                    if ( LayerPredicates.get(i).getName().equalsIgnoreCase(LayerPredicates.get(j).getName()) && (  LayerPredicates.get(i).getTruth() != (LayerPredicates.get(j).getTruth())) && LayerPredicates.get(i).getParamString().equals(LayerPredicates.get(j).getParamString())) {
                        MutexPredicate mutexPredicate = new MutexPredicate(LayerPredicates.get(i),LayerPredicates.get(j));
                        Mutexlist.add(mutexPredicate);
                        //break;
                    }else {
                        List<Action> causal1 = LayerPredicates.get(i).getCausal();
                        List<Action> causal2 = LayerPredicates.get(j).getCausal();

                        boolean flag = true;

                        for (Action act1 : causal1) {
                            for (Action act2 : causal2) {
                                Mutex mutex = new Mutex(act1, act2);
                                if ( ! previouslayer.MutexExist(mutex)) {
                                    flag = false;
                                    break;
                                }
                            }
                            if ( ! flag ) {
                                break;

                            }
                        }
                        if (flag) {
                            MutexPredicate mutexPredicate = new MutexPredicate(LayerPredicates.get(i), LayerPredicates.get(j));
                            Mutexlist.add(mutexPredicate);
                        }

                    }
            }
        }
    }


    public Boolean MutexExist(MutexPredicate mutex){

        for (MutexPredicate _mutex : Mutexlist){
            if ( _mutex.Equal(mutex)){
                return true;
            }
        }
        return false;
    }

    public List<Predicate> getLayerPredicates() {
        return LayerPredicates;
    }

    public List<MutexPredicate> getMutexList() { return Mutexlist; }

}
