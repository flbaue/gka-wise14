package aufgabe1.algorithms;

import aufgabe1.Vertex;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.Iterator;

/**
 * Created by flbaue on 24.10.14.
 */
public class BreadthFirstSearch implements Iterable<Vertex> {

    private final Vertex startVertex;
    private final Graph<Vertex, DefaultWeightedEdge> graph;

    public BreadthFirstSearch(Graph<Vertex, DefaultWeightedEdge> graph, Vertex startVertex) {
        this.startVertex = startVertex;
        this.graph = graph;
    }

    @Override
    public Iterator<Vertex> iterator() {
        return new BreadthFirstSearchIterator(graph, startVertex);
    }
}
