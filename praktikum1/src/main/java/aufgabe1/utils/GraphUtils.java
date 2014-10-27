package aufgabe1.utils;

import aufgabe1.Vertex;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;

/**
 * Created by schlegel11 on 24.10.14.
 */
public final class GraphUtils {

    private GraphUtils() {
    }

    public static boolean isDirectedGraph(Graph<? extends Vertex, ? extends DefaultEdge> graph) {
        return graph instanceof DirectedGraph;
    }

    public static void removeMarkers(Graph<Vertex, DefaultWeightedEdge> graph) {
        for (Vertex v : graph.vertexSet()) {
            v.removeMarker();
        }
        for (DefaultWeightedEdge e : graph.edgeSet()) {
            graph.getEdgeSource(e).removeMarker();
            graph.getEdgeTarget(e).removeMarker();
        }
    }
}
