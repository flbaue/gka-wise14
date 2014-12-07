package aufgabe1.utils;

import org.jgrapht.graph.DefaultEdge;

/**
 * Created by schlegel11 on 07.12.14.
 */
public class NetworkEdge extends DefaultEdge {
    private double capacity;
    private double flow;

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public double getFlow() {
        return flow;
    }

    public void setFlow(double flow) {
        this.flow = flow;
    }
}
