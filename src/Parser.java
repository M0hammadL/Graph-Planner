

import javafx.beans.binding.ListBinding;

import java.io.*;
import  java.util.List;
import  java.util.ArrayList;
import java.util.Objects;


/**
 * Created by Mohammad on 8/6/2016.
 */
public class Parser {
    public static String []obj;
    public static String file;
    private  static  Predicate[] predicateList;
    private static Action[] operatorList;
    private static List<Predicate> initialState;
    private static List<Predicate> goalState;
    private  List<Action> Actionlist;
    private  List<Predicate> Gpredicate;
    private static String perm;
    private static List<Predicate> finalpredicates = new ArrayList<Predicate>();
    private static List<Action> finalaction = new ArrayList<Action>();


    public static void starter() {
        new Parser();
        File f =new File("domain.txt");
        File h =new File("twelve-step.txt");
        getFile(f );
        initial(h);
        makefinalpredicates();
        makefinaloperation();
        //Action noop = new Action("NO-OP",0,null,null,null,null);
        //finalaction.add(noop);
        /*for (Predicate goal : goalState) {
            Predicate notGoal = new Predicate(goal.getName(), ! goal.getTruth(), goal.getNum_Par(), goal.getPar());
            initialState.add(notGoal);
        }*/
        GenerateNoOP();
        int ramin = 8;



    }

    public static void initial(File initial){
        String line;
        int scont=0;
        try{
            BufferedReader br =new BufferedReader(new FileReader(initial));
            boolean first =false;
            while ((line = br.readLine()) != null) {
                if (line.length() > 0 && first==false ){
                    int num_obj=Integer.valueOf(line.substring(line.indexOf(":")+1)) ;
                    //System.out.printf("number of objects is %d\n",num_obj);
                    first=true;
                    String []objects=new String[num_obj];
                    for (int j=0;j<num_obj;j++){
                        objects[j]=br.readLine();
                    }
                    obj = objects;
                }
                else if(line.length() > 0) {
                    String predicat_name =line.substring(0, line.indexOf(":"));
                    int num_par = Integer.valueOf(line.substring(line.indexOf(":") + 1 ));
                    //System.out.printf("the name of predicate is %s and #of params is %d\n",predicat_name,num_par);
                }
                if (line.length() == 0)
                {
                    scont++;
                }
                if(scont==2){
                    String linee=br.readLine();
                    int num_per_init=Integer.valueOf(linee.substring(linee.indexOf(":")+1)) ;
                    initialState = new ArrayList<Predicate>();
                    for(int i=0;i<num_per_init;i++){
                        String per_name=br.readLine();
                        int num_par = 0;
                        String[] params = new String[0];
                        for(int k=0;k<predicateList.length;k++){
                            Predicate currentPredicate = predicateList[k];
                            if(per_name.equalsIgnoreCase(currentPredicate.getName())){
                                num_par = predicateList[k].getNum_Par();
                                params = new String[num_par];
                                for (int j=0;j<predicateList[k].getNum_Par();j++) {
                                    params[j] = br.readLine();
                                }
                            }
                        }
                        Predicate tempPredicate = new Predicate(per_name, true, num_par, params);
                        initialState.add(tempPredicate);
                    }
                }
                if(scont==4){
                    ///reading goale
                    String linee=br.readLine();
                    int num_per_goal=Integer.valueOf(linee.substring(linee.indexOf(":")+1)) ;
                    goalState = new ArrayList<Predicate>();
                    for(int n=0;n<num_per_goal;n++){
                        String per_name=br.readLine();
                        int num_par = 0;
                        String[] params = new String[0];
                        for(int k=0;k<predicateList.length;k++){
                            Predicate currentPredicate = predicateList[k];
                            if(per_name.equalsIgnoreCase(currentPredicate.getName())) {
                                num_par = predicateList[k].getNum_Par();
                                params = new String[num_par];
                                for(int j=0;j<predicateList[k].getNum_Par();j++){
                                    params[j] = br.readLine();
                                }
                            }
                        }
                        Predicate tempPredicate = new Predicate(per_name, true, num_par, params);
                        goalState.add(tempPredicate);
                    }
                }
            }
        }
        catch (IOException E){
            System.err.print("file is not ready to read  ");
        }
    }

