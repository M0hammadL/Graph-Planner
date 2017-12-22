/**
 * Created by Mohammad on 8/6/2016.
 */
public class MutexPredicate {
    
        private Predicate Predicate1;
        private Predicate Predicate2;


        public MutexPredicate(Predicate _pre1, Predicate _pre2){
            Predicate1 = _pre1;
            Predicate2 = _pre2;
        }
        public Predicate getPredicate1() {
            return Predicate1;
        }

        public void setPredicate1(Predicate Predicate1) {
            this.Predicate1 = Predicate1;
        }

        public Predicate getPredicate2() {
            return Predicate2;
        }

        public void setPredicate2(Predicate Predicate2) {
            this.Predicate2 = Predicate2;
        }

        public boolean Equal(MutexPredicate mutex){

            if ( mutex.getPredicate1().getName().equalsIgnoreCase(Predicate1.getName()) && mutex.getPredicate1().getTruth() == Predicate1.getTruth() && mutex.getPredicate1().getParamString().equals(Predicate1.getParamString()) && mutex.getPredicate2().getName().equalsIgnoreCase(Predicate2.getName()) && mutex.getPredicate2().getTruth() == Predicate2.getTruth() && mutex.getPredicate2().getParamString().equals(Predicate2.getParamString())){
                return true;
            }else if (mutex.getPredicate1().getName().equalsIgnoreCase(Predicate2.getName()) && mutex.getPredicate1().getTruth() == Predicate2.getTruth() && mutex.getPredicate1().getParamString().equals(Predicate2.getParamString()) && mutex.getPredicate2().getName().equalsIgnoreCase(Predicate1.getName()) && mutex.getPredicate2().getTruth() == Predicate1.getTruth() && mutex.getPredicate2().getParamString().equals(Predicate1.getParamString())){
                return true;
            }
            return false;
        }
}
