import java.util.ArrayList;
import java.util.List;


public class Graph {

    private List<Layer> GraphLayers = new ArrayList<Layer>();
    public Graph(Layer initialLayer){
        GraphLayers.add(initialLayer);

    }

    public List<Layer> getGraphLayers() {
        return GraphLayers;
    }
    public  void addlayer(Layer newLayer){
        GraphLayers.add(newLayer);

    }

    public int getNumlayers() {
       return GraphLayers.size();
    }

    public Layer getLastLayer(){
        return GraphLayers.get(GraphLayers.size() - 1);
    }

    public void nextstep(){
        ActionLayer newactlayer = (ActionLayer) getLastLayer().MakeNextLayer();
        GraphLayers.add(newactlayer);
        newactlayer.checkmutex();
        PredicateLayer newpredlayer = (PredicateLayer) getLastLayer().MakeNextLayer();
        GraphLayers.add(newpredlayer);
        newpredlayer.checkmutex();
    }

    public Layer getLayer(int number){
        return GraphLayers.get(number);
    }
}