    public static void getFile(File in) {
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(in));
            int scont=0;
            List<String> input = new ArrayList<String>();
            boolean first =false;
            while ((line = br.readLine()) != null) {
                if (line.length() > 0 && first==false ){
                    int num_per=Integer.valueOf(line.substring(line.indexOf(":")+1)) ;
                   // System.out.printf("number of predicate is %d\n",num_per);
                    first=true;
                    predicateList = new Predicate[num_per];
                    for (int i=0;i<num_per;i++) {
                        String linee = br.readLine();
                       // System.out.println(linee);
                        String predicat_name = linee.substring(0, linee.indexOf(":"));
                        //System.out.println(predicat_name);
                        int num_par = Integer.valueOf(linee.substring(linee.indexOf(":") + 1 ));
                        String tempparam = "";


                            Predicate tempPredicate = new Predicate(predicat_name, null, num_par, null);
                           // System.out.println(tempPredicate.getNum_Par());
                            predicateList[i] = tempPredicate;




                    }
                }

                if (line.length() == 0)
                {
                    scont++;
                }
                if(scont==2){
                    line = br.readLine();
                    int num_op=Integer.valueOf(line.substring(line.indexOf(":")+1)) ;
                    operatorList = new Action[num_op];
                    for (int i=0;i<num_op;i++) {
                        String act_namee =br.readLine();
                        line = br.readLine();
                        int num_par1 = Integer.valueOf(line.substring(line.indexOf(":") + 1 ));
                        String []paramlist=new String[num_par1];
                        for(int k=0;k<num_par1;k++){
                            paramlist[k]=br.readLine();
                           // System.out.println(paramlist[k]+"\n");

                        }
                        line = br.readLine();
                        int num_pre = Integer.valueOf(line.substring(line.indexOf(":") + 1 ));
                        List<Predicate> preconditions = new ArrayList<Predicate>();
                        for(int k=0;k<num_pre;k++){
                            int temp_num_par = 0;
                            String[] temp_params = new String[0];
                            String temp_name = br.readLine();
                            for (int m=0;m<predicateList.length;m++) {
                                //System.out.println(predicateList[m].getName());
                                //System.out.println(predicateList[m].getNum_Par());
                                if (predicateList[m].getName().equalsIgnoreCase(temp_name)) {
                                    temp_num_par = predicateList[m].getNum_Par();
                                    temp_params = new String[temp_num_par];
                                    for (int j=0;j<predicateList[m].getNum_Par();j++) {
                                        temp_params[j] = br.readLine();
                                    }
                                }
                            }
                            Predicate tempPredicate = new Predicate(temp_name, true, temp_num_par, temp_params);
                            preconditions.add(tempPredicate);
                        }
                        line = br.readLine();
                        int num_add = Integer.valueOf(line.substring(line.indexOf(":") + 1 ));
                        List<Predicate> addEffects = new ArrayList<Predicate>();
                        for(int k=0;k<num_add;k++){
                            int temp_num_par = 0;
                            String[] temp_params = new String[0];
                            String temp_name = br.readLine();
                            for (int n=0;n<predicateList.length;n++) {
                                if (predicateList[n].getName().equalsIgnoreCase(temp_name)) {
                                    temp_num_par = predicateList[n].getNum_Par();
                                    temp_params = new String[temp_num_par];
                                    for (int j=0;j<predicateList[n].getNum_Par();j++) {
                                        temp_params[j] = br.readLine();
                                    }
                                }
                            }
                            Predicate tempPredicate = new Predicate(temp_name, true, temp_num_par, temp_params);
                            addEffects.add(tempPredicate);
                        }
                        line = br.readLine();
                        int num_del = Integer.valueOf(line.substring(line.indexOf(":") + 1 ));
                        List<Predicate> delEffects = new ArrayList<Predicate>();
                        
                        for(int k=0;k<num_del;k++){
                            int temp_num_par = 0;
                            String[] temp_params = new String[0];
                            String temp_name = br.readLine();
                            
                            for (int p=0;p<predicateList.length;p++) {
                                if (predicateList[p].getName().equalsIgnoreCase(temp_name)) {
                                    temp_num_par = predicateList[p].getNum_Par();
                                    temp_params = new String[predicateList[p].getNum_Par()];
                                    
                                    for (int j=0;j<predicateList[p].getNum_Par();j++) {
                                        temp_params[j] = br.readLine();
                                    }
                                }
                            }
                            Predicate tempPredicate = new Predicate(temp_name, false, temp_num_par, temp_params);
                            delEffects.add(tempPredicate);
                        }
                        br.readLine();
                        Action tempOperator = new Action(act_namee, num_par1, paramlist, preconditions, delEffects, addEffects);
                        operatorList[i] = tempOperator;
                    }

                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("Object not found Exception");

        } catch (IOException o) {
        }

    }


