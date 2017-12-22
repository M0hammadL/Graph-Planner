import java.util.ArrayList;
import java.util.List;


public class Planner {
    static boolean weshouldextend = true;
    static String plan[];
    static boolean wegotplan = false;

    public static void main(String args[]){
        int counter=0;
        Parser parser = new Parser();
        parser.starter();
        int level = 0;
        PredicateLayer InitialLayer = new PredicateLayer(parser.getInitialState());
        Graph graph = new Graph(InitialLayer);
        Goals goals = new Goals(parser.getGoalState());
        ActionSets initialactionset = new ActionSets();
        initialactionset.setAllActionsList(parser.getFinalaction());
        do {

            do {
                //if(!(level_off(graph)) ){
                counter++;
                System.out.printf("%d layers has been expanded\n",counter);
                graph.nextstep();//}
               // else
                    //reak;

            }
            while (!goals.Satisfied((PredicateLayer) graph.getLastLayer()) && ! level_off(graph)  );
            if ( ! level_off(graph)) {
                plan = new String[graph.getNumlayers() - 1];
                List<Predicate> tempGoals = new ArrayList<Predicate>();

                for (Predicate literal : ((PredicateLayer) graph.getLastLayer()).getLayerPredicates()) {
                    for (Predicate goal : goals.getGoalsList()) {
                        if (literal.getName().equals(goal.getName()) && literal.getTruth() == goal.getTruth() && literal.getParamString().equals(goal.getParamString())) {
                            tempGoals.add(literal);
                        }
                    }
                }
                System.out.println("goal has been satisfied at level \n");

                CheckifPlanExist(graph, tempGoals, graph.getNumlayers() - 1);
            }
        }while ( weshouldextend && ! level_off(graph));
        if ( ! weshouldextend) {
            fixoutput();
            for (int i = 1; i < plan.length; i = i + 2) {
                System.out.println(" Level " + (i / 2 + 1) + " " + plan[i]);
            }
        }else {
            System.out.println("WE GOT LEVEL OFF");
        }
    }


    public static void CheckifPlanExist(Graph graph , List<Predicate> goals,int level){
        String thislvlplan = "";
        Boolean breakthis = true;
        Boolean lastone = false;
        if ( level == 0 ){

            weshouldextend = false;
            wegotplan = true;
            return;
        }else{

        List<ActionSets> goalscausals = new ArrayList<ActionSets>();
        for ( Predicate predicate : goals){
            ActionSets tempactionset = new ActionSets();
            tempactionset.setActionList(predicate.getCausal());
            goalscausals.add(tempactionset);
        }

        int[] itterations = new int[goalscausals.size()];
        int[] sizeitterator = new int[itterations.length];

        for ( int i=0;i<goalscausals.size();i++){
            sizeitterator[i] =  goalscausals.get(i).getActionList().size() - 1;
        }

        while ( ! lastone ) {
            ActionSets actionstodo = new ActionSets();
            for ( int i=0;i<goalscausals.size();i++ ){
                actionstodo.AddAction(goalscausals.get(i).getAction(itterations[i]));
            }

            plan[level - 1] = "";

            itterations = increament(itterations , sizeitterator );
            if (itterations == null ){
                lastone = true;
            }
            // now check if there is a mutex btw this
            breakthis = true;
            for ( int i=0;i<actionstodo.getActionList().size();i++){
                for (int j=0;j<actionstodo.getActionList().size();j++){
                    if ( i == j ){
                        continue;
                    }else{

                        Mutex mutex = new Mutex(actionstodo.getAction(i),actionstodo.getAction(j));
                        List<Mutex> mutexList = ((ActionLayer) graph.getLayer(level-1)).getMutexList();
                        //System.out.println(mutex.getAction1().getName()  + "     "  + mutex.getAction2().getName());

                        for (Mutex mutex2 : mutexList) {
                            if (mutex.Equal(mutex2)) {
                                breakthis = false;
                                break;
                            }
                        }
                        if ( ! breakthis){
                            break;
                        }
                    }
                }
                if ( ! breakthis){
                    break;
                }
            }
            if ( breakthis ){
                List<Predicate> newgoals = new ArrayList<Predicate>();
                plan[level - 1] = "";
                for ( Action action : actionstodo.getActionList() ){
                    plan[level - 1] = plan[level - 1] +  "At level : "  + " " +  action.getName() +  "  " + action.getParamString() + "   ";
                    //addActiontoPlan(str);
                    for ( Predicate _predicate : action.getPreCondition()){
                        PredicateLayer lastpredicatelayer = (PredicateLayer )graph.getLayer(level - 2);
                        Predicate predicate = null;
                        for ( Predicate temppredicate : lastpredicatelayer.getLayerPredicates()){
                            if (_predicate.getName().equalsIgnoreCase(temppredicate.getName()) && _predicate.getTruth() == temppredicate.getTruth() && _predicate.getParamString().equals(temppredicate.getParamString())){
                                predicate = temppredicate;
                            }
                        }
                        Boolean flag = true;
                        for ( Predicate predicate2 : newgoals) {
                            if ( predicate2.getName().equalsIgnoreCase(predicate.getName()) && predicate2.getTruth() == predicate.getTruth() && predicate.getParamString().equals(predicate2.getParamString()) ){
                                flag = false;
                                break;
                            }
                        }
                        if ( flag ){
                            newgoals.add(predicate);
                        }
                    }
                }
                CheckifPlanExist( graph , newgoals, level - 2);
                if ( wegotplan){
                    return;
                }
            }
        }


        }
    }


