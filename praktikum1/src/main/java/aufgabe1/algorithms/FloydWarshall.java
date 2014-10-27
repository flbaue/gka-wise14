package aufgabe1.algorithms;

import aufgabe1.Vertex;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Created by schlegel11 on 27.10.14.
 */
public class FloydWarshall {

    private final Graph<Vertex, DefaultWeightedEdge> graph;
    private final Map<FloydWarshallPair, Integer> distanceMap = new HashMap<>();
    private final Map<FloydWarshallPair, Integer> transitMap = new HashMap<>();
    private int dereferences = 0;

    public FloydWarshall(Graph<Vertex, DefaultWeightedEdge> graph) {
        this.graph = graph;
    }

    public void run() {
        createDistanceMatrix();
        generateTransitMatrix();
    }

    public int getGraphDereferences() {
        return dereferences;
    }

    public void resetGraphDereferences() {
        dereferences = 0;
    }

    private void createDistanceMatrix() {
        dereferences++;
        for (Vertex vertex : graph.vertexSet()) {
            for (Vertex vertex1 : getAllTargets(vertex)) {
                if (!vertex.equals(vertex1))
                    distanceMap.put(new FloydWarshallPair(vertex, vertex1), getEdgeWeight(vertex, vertex1));
            }
        }
    }

    private void generateTransitMatrix() {
        dereferences++;
        for (Vertex vertex : graph.vertexSet()) {
            for (Vertex target : getAllTargets(vertex)) {
                for (Vertex source : getAllSources(vertex)) {
                    int dist = distanceMap.getOrDefault(new FloydWarshallPair(source, target), Integer.MAX_VALUE);
                    int currentDist = getEdgeWeight(source, vertex) + getEdgeWeight(vertex, target);
                    transitMap.put(new FloydWarshallPair(source, target), Math.min(dist, currentDist));
                }
            }
        }
    }

    private Set<Vertex> getAllSources(Vertex vertex) {
        Set<Vertex> vertices = new HashSet<>();
        getAllEdgesOfVertex(vertex, e -> vertices.add(graph.getEdgeSource(e)));
        vertices.remove(vertex);
        return vertices;
    }

    private Set<Vertex> getAllTargets(Vertex vertex) {
        Set<Vertex> vertices = new HashSet<>();
        getAllEdgesOfVertex(vertex, e -> vertices.add(graph.getEdgeTarget(e)));
        vertices.remove(vertex);
        return vertices;
    }

    private void getAllEdgesOfVertex(Vertex vertex, Consumer<DefaultWeightedEdge> consumer) {
        dereferences++;
        for (DefaultWeightedEdge edge : graph.edgesOf(vertex)) {
            dereferences++;
            consumer.accept(edge);

        }
    }

    private int getEdgeWeight(Vertex source, Vertex target) {
        DefaultWeightedEdge e = graph.getEdge(source, target);
        return (int) graph.getEdgeWeight(e);
    }
}
