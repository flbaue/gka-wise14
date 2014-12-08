package aufgabe1.algorithms;

import aufgabe1.Vertex;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by florian on 23.11.14.
 */
public class FloydWarshallNew {

    private Graph<Vertex, DefaultWeightedEdge> graph;
    private List<Vertex> vertexList;
    private List<DefaultWeightedEdge> edgeList;
    private int vertexCount;
    private double[][] distanceMatrix;
    private int[][] next;
    private double pathLength = -1;

    public FloydWarshallNew(Graph<Vertex, DefaultWeightedEdge> graph) {
        this.graph = graph;
        vertexList = new ArrayList<>(graph.vertexSet());
        vertexCount = vertexList.size();
        edgeList = new ArrayList<>(graph.edgeSet());
    }

    public void run() {
        initDistanceMatrix();
        initNextMatrix();
        distancesOfDirectNeighbors();
        transitDistances();
    }

    private void initNextMatrix() {
        next = new int[vertexCount][vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                next[i][j] = -1;
            }
        }
    }

    public List<Vertex> path(Vertex source, Vertex target) {
        List<Vertex> path = new LinkedList<>();
        int sourceIndex = vertexList.indexOf(source);
        int targetIndex = vertexList.indexOf(target);

        if (next[sourceIndex][targetIndex] == -1) {
            return path;
        }

        pathLength = distanceMatrix[sourceIndex][targetIndex];
        path.add(source);
        int currentIndex = sourceIndex;
        while (currentIndex != targetIndex) {
            currentIndex = next[currentIndex][targetIndex];
            path.add(vertexList.get(currentIndex));
        }

        return path;
    }

    public double getPathLength() {
        return pathLength;
    }

    private void transitDistances() {
        for (int k = 0; k < vertexCount; k++) {
            for (int i = 0; i < vertexCount; i++) {
                for (int j = 0; j < vertexCount; j++) {
                    double transitDistance = distanceMatrix[i][k] + distanceMatrix[k][j];
                    //if(vertexList.get())

                    if (distanceMatrix[i][j] > transitDistance) {
                        distanceMatrix[i][j] = transitDistance;
                        next[i][j] = next[i][k];
                    }
                }
            }
        }
    }

    private void distancesOfDirectNeighbors() {
        for (DefaultWeightedEdge edge : edgeList) {
            Vertex source = graph.getEdgeSource(edge);
            Vertex target = graph.getEdgeTarget(edge);
            int sourceIndex = vertexList.indexOf(source);
            int targetIndex = vertexList.indexOf(target);

            double weight = graph.getEdgeWeight(edge);

            if (weight < distanceMatrix[sourceIndex][targetIndex] || distanceMatrix[sourceIndex][targetIndex] == -1) {
                distanceMatrix[sourceIndex][targetIndex] = weight;
                next[sourceIndex][targetIndex] = targetIndex;
            }
        }
    }

    private void initDistanceMatrix() {
        distanceMatrix = new double[vertexCount][vertexCount];
        for (int row = 0; row < vertexCount; row++) {
            for (int column = 0; column < vertexCount; column++) {
                if (row == column) {
                    distanceMatrix[row][column] = 0;
                } else {
                    distanceMatrix[row][column] = Double.POSITIVE_INFINITY;
                }
            }
        }
    }
}
