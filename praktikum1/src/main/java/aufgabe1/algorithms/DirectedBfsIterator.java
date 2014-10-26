package aufgabe1.algorithms;

import aufgabe1.Marker;
import aufgabe1.Vertex;
import aufgabe1.utils.GraphUtils;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by flbaue on 26.10.14.
 */
public class DirectedBfsIterator implements Iterator<Vertex> {

    private Deque<Vertex> queue = new LinkedList<>();
    private Deque<Vertex> neighbors = new LinkedList<>();
    private Vertex currentVertex;
    private DirectedGraph<Vertex, DefaultWeightedEdge> graph;

    public DirectedBfsIterator(DirectedGraph<Vertex, DefaultWeightedEdge> graph, Vertex startVertex) {
        GraphUtils.removeMarkers(graph);
        startVertex.setMarker(new Marker(null, 0));
        queue.add(startVertex);
        this.graph = graph;
    }

    private void findNeigbors(final Vertex vertex) {
        for (DefaultWeightedEdge e : graph.outgoingEdgesOf(vertex)) {
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
