package aufgabe1.utils;

import org.jgrapht.graph.DefaultWeightedEdge;

/**
 * Created by schlegel11 on 07.12.14.
 */
public class NetworkEdge extends DefaultWeightedEdge {
    private int capacity;
    private int flow;

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getFlow() {
        return flow;
    }

    public void setFlow(int flow) {
        this.flow = flow;
    }
}
