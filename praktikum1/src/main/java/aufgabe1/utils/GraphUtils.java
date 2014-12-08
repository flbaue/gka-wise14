package aufgabe1.utils;

import aufgabe1.Vertex;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedPseudograph;

import java.util.Objects;

/**
 * Created by schlegel11 on 24.10.14.
 */
public final class GraphUtils {

    private GraphUtils() {
    }

    public static boolean isDirectedGraph(Graph<? extends Vertex, ? extends DefaultEdge> graph) {
        return graph instanceof DirectedGraph;
    }

    public static <V extends Vertex, E extends DefaultEdge> boolean isOutgoingEdge(Graph<V, E> graph, V source, V target) {
        return Objects.nonNull(graph.getEdge(source, target)) && graph.getEdgeTarget(graph.getEdge(source, target)).equals(target);
    }

    public static void removeMarkers(Graph<Vertex, DefaultWeightedEdge> graph) {
        for (Vertex v : graph.vertexSet()) {
            v.removeMarker();
        }
    }

    public static Network<Vertex, NetworkEdge> convertToNetwork(Graph<Vertex, DefaultWeightedEdge> graph) {
        Network<Vertex, NetworkEdge> network = new Network();
        graph.vertexSet().forEach(vertex -> network.addVertex(vertex));
        graph.edgeSet().forEach(edge -> network.addEdge(graph.getEdgeSource(edge), graph.getEdgeTarget(edge)).setCapacity(graph.getEdgeWeight(edge)));
        return network;
    }

    public static Graph<Vertex, DefaultWeightedEdge> convertToGraph(Network<Vertex, NetworkEdge> network){
        DirectedWeightedPseudograph<Vertex, DefaultWeightedEdge> graph = new DirectedWeightedPseudograph(DefaultWeightedEdge.class);
        network.vertexSet().forEach(vertex -> graph.addVertex(vertex));
        network.edgeSet().forEach(edge -> graph.setEdgeWeight(graph.addEdge(network.getEdgeSource(edge), network.getEdgeTarget(edge)), edge.getCapacity()));
        return graph;
    }
}
