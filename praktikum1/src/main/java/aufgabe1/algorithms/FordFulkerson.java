package aufgabe1.algorithms;

import aufgabe1.Marker;
import aufgabe1.Vertex;
import aufgabe1.utils.GraphUtils;
import aufgabe1.utils.Network;
import aufgabe1.utils.NetworkEdge;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedPseudograph;

import java.util.Objects;

/**
 * Created by schlegel11 on 07.12.14.
 */
public class FordFulkerson {
    private final Network<Vertex, NetworkEdge> network;
    private DirectedWeightedPseudograph<Vertex, DefaultWeightedEdge> residualGraph;
    private int maxFlow;
    private long startTime;
    private long endTime;

    public FordFulkerson(Graph<Vertex, DefaultWeightedEdge> graph) {
        network = GraphUtils.convertToNetwork(graph);
    }

    public FordFulkerson(Network<Vertex, NetworkEdge> network) {
        this.network = network;
    }

    public void run(Vertex source, Vertex sink) {
        startTime = System.currentTimeMillis();

        residualGraph = (DirectedWeightedPseudograph<Vertex, DefaultWeightedEdge>) GraphUtils.convertToGraph(network);
        source.setMarker(new Marker(null, Integer.MAX_VALUE));
        BreadthFirstSearch bfs;

        boolean foundPath;
        do {
            foundPath = false;
            bfs = new BreadthFirstSearch(residualGraph, source);
            for (Vertex vertex : bfs) {
                if (vertex.equals(sink)) {
                    int minFlow = findMinimumCapacity(vertex);
                    updateNetworkFlow(vertex, minFlow);
                    updateResidualFlow(vertex, minFlow);
                    foundPath = true;
                    maxFlow += minFlow;
                }
            }
        } while (foundPath);

        endTime = System.currentTimeMillis();
    }

    public int getMaxFlow() {
        return maxFlow;
    }

    public long getRuntimeMillis() {
        return endTime - startTime;
    }

    private int findMinimumCapacity(Vertex vertex) {
        int minCapacity = Integer.MAX_VALUE;
        for (Vertex elem : vertex.getThisAndPredecessors()) {
            Vertex predecessor = elem.getPredecessor();
            if (!predecessor.equals(elem)) {
                minCapacity =  Math.min(minCapacity, (int) residualGraph.getEdgeWeight(residualGraph.getEdge(predecessor, elem)));
            }
        }
        return minCapacity;
    }

    private void updateNetworkFlow(Vertex vertex, int flowValue) {
        for (Vertex elem : vertex.getThisAndPredecessors()) {
            Vertex predecessor = elem.getPredecessor();
            if (!predecessor.equals(elem)) {
                NetworkEdge edge = network.getEdge(predecessor, elem);
                edge.setFlow(edge.getFlow() + flowValue);
            }
        }
    }

    private void updateResidualFlow(Vertex vertex, int flowValue) {
        for (Vertex elem : vertex.getThisAndPredecessors()) {
            Vertex predecessor = elem.getPredecessor();
            if (!predecessor.equals(elem)) {
                DefaultWeightedEdge backEdge = residualGraph.getEdge(elem, predecessor);
                int edgeWeight = (int) residualGraph.getEdgeWeight(residualGraph.getEdge(predecessor, elem));

                if ((edgeWeight - flowValue) <= 0) {
                    residualGraph.removeEdge(residualGraph.getEdge(predecessor, elem));
                } else {
                    residualGraph.setEdgeWeight(residualGraph.getEdge(predecessor, elem), edgeWeight - flowValue);
                }

                if (Objects.nonNull(backEdge)) {
                    residualGraph.setEdgeWeight(backEdge, edgeWeight + flowValue);
                } else {
                    residualGraph.setEdgeWeight(residualGraph.addEdge(elem, predecessor), flowValue);
                }
            }
        }
    }
}
