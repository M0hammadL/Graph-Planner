import java.util.ArrayList;
import java.util.List;


public class ActionLayer implements  Layer {


    private List<Mutex> MutexList = new ArrayList<Mutex>();
    private List<Action> LayerActions = new ArrayList<Action>();
    private PredicateLayer previouslayer;

    public ActionLayer(List _Actions, PredicateLayer _previous) {
        LayerActions = _Actions;
        previouslayer = _previous;
    }

    public PredicateLayer MakeNextLayer() {
        List<Predicate> nextpredicate = new ArrayList<Predicate>();
        for (Action act : LayerActions) {
            List<Predicate> effects = new ArrayList<>();
            for ( Predicate temppredicate : act.getAddEffects()){
                effects.add(temppredicate);
            }
            for ( Predicate temppredicate : act.getDeleteEffects()){
                effects.add(temppredicate);
            }
            for (Predicate _predicate : effects) {
                boolean flag = false;
                String predicatename = _predicate.getName();
                for ( Predicate _predicate2 : nextpredicate){
                    String predicatename2 = _predicate2.getName();
                    if ( predicatename.equalsIgnoreCase(predicatename2) && _predicate.getTruth() == _predicate2.getTruth() && _predicate.getParamString().equals(_predicate2.getParamString())){
                        _predicate2.AddCausal(act);
                        flag = true;
                    }
                }
                if ( ! flag ) {
                    Predicate newpredicate = new Predicate(_predicate.getName(),_predicate.getTruth(),_predicate.getNum_Par(),_predicate.getPar());
                    newpredicate.AddCausal(act);
                    nextpredicate.add(newpredicate);
                }
            }

        }


        PredicateLayer predicateLayer = new PredicateLayer(nextpredicate, this);
        return predicateLayer;

    }


    public void checkmutex() {

        for (int i = 0; i < LayerActions.size() - 1; i++) {

            for (int j = i + 1; j < LayerActions.size(); j++) {

                for (Predicate effect1 : LayerActions.get(i).getDeleteEffects()) {

                    for (Predicate effect2 : LayerActions.get(j).getAddEffects()) {

                        if (effect1.getName().equalsIgnoreCase(effect2.getName()) && effect1.getParamString().equals(effect2.getParamString()) && effect1.getTruth()!= effect2.getTruth()) {

                            Mutex mutex = new Mutex(LayerActions.get(i), LayerActions.get(j));

                            if ( ! MutexExist(mutex)) {

                                MutexList.add(mutex);

                            }
                            //MutexList.add(mutex);
                            break;
                        }
                    }
                    for (Predicate precond : LayerActions.get(j).getPreCondition()) {
                        if (effect1.getName().equalsIgnoreCase(precond.getName()) && effect1.getParamString().equals(precond.getParamString())&& effect1.getTruth() != precond.getTruth()) {
                            Mutex mutex = new Mutex(LayerActions.get(i), LayerActions.get(j));
                            if ( ! MutexExist(mutex)) {
                                MutexList.add(mutex);
                            }
                            break;
                        }
                    }

                }
                for (Predicate effect1 : LayerActions.get(i).getAddEffects()) {
                    for (Predicate effect2 : LayerActions.get(j).getDeleteEffects()) {
                        if (effect1.getName().equalsIgnoreCase(effect2.getName()) && effect1.getParamString().equals(effect2.getParamString()) && effect1.getTruth() != effect2.getTruth()) {
                            Mutex mutex = new Mutex(LayerActions.get(i), LayerActions.get(j));
                            if ( ! MutexExist(mutex)) {
                                MutexList.add(mutex);
                            }

                            break;
                        }
                    }
                    //if ( ! (LayerActions.get(i).getName().equals("NO-OP") || LayerActions.get(j).getName().equals("NO-OP"))) {
                        for (Predicate precond : LayerActions.get(i).getPreCondition()) {
                            if (effect1.getName().equalsIgnoreCase(precond.getName()) && effect1.getParamString().equals(precond.getParamString()) && effect1.getTruth() != precond.getTruth()) {
                                Mutex mutex = new Mutex(LayerActions.get(i), LayerActions.get(j));
                                if (!MutexExist(mutex)) {
                                    MutexList.add(mutex);
                                }

                                break;
                            }
                        }
                   // }

                }

                for (Predicate precond1 : LayerActions.get(i).getPreCondition()) {
                    for (Predicate precond2 : LayerActions.get(j).getPreCondition()) {
                        //if ( ! (LayerActions.get(i).getName().equals("NO-OP") || LayerActions.get(j).getName().equals("NO-OP"))) {
                            MutexPredicate mutex = new MutexPredicate(precond1, precond2);
                            if (previouslayer.MutexExist(mutex)) {
                                Mutex _mutex = new Mutex(LayerActions.get(i), LayerActions.get(j));
                                if (!MutexExist(_mutex)) {
                                    MutexList.add(_mutex);
                                }

                                break;
                            }
                        //}
                    }

                }


            }
        }
    }


    public Boolean MutexExist(Mutex mutex){
        for (Mutex _mutex : MutexList){
            if ( _mutex.Equal(mutex )){
                return true;
            }
        }
        return false;
    }


    public List<Action> getLayerActions() {
        return LayerActions;
    }

    public List<Mutex> getMutexList() {
        return MutexList;
    }
}


/*for ( Predicate predicate : previouslayer.getLayerPredicates()){
            boolean breakflag = false;
            for ( Predicate _newpredicate : nextpredicate){
                if ( predicate.getName().equals(_newpredicate.getName()) && predicate.getTruth() == _newpredicate.getTruth() && predicate.getParamString().equals(_newpredicate.getParamString())){
                    _newpredicate.AddCausal(new Action("NO-OP",0,null,null,null,null));
                    breakflag = true;
                    break;
                }
            }
            if ( ! breakflag ){
                Predicate predicate2 = new Predicate(predicate.getName(),predicate.getTruth(),predicate.getNum_Par(),predicate.getPar());
                predicate2.AddCausal(new Action("NO-OP",0,null,null,null,null));
                nextpredicate.add(predicate2);
            }
        }
        */