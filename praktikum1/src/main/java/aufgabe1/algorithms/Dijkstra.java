package aufgabe1.algorithms;

import aufgabe1.Marker;
import aufgabe1.Vertex;
import aufgabe1.utils.GraphUtils;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.List;
import java.util.Set;

/**
 * Created by flbaue on 26.10.14.
 */
public class Dijkstra {

    private final Graph<Vertex, DefaultWeightedEdge> graph;
    private final Vertex startVertex;
    private int dereferences = 0;

    public Dijkstra(Graph<Vertex, DefaultWeightedEdge> graph, Vertex startVertex) {
        this.graph = graph;
        this.startVertex = startVertex;
    }

    public void run() {
        // 0. Set all vertex marker to unvisited, no predecessor and infinity distance
        prepareVertexes();

        while (true) {

            // 1. Find unvisited vertex with lowest distance
            Vertex vertex = findClosestUnvisitedVertex();

            // 1.1 If no vertex is left, we are done.
            if (vertex == null) {
                break;
            } else {
                vertex.visit();
            }

            // 2. check and update all distances to unvisited neighbors
            updateUnvisitedNeighborsNew(vertex);
        }
    }

    private void updateUnvisitedNeighborsOld(Vertex vertex) {
        dereferences++;
        List<Vertex> neighbors = Graphs.neighborListOf(graph, vertex);
        for (Vertex neighbor : neighbors) {
            if (neighbor.isVisited()) {
                continue;
            }
            if (vertex.getName().equals("3") && neighbor.getName().equals("77") || vertex.getName().equals("77") && neighbor.getName().equals("3")) {
                System.out.println("match");
            }
            int edgeWeight = getEdgeWeight(vertex, neighbor);
            if (neighbor.getDistance() > vertex.getDistance() + edgeWeight) {
                neighbor.setMarker(new Marker(vertex, vertex.getDistance() + edgeWeight));
            }
        }
    }

    private void updateUnvisitedNeighborsNew(Vertex vertex) {
        dereferences++;
        Set<DefaultWeightedEdge> edgeSet = graph.edgesOf(vertex);
        for (DefaultWeightedEdge edge : edgeSet) {
            dereferences++;
            Vertex target = graph.getEdgeTarget(edge);
            //filter cycles and wrong directions
            if (target.equals(vertex)) {
                if (GraphUtils.isDirectedGraph(graph)) {
                    continue;
                } else {
                    target = graph.getEdgeSource(edge);
                    if (target.equals(vertex)) {
                        continue;
                    }
                }
            }

            //filter visited
            if (target.isVisited()) {
                continue;
            }

            int edgeWeight = (int) graph.getEdgeWeight(edge);
            if (target.getDistance() > vertex.getDistance() + edgeWeight) {
                target.setMarker(new Marker(vertex, vertex.getDistance() + edgeWeight));
            }
        }
    }

    private Vertex findClosestUnvisitedVertex() {
        Vertex closest = null;
        dereferences++;
        for (Vertex vertex : graph.vertexSet()) {
            if (vertex.isVisited()) {
                continue;
            }
            if (closest == null) {
                closest = vertex;
            } else if (closest.getDistance() > vertex.getDistance()) {
                closest = vertex;
            }
        }
        if (closest != null && closest.getPredecessor() == null) {
            closest.setMarker(new Marker(closest, 0));
        }
        return closest;
    }

    void prepareVertexes() {
        dereferences++;
        for (Vertex vertex : graph.vertexSet()) {

            if (vertex.equals(startVertex)) {
                vertex.setMarker(new Marker(vertex, 0));
            } else {
                vertex.setMarker(new Marker(null, Integer.MAX_VALUE));
            }
        }
    }

    private int getEdgeWeight(Vertex source, Vertex target) {
        dereferences++;
        DefaultWeightedEdge e = graph.getEdge(source, target);
        dereferences++;
        return (int) graph.getEdgeWeight(e);
    }

    public int getGraphDereferences() {
        return dereferences;
    }

    public void resetGraphDereferences() {
        dereferences = 0;
    }
}
