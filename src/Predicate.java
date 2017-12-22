import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohammad on 8/4/2016.
 */
public class Predicate {
    private String Name;
    private Boolean Truth;
    private int num_par;
    private String []par;
    private List<Action> Causal = new ArrayList<Action>();
    public Predicate(String _name,Boolean _Truth,int _num_par,String[]pars){
        ////num_par baiad hazf shavad !!!
        Name = _name;
        Truth = _Truth;
        par=pars;
        num_par = _num_par;


    }

    public String getName() {
        return Name;
    }

    public String[] getPar() {
        return par;
    }

    public void setName(String name) {
        Name = name;
    }

    public Boolean getTruth() {
        return Truth;
    }

    public void setTruth(Boolean truth) {
        Truth = truth;
    }

    public List<Action> getCausal() {
        return Causal;
    }

    public void AddCausal(Action act){
        Causal.add(act);
    }

    public int getNum_Par() {
        return num_par;
    }

    public void setNum_Par(int numPar) {
        num_par = numPar;
    }

    public void setCausal(List<Action> causal) {
        Causal = causal;
    }

    public void setPar(String[] par) {
        this.par = par;
    }

    public String getParamString(){
        String params = "";
        for (String s : par){
            params = params + s;
        }
        return params;
    }


}
