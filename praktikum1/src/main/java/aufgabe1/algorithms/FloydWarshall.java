package aufgabe1.algorithms;

import aufgabe1.Vertex;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.*;

/**
 * Created by schlegel11 on 27.10.14.
 */
public class FloydWarshall {

    private final Graph<Vertex, DefaultWeightedEdge> graph;
    private double[][] distanceMatrix;
    private int[][] transitMatrix;
    private List<Vertex> vertices;
    private int dereferences = 0;

    public FloydWarshall(Graph<Vertex, DefaultWeightedEdge> graph) {
        this.graph = graph;
    }

    public void run() {
        vertices = new ArrayList<>(graph.vertexSet());
        createDistanceMatrix();
        runAlgorithm();
    }

    public int getGraphDereferences() {
        return dereferences;
    }

    public void resetGraphDereferences() {
        dereferences = 0;
    }

    public double getShortestPathWeight(Vertex source, Vertex target) {
        double pathWeight = 0;
        if (vertices.containsAll(Arrays.asList(source, target))) {
            pathWeight = distanceMatrix[vertices.indexOf(source)][vertices.indexOf(target)];
        }
        return pathWeight;
    }

    public Collection<Vertex> getPath(Vertex source, Vertex target) {
        List<Vertex> path = null;

        if (vertices.containsAll(Arrays.asList(source, target))) {
            path = getPath_(vertices.indexOf(source), vertices.indexOf(target), new ArrayList<>());
            Collections.reverse(path);
            path.add(target);
        }
        return path;
    }

    private List<Vertex> getPath_(int sourceIndex, int targetIndex, List<Vertex> path) {
        int nextVertex = transitMatrix[sourceIndex][targetIndex];

        if (nextVertex == Integer.MAX_VALUE) {
            path.add(vertices.get(sourceIndex));
            return path;
        } else {
            path.add(vertices.get(nextVertex));
            return getPath_(sourceIndex, nextVertex, path);
        }
    }

    private void createDistanceMatrix() {
        distanceMatrix = new double[vertices.size()][vertices.size()];
        for (double[] elem : distanceMatrix) {
            Arrays.fill(elem, Double.POSITIVE_INFINITY);
        }

        for (ListIterator<Vertex> sourceItr = vertices.listIterator(); sourceItr.hasNext(); ) {
            Vertex sourceVertex = sourceItr.next();
            int sourceIndex = sourceItr.nextIndex() - 1;
            for (ListIterator<Vertex> targetItr = getAllTargets(sourceVertex).listIterator(); targetItr.hasNext(); ) {
                Vertex targetVertex = targetItr.next();
                int targetIndex = vertices.indexOf(targetVertex);
                distanceMatrix[sourceIndex][targetIndex] = sourceVertex.equals(targetVertex) ? 0 : getEdgeWeight(sourceVertex, targetVertex);
            }
        }
    }

    private void runAlgorithm() {
        transitMatrix = new int[vertices.size()][vertices.size()];
        for (int[] elem : transitMatrix) {
            Arrays.fill(elem, Integer.MAX_VALUE);
        }

        for (int k = 0; k < vertices.size(); k++) {
            for (int i = 0; i < vertices.size(); i++) {
                for (int j = 0; j < vertices.size(); j++) {
                    double distance = distanceMatrix[i][k] + distanceMatrix[k][j];
                    if (distance < distanceMatrix[i][j]) {
                        distanceMatrix[i][j] = distance;
                        transitMatrix[i][j] = k;
                    }
                }
            }
        }
    }

    private List<Vertex> getAllTargets(Vertex vertex) {
        List<Vertex> vertices = new ArrayList<>();
        vertices.add(vertex);
        dereferences++;
        for (DefaultWeightedEdge edge : graph.edgesOf(vertex)) {
            dereferences++;
            vertices.add(graph.getEdgeTarget(edge));
        }
        return vertices;
    }

    private int getEdgeWeight(Vertex source, Vertex target) {
        dereferences++;
        DefaultWeightedEdge e = graph.getEdge(source, target);
        dereferences++;
        return (int) graph.getEdgeWeight(e);
    }
}
