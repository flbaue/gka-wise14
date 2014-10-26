package aufgabe1.algorithms;

import aufgabe1.Vertex;
import aufgabe1.utils.GraphUtils;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.Iterator;

/**
 * Created by flbaue on 24.10.14.
 */
public class BreadthFirstSearch implements Iterable<Vertex> {

    private final Vertex startVertex;
    private final Graph<Vertex, DefaultWeightedEdge> graph;
    private final boolean isDrirected;

    public BreadthFirstSearch(Graph<Vertex, DefaultWeightedEdge> graph, Vertex startVertex) {
        this.startVertex = startVertex;
        this.graph = graph;
        this.isDrirected = GraphUtils.isDirectedGraph(graph);
    }

    @Override
    public Iterator<Vertex> iterator() {
        if (isDrirected) {
            return new DirectedBfsIterator((DirectedGraph<Vertex, DefaultWeightedEdge>) graph, startVertex);
        } else {
            return new BfsIterator(graph, startVertex);
        }
    }
}
