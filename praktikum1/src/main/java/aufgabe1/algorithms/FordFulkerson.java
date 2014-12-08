package aufgabe1.algorithms;

import aufgabe1.Marker;
import aufgabe1.Vertex;
import aufgabe1.utils.GraphUtils;
import aufgabe1.utils.Network;
import aufgabe1.utils.NetworkEdge;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedPseudograph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by schlegel11 on 07.12.14.
 */
public class FordFulkerson {
    private final Network<Vertex, NetworkEdge> network;
    private DirectedWeightedPseudograph<Vertex, DefaultWeightedEdge> residualGraph;
    private double[][] graphMatrix;
    private List<Vertex> vertices;
    private Vertex source;
    private Vertex sink;

    public FordFulkerson(Graph<Vertex, DefaultWeightedEdge> graph) {
        network = GraphUtils.convertToNetwork(graph);
    }

    public FordFulkerson(Network<Vertex, NetworkEdge> network) {
        this.network = network;
    }

    public void run(Vertex source, Vertex sink) {
        residualGraph = (DirectedWeightedPseudograph<Vertex, DefaultWeightedEdge>) GraphUtils.convertToGraph(network);
        source.setMarker(new Marker(null, Integer.MAX_VALUE));
        BreadthFirstSearch bfs = new BreadthFirstSearch(residualGraph, source);

        for (Vertex vertex : bfs) {
                if (vertex.equals(sink)) {
                    double minFlow = findMinimumCapacity(vertex);
                    updateNetworkFlow(vertex, minFlow);
                    updateResidualFlow(vertex, minFlow);
                }
        }
    }

    private double findMinimumCapacity(Vertex vertex) {
        double minCapacity = Double.POSITIVE_INFINITY;
        for (Vertex elem : vertex.getThisAndPredecessors()) {
            Vertex predecessor = elem.getPredecessor();
            if (!predecessor.equals(elem)) {
                minCapacity = Math.min(minCapacity, residualGraph.getEdgeWeight(residualGraph.getEdge(predecessor, elem)));
            }
        }
        return minCapacity;
    }

    private void updateNetworkFlow(Vertex vertex, double flowValue){
        for(Vertex elem : vertex.getThisAndPredecessors()){
            Vertex predecessor = elem.getPredecessor();
            if (!predecessor.equals(elem)) {
                NetworkEdge edge = network.getEdge(predecessor, elem);
                edge.setFlow(edge.getFlow() + flowValue);
            }
        }
    }

    private void updateResidualFlow(Vertex vertex, double flowValue) {
        for (Vertex elem : vertex.getThisAndPredecessors()) {
            Vertex predecessor = elem.getPredecessor();
            if (!predecessor.equals(elem)) {
                DefaultWeightedEdge backEdge = residualGraph.getEdge(elem, predecessor);

                if(Objects.nonNull(backEdge)){
                    double edgeWeight = residualGraph.getEdgeWeight(residualGraph.getEdge(elem, predecessor));
                    residualGraph.setEdgeWeight(residualGraph.getEdge(predecessor, elem), edgeWeight + flowValue);

                }else{
                    double edgeWeight = residualGraph.getEdgeWeight(residualGraph.getEdge(predecessor, elem));
                    if(edgeWeight != flowValue){
                        residualGraph.setEdgeWeight(residualGraph.getEdge(predecessor, elem), edgeWeight - flowValue);
                        residualGraph.setEdgeWeight(residualGraph.addEdge(elem, predecessor), flowValue);
                    }
                }
            }
        }
    }
}
