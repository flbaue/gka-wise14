package aufgabe1.algorithms;

import aufgabe1.Marker;
import aufgabe1.Vertex;
import aufgabe1.utils.GraphUtils;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by flbaue on 26.10.14.
 */
public class BfsIterator implements Iterator<Vertex> {

    private Deque<Vertex> queue = new LinkedList<>();
    private Deque<Vertex> neighbors = new LinkedList<>();
    private Vertex currentVertex;
    private Graph<Vertex, DefaultWeightedEdge> graph;

    public BfsIterator(Graph<Vertex, DefaultWeightedEdge> graph, Vertex startVertex) {
        GraphUtils.removeMarkers(graph);
        startVertex.setMarker(new Marker(null, 0));
        queue.add(startVertex);
        this.graph = graph;
    }

    private void findNeigbors(final Vertex vertex) {
        for (DefaultWeightedEdge e : graph.edgesOf(vertex)) {
            Vertex v = Graphs.getOppositeVertex(graph, e, vertex);
            v.setMarker(new Marker(null, 0));
            neighbors.add(v);
            queue.add(v);
        }
    }

    @Override
    public boolean hasNext() {
        if (currentVertex == null) {
            currentVertex = queue.poll();
            findNeigbors(currentVertex);
            return true;
        }

        for (Vertex v : neighbors) {
            if (!v.hasMarker() || v.getDistance() > currentVertex.getDistance() + 1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Vertex next() {
        if (!currentVertex.isVisited()) {
            currentVertex.visit();
            return currentVertex;
        }

        if (neighbors.size() > 0) {
            Vertex n = neighbors.poll();
            n.setMarker(new Marker(currentVertex, currentVertex.getDistance() + 1));
            if (neighbors.size() == 0) {
                currentVertex = null;
            }
            return n;
        }

        return null;
    }
}
