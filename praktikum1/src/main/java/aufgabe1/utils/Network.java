package aufgabe1.utils;

import org.jgrapht.graph.DirectedWeightedPseudograph;

/**
 * Created by schlegel11 on 07.12.14.
 */
public class Network<V, E extends NetworkEdge> extends DirectedWeightedPseudograph<V, E> {

    public Network() {
        this((Class<? extends E>) NetworkEdge.class);
    }

    public Network(Class<? extends E> edgeClass) {
        super(edgeClass);
    }

    public int getEdgeCapacity(E edge){
        return edge.getCapacity();
    }

    public int getEdgeFlow(E edge){
        return edge.getFlow();
    }
}