    public static int[] increament(int[] input, int[] index){

        input[input.length - 1 ]++;
        for (int i=input.length - 1 ; i >= 0; i--) {
            if ( input[i] >  index[i]){
                if ( i > 0 ){
                    input[i - 1 ]++;
                    input[i] = 0;
                }else{
                    return null;
                }
            }
        }
        return input;}


        public  static boolean level_off(Graph g){
        boolean flag = false;
        boolean pfalg=false;
        int scont=0;
            if (g.getNumlayers()>2) {
                for (Predicate predicate1 : ((PredicateLayer) g.getLastLayer()).getLayerPredicates()) {
                    for (Predicate predicate2 : (((PredicateLayer) g.getLayer(g.getNumlayers() - 3)).getLayerPredicates())) {
                        if ((predicate1.getName().equalsIgnoreCase(predicate2.getName())) && (predicate1.getTruth() == predicate2.getTruth()) && (predicate1.getParamString().equals(predicate2.getParamString()))) {
                            flag = true;
                        }
                        if (!flag) {
                            break;
                        } else
                            scont++;


                    }

                }
                if (scont == ((PredicateLayer) g.getLastLayer()).getLayerPredicates().size()) {
                    pfalg = true;

                }

                flag = false;
                boolean nfalg = false;
                scont = 0;

                for (MutexPredicate mutex1 : ((PredicateLayer) g.getLastLayer()).getMutexList()) {
                    for (MutexPredicate mutex2 : (((PredicateLayer) g.getLayer(g.getNumlayers() - 3)).getMutexList())) {
                        if (mutex1.Equal(mutex2)) {
                            flag = true;
                        }
                        if (!flag) {
                            break;
                        } else
                            scont++;
                    }

                }
                if (scont == ((PredicateLayer) g.getLastLayer()).getMutexList().size()) {
                    nfalg = true;

                }
                return (nfalg && pfalg);
            }
            else
                return false;
    }

    public static void fixoutput(){
        for ( int i=0;i<plan.length;i++){
            if ( plan[i] != null ){
                String temp[] = plan[i].split("At level : ");
                List<String> newplan = new ArrayList<>();
                Boolean flag;
                for ( int j = 1; j < temp.length;j++){
                    flag = true;
                    for (int k=0;k < newplan.size();k++){
                        if ( temp[j].equals(newplan.get(k))){
                            flag = false;
                            break;
                        }
                    }
                    if ( flag){
                        if ( ! temp[j].contains("NO-OP")) {
                            newplan.add(temp[j]);
                        }
                    }
                }
                plan[i] = "";
                for ( String str : newplan){
                    plan[i] = plan[i] + "\t " + str;
                }
            }
        }

    }

/*
    public  static void resetplan(){
        plan = "";
    }

    public static void addActiontoPlan(String str){
        plan = plan + "  " + str;
    }
*/

}
/*   for ( Predicate predicate1 : goals){
                for (Predicate predicate2 : goals) {
                    if (predicate1.getName().equals(predicate2.getName()) && predicate1.getTruth().equals(predicate2.getTruth())) {
                        continue;
                    }
                    List<Action> causals1 = predicate1.getCausal();
                    List<Action> causals2 = predicate2.getCausal();
                    for (Action action1 : causals1) {
                        for (Action action2 : causals2) {
                            Mutex tempMutex = new Mutex(action1, action2);
                            for (Mutex mutex : (( ActionLayer ) graph.getLayer(level-1)).getMutexList()) {
                                if (mutex.Equal(tempMutex)) {
                                    twopredicateflag = true;
                                    break;;
                                }
                            }
                            if (twopredicateflag) {
                                continue;;
                            }
                        }
                    }
                }
            }
        */