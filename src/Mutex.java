import com.sun.org.apache.xpath.internal.operations.Bool;


public class Mutex {
    private Action action1;
    private Action action2;


    public Mutex(Action act1, Action act2){
        action1 = act1;
        action2 = act2;
    }
    public Action getAction1() {
        return action1;
    }

    public void setAction1(Action action1) {
        this.action1 = action1;
    }

    public Action getAction2() {
        return action2;
    }

    public void setAction2(Action action2) {
        this.action2 = action2;
    }

    public Boolean Equal(Mutex mutex){
       if (!action1.getName().equalsIgnoreCase("NO-OP") && !action2.getName().equalsIgnoreCase("NO-OP")) {
           if ( ((action1.getName().equalsIgnoreCase(mutex.getAction1().getName()) && action1.getParamString().equals(mutex.getAction1().getParamString()))) && action2.getName().equals(mutex.getAction2().getName()) && action2.getParamString().equals(mutex.getAction2().getParamString())){
              // System.out.println(mutex.getAction2().getParamString()  );
               return true;
           }else if(((action1.getName().equalsIgnoreCase(mutex.getAction2().getName()) && action1.getParamString().equals(mutex.getAction2().getParamString()))) && action2.getName().equals(mutex.getAction1().getName()) && action2.getParamString().equals(mutex.getAction1().getParamString())){
               // System.out.println(action1.getName() + "    " +  action2.getName());
               return true;
           }
       }else if ( action1.getName().equalsIgnoreCase("NO-OP") && mutex.getAction1().getName().equalsIgnoreCase("NO-OP") && action2.getName().equalsIgnoreCase("NO-OP") && mutex.getAction2().getName().equalsIgnoreCase("NO-OP")){
           if ( action1.getPreCondition().get(0).getName().equals(mutex.getAction1().getPreCondition().get(0).getName()) && action1.getPreCondition().get(0).getTruth() == mutex.getAction1().getPreCondition().get(0).getTruth() && action1.getParamString().equals(mutex.getAction1().getPreCondition().get(0).getParamString())){
               if ( action2.getPreCondition().get(0).getName().equals(mutex.getAction2().getPreCondition().get(0).getName()) && action2.getPreCondition().get(0).getTruth() == mutex.getAction2().getPreCondition().get(0).getTruth() && action2.getParamString().equals(mutex.getAction2().getPreCondition().get(0).getParamString())){
                   return true;
               }
           }else if ( action1.getPreCondition().get(0).getName().equals(mutex.getAction2().getPreCondition().get(0).getName()) && action1.getPreCondition().get(0).getTruth() == mutex.getAction2().getPreCondition().get(0).getTruth() && action1.getParamString().equals(mutex.getAction2().getPreCondition().get(0).getParamString())){
               if ( action2.getPreCondition().get(0).getName().equals(mutex.getAction1().getPreCondition().get(0).getName()) && action2.getPreCondition().get(0).getTruth() == mutex.getAction1().getPreCondition().get(0).getTruth() && action2.getParamString().equals(mutex.getAction1().getPreCondition().get(0).getParamString())){
                   return true;
               }

           }

       }else if ( action1.getName().equalsIgnoreCase("NO-OP") && mutex.getAction1().getName().equalsIgnoreCase("NO-OP")){

           if ((action2.getName().equalsIgnoreCase(mutex.getAction2().getName())) && (action2.getParamString().equalsIgnoreCase(mutex.getAction2().getParamString()))) {
               if ((action1.getPreCondition().get(0).getName().equals(mutex.getAction1().getPreCondition().get(0).getName())) && (action1.getPreCondition().get(0).getTruth() == mutex.getAction1().getPreCondition().get(0).getTruth()) && (action1.getPreCondition().get(0).getParamString().equals(mutex.getAction1().getPreCondition().get(0).getParamString()))) {
                   return true;
               }
           }

       }else if ( action1.getName().equalsIgnoreCase("NO-OP") && mutex.getAction2().getName().equalsIgnoreCase("NO-OP")){

           if ((action2.getName().equalsIgnoreCase(mutex.getAction1().getName())) && (action2.getParamString().equalsIgnoreCase(mutex.getAction1().getParamString()))) {
               if ((action1.getPreCondition().get(0).getName().equals(mutex.getAction2().getPreCondition().get(0).getName())) && (action1.getPreCondition().get(0).getTruth() == mutex.getAction2().getPreCondition().get(0).getTruth()) && (action1.getPreCondition().get(0).getParamString().equals(mutex.getAction2().getPreCondition().get(0).getParamString()))) {
                   return true;
               }
           }

       }else if ( action2.getName().equalsIgnoreCase("NO-OP") && mutex.getAction1().getName().equalsIgnoreCase("NO-OP")){

           if ((action1.getName().equalsIgnoreCase(mutex.getAction2().getName())) && (action1.getParamString().equalsIgnoreCase(mutex.getAction2().getParamString()))) {
               if ((action2.getPreCondition().get(0).getName().equals(mutex.getAction1().getPreCondition().get(0).getName())) && (action2.getPreCondition().get(0).getTruth() == mutex.getAction1().getPreCondition().get(0).getTruth()) && (action2.getPreCondition().get(0).getParamString().equals(mutex.getAction1().getPreCondition().get(0).getParamString()))) {
                   return true;
               }
           }

       }else if ( action2.getName().equalsIgnoreCase("NO-OP") && mutex.getAction2().getName().equalsIgnoreCase("NO-OP")){
           if ((action1.getName().equalsIgnoreCase(mutex.getAction1().getName())) && (action1.getParamString().equalsIgnoreCase(mutex.getAction1().getParamString()))) {
               if ((action2.getPreCondition().get(0).getName().equals(mutex.getAction2().getPreCondition().get(0).getName())) && (action2.getPreCondition().get(0).getTruth() == mutex.getAction2().getPreCondition().get(0).getTruth()) && (action2.getPreCondition().get(0).getParamString().equals(mutex.getAction2().getPreCondition().get(0).getParamString()))) {
                   return true;
               }
           }

       }

        return false;
    }


}
