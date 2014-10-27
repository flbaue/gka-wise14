package aufgabe1.algorithms;

import aufgabe1.Marker;
import aufgabe1.Vertex;
import aufgabe1.utils.GraphUtils;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by flbaue on 26.10.14.
 */
public class BreadthFirstSearchIterator implements Iterator<Vertex> {

    private Deque<Vertex> queue = new LinkedList<>();
    private Deque<Vertex> neighbors = new LinkedList<>();
    private Vertex currentVertex;
    private Graph<Vertex, DefaultWeightedEdge> graph;

    public BreadthFirstSearchIterator(Graph<Vertex, DefaultWeightedEdge> graph, Vertex startVertex) {
        GraphUtils.removeMarkers(graph);
        startVertex.setMarker(new Marker(null, 0));
        queue.add(startVertex);
        this.graph = graph;
    }

    private void findNeighbors(final Vertex vertex) {
        neighbors = new LinkedList<>();
        for (DefaultWeightedEdge e : graph.edgesOf(vertex)) {
            Vertex v = graph.getEdgeTarget(e);
            if ((v.hasMarker() && v.isVisited()) || v.equals(vertex)) {
                continue;
            } else if (!v.hasMarker()) {
                v.setMarker(new Marker(null, 0));
                queue.add(v);
            }
            neighbors.add(v);
        }
    }

    @Override
    public boolean hasNext() {
        // getting the next vertex to visit
        if (currentVertex == null && queue.size() > 0) {
            currentVertex = queue.poll();
            findNeighbors(currentVertex);
            return true;
        }

        // is there a neighbor we haven't visited, or that will be closer now?
        if (currentVertex != null) {
            for (Vertex v : neighbors) {
                if (v.isVisited()) {
                    continue;
                }
                int weight = getEdgeWeight(v);
                if (v.getPredecessor() == null || v.getDistance() > currentVertex.getDistance() + weight) {
                    return true;
                }
            }
        }

        // if no neighbor was available, we try the next vertex
        if (queue.size() > 0) {
            currentVertex = queue.poll();
            findNeighbors(currentVertex);
            return true;
        }

        // still nothing, than we are done.
        return false;
    }

    private int getEdgeWeight(Vertex v) {
        DefaultWeightedEdge e = graph.getEdge(currentVertex, v);
        return (int) graph.getEdgeWeight(e);
    }

    @Override
    public Vertex next() {
        if (!currentVertex.isVisited()) {
            currentVertex.visit();
            return currentVertex;
        }

        if (neighbors.size() > 0) {
            Vertex n = neighbors.poll();
            int weight = getEdgeWeight(n);
            n.setMarker(new Marker(currentVertex, currentVertex.getDistance() + weight));
            if (neighbors.size() == 0) {
                currentVertex = null;
            }
            return n;
        }

        return null;
    }
}
