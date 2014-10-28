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
public class BreadthFirstSearchIterator implements Iterator<Vertex> {

    private Deque<Vertex> queue = new LinkedList<>();
    private Graph<Vertex, DefaultWeightedEdge> graph;
    private int dereferences = 0;

    public BreadthFirstSearchIterator(Graph<Vertex, DefaultWeightedEdge> graph, Vertex startVertex) {
        GraphUtils.removeMarkers(graph);
        startVertex.setMarker(new Marker(startVertex, 0));
        queue.add(startVertex);
        this.graph = graph;
    }

    private void findNeighbors(final Vertex vertex) {
        //neighbors = new LinkedList<>();
        dereferences++;
        for (DefaultWeightedEdge e : graph.edgesOf(vertex)) {
            dereferences++;
            Vertex neighbor = Graphs.getOppositeVertex(graph, e, vertex);
            if (neighbor.hasMarker() || neighbor.equals(vertex)) {
                continue;
            } else if (!neighbor.hasMarker()) {
                neighbor.setMarker(new Marker(vertex, vertex.getDistance() + 1));
                queue.add(neighbor);
            }
        }
    }

    @Override
    public boolean hasNext() {
        // getting the next vertex to visit
        return queue.size() > 0;
    }

    @Override
    public Vertex next() {
        Vertex v = queue.poll();
        findNeighbors(v);
        return v;
    }

    public int getGraphDereferences() {
        return dereferences;
    }

    public void resetGraphDereferences() {
        dereferences = 0;
    }
}
