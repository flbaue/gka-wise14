package aufgabe1.gui;

import aufgabe1.Vertex;
import aufgabe1.utils.GraphUtils;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.ListenableGraph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.ListenableDirectedGraph;
import org.jgrapht.graph.ListenableUndirectedWeightedGraph;

/**
 * Created by schlegel11 on 25.10.14.
 */
final class ListenableGraphFactory {

    private ListenableGraphFactory() {
    }

    public static ListenableGraph<? extends Vertex, ? extends DefaultEdge> createFromGraph(Graph<? extends Vertex, ? extends DefaultEdge> graph) {
        return GraphUtils.isDirectedGraph(graph) ? new ListenableDirectedGraph((DirectedGraph) graph) : new ListenableUndirectedWeightedGraph((WeightedGraph) graph);
    }
}