    public static void makefinalpredicates(){
        String objectstring = "";
        for (int i=0;i<obj.length;i++){
            objectstring = objectstring + obj[i];
        }
        for ( Predicate predicate : predicateList ){
            perm = "";


            permute(objectstring,"",predicate.getNum_Par());
            perm = perm.substring(1);
            String[] allparams = perm.split(",");
            for ( int i=0;i< allparams.length ;i++){
                Predicate _finalpredicate1 = new Predicate(predicate.getName(),true,predicate.getNum_Par(),allparams[i].split(""));
                Predicate _finalpredicate2 = new Predicate(predicate.getName(),false,predicate.getNum_Par(),allparams[i].split(""));
                finalpredicates.add(_finalpredicate1);
                finalpredicates.add(_finalpredicate2);
            }

        }

    }

    public static void makefinaloperation(){
        String objectstring = "";
        for (int i=0;i<obj.length;i++){
            objectstring = objectstring + obj[i];
        }
        for ( Action action : operatorList ){
            perm = "";


            permute(objectstring,"",action.getNumpar());
            perm = perm.substring(1);
            String[] allparams = perm.split(",");
            for ( int i=0;i< allparams.length ;i++){
                List<Predicate> tempPrecondition = new ArrayList<Predicate>();
                List<Predicate> tempAddEffects = new ArrayList<Predicate>();
                List<Predicate> tempDelEffects = new ArrayList<Predicate>();
                if (action.getPreCondition() != null) {
                    for (Predicate precond: action.getPreCondition()) {
                        Predicate predicate = new Predicate(precond.getName(),precond.getTruth(),precond.getNum_Par(),precond.getPar());
                        tempPrecondition.add(predicate);
                    }
                }

                if (action.getAddEffects() != null) {
                    for (Predicate addEffect: action.getAddEffects()) {
                        Predicate predicate = new Predicate(addEffect.getName(),addEffect.getTruth(),addEffect.getNum_Par(),addEffect.getPar());
                        tempAddEffects.add(predicate);
                    }
                }

                if (action.getDeleteEffects()!= null) {
                    for (Predicate delEffect: action.getDeleteEffects()) {
                        Predicate predicate = new Predicate(delEffect.getName(),delEffect.getTruth(),delEffect.getNum_Par(),delEffect.getPar());
                        tempDelEffects.add(predicate);
                    }
                }

                Action _finalaction = new Action(action.getName(),action.getNumpar(),allparams[i].split(""),tempPrecondition,tempDelEffects,tempAddEffects);
                _finalaction.setparamsforpredicates();
                List<String> checkparams = new ArrayList<String>();
                boolean checkparamsflag = false;
                for ( String s : _finalaction.getParametrs() ){
                    for ( String ss : checkparams){
                        if ( s.equals(ss)){
                            checkparamsflag = true;
                            break;
                        }
                    }
                    checkparams.add(s);
                }
                if ( ! checkparamsflag) {
                    finalaction.add(_finalaction);
                }
            }

        }

    }

    static void permute(String in, String prefix, int k) {
        if (k==0){
           // System.out.println(prefix);
            perm=perm +","+prefix;
            return;
        }
        for (int j=0;j<in.length();++j){
            String Nprefix=prefix+in.charAt(j);
            permute(in,Nprefix,k-1);



        }



    }

    public static List<Predicate> getInitialState() {
        return initialState;
    }

    public static List<Predicate> getGoalState() {
        return goalState;
    }

    public static List<Action> getFinalaction() {
        return finalaction;
    }

    public static void GenerateNoOP(){
        for ( Predicate predicate : finalpredicates){

            Predicate temppredicate = new Predicate(predicate.getName(),predicate.getTruth(),predicate.getNum_Par(),predicate.getPar());
            List<Predicate> precond = new ArrayList<>();
            precond.add(temppredicate);


            Predicate temppredicate2 = new Predicate(predicate.getName(),predicate.getTruth(),predicate.getNum_Par(),predicate.getPar());
            List<Predicate> addeffect = new ArrayList<>();
            addeffect.add(temppredicate2);

            List<Predicate> emptylist = new ArrayList<>();
            String str[]= new String [0];

            if ( temppredicate2.getTruth() ) {
                Action noop = new Action("NO-OP", 0, str, precond, emptylist, addeffect);
                finalaction.add(noop);
            }else {
                Action noop = new Action("NO-OP", 0, str, precond,addeffect,emptylist);
                finalaction.add(noop);
            }

        }
    }
}

