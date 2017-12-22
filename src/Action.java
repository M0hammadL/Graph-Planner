import java.util.ArrayList;
import java.util.List;


public class Action {
    private String Name;
    private List<Predicate> PreCondition = new ArrayList<Predicate>();
    private List<Predicate> DeleteEffects = new ArrayList<Predicate>();
    private List<Predicate> AddEffects = new ArrayList<Predicate>();
    private List<Predicate> Causal = new ArrayList<Predicate>();
    private int numpar = 0;
    private String[] parametrs;

    public Action(String _Name,int num_par,String[] pars,List _PreCondition,List _DeleteEffects,List _AddEffects){
        PreCondition = _PreCondition;
        DeleteEffects = _DeleteEffects;
        AddEffects = _AddEffects;
        Name = _Name;
        numpar = num_par;
        parametrs = pars;
    }

    public List<Predicate> getPreCondition() {
        return PreCondition;
    }

    public void setPreCondition(List<Predicate> preCondition) {
        PreCondition = preCondition;
    }

    public List<Predicate> getDeleteEffects() {
        return DeleteEffects;
    }

    public void setDeleteEffects(List<Predicate> deleteEffects) {
        DeleteEffects = deleteEffects;
        for ( Predicate predicate : DeleteEffects){
            List<Action> temp = new ArrayList<Action>();
            temp.add(this);
            predicate.setCausal(temp);
        }
    }

    public List<Predicate> getAddEffects() {
        return AddEffects;

    }

    public void setAddEffects(List<Predicate> addEffects) {
        AddEffects = addEffects;
        for ( Predicate predicate : AddEffects){
            List<Action> temp = new ArrayList<Action>();
            temp.add(this);
            predicate.setCausal(temp);
        }
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getNumpar() {
        return numpar;
    }

    public void setparamsforpredicates(){

        for ( Predicate predicate : PreCondition ){
            String [] newparams = new String[predicate.getNum_Par()];
            for ( int i=0;i<predicate.getNum_Par();i++){
                String[] params = predicate.getPar();
                if ( params == null ){

                }
                for ( int j=0;j<params.length;j++ ){
                    String whichparamstring = params[j].replaceAll("\\D+","");
                    int whichpar = 1;
                    if ( params[j].equals("sunderob")){
                        whichpar = 2;
                    }
                    if ( ! whichparamstring.equals("")){
                        whichpar = Integer.valueOf(whichparamstring);
                    }
                    //System.out.println(whichpar + "   " + getName() );
                    if ( getName().equals("NO-OP0")){
                        String ramin = "khar";
                    }
                    newparams[j] = parametrs[whichpar - 1];
                }
            }
            predicate.setPar(newparams);
        }
        for ( Predicate predicate : AddEffects ){
            String [] newparams = new String[predicate.getNum_Par()];
            for ( int i=0;i<predicate.getNum_Par();i++){
                String[] params = predicate.getPar();
                for ( int j=0;j<params.length;j++ ){
                    String whichparamstring = params[j].replaceAll("\\D+","");

                    int whichpar = 1;
                    if ( params[j].equals("sunderob")){
                        whichpar = 2;
                    }
                    if ( ! whichparamstring.equals("")){
                        whichpar = Integer.valueOf(whichparamstring);
                    }

                    newparams[j] = parametrs[whichpar - 1];
                }
            }
            predicate.setPar(newparams);
        }
        for ( Predicate predicate : DeleteEffects ){
            String [] newparams = new String[predicate.getNum_Par()];
            for ( int i=0;i<predicate.getNum_Par();i++){
                String[] params = predicate.getPar();

                for ( int j=0;j<params.length;j++ ){
                    String whichparamstring = params[j].replaceAll("\\D+","");
                    int whichpar = 1;
                    if ( params[j].equals("sunderob")){
                        whichpar = 2;
                    }
                    if ( ! whichparamstring.equals("")){
                        whichpar = Integer.valueOf(whichparamstring);
                    }

                    newparams[j] = parametrs[whichpar - 1];
                }
            }
            predicate.setPar(newparams);
        }
    }

    public String[] getParametrs() {
        return parametrs;
    }

    public String getParamString(){
        String temp = "";
        for ( String s : parametrs){
            temp = temp + s;
        }
       /* if (! temp.equals("")){
            temp = temp.substring(1);
        }
        */
        return temp;
    }
}
