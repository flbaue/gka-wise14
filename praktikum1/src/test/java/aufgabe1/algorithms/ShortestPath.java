package aufgabe1.algorithms;

import aufgabe1.Vertex;
import aufgabe1.io.GraphIO;
import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Created by florian on 17.11.14.
 */
public class ShortestPath {

    @Test
    public void test_3_1() throws Exception {
        GraphIO graphIO = new GraphIO();
        URL url = getClass().getResource("/graph3.gka.txt");
        File file = new File(url.toURI());
        Graph<Vertex, DefaultWeightedEdge> graph = graphIO.readGraphFromFile(file);

        Vertex startVertex = null;
        Vertex targetVertex = null;
        for (Vertex vertex : graph.vertexSet()) {
            if (vertex.getName().equals("Hamburg")) {
                startVertex = vertex;
            } else if (vertex.getName().equals("Norderstedt")) {
                targetVertex = vertex;
            }
        }
        org.junit.Assert.assertNotNull(startVertex);
        org.junit.Assert.assertNotNull(targetVertex);

        Dijkstra dijkstra = new Dijkstra(graph, startVertex);
        dijkstra.run();
        System.out.println("Dijkstra dereferences:" + dijkstra.getGraphDereferences());
        printPath(startVertex, targetVertex);

        FloydWarshall floydWarshall = new FloydWarshall(graph);
        floydWarshall.run();
        System.out.println("FloydWarshall dereferences:" + floydWarshall.getGraphDereferences());
        printPath(startVertex, targetVertex);

    }

    private void printPath(Vertex startVertex, Vertex targetVertex) {
        String path;
        Vertex tmp;
        path = targetVertex.getName();
        tmp = targetVertex;
        while (!startVertex.equals(tmp)) {
            tmp = tmp.getPredecessor();
            path += " -> " + tmp.getName();
        }
        System.out.println("Path: " + path);
    }

    @Test
    public void test_3_3() {
        GraphGenerator graphGenerator = new GraphGenerator(6000, 100, true);
        graphGenerator.generate();
        //graphGenerator.addShortestPath(Arrays.asList(new Vertex("3"), new Vertex("77")));
        WeightedGraph<Vertex, DefaultWeightedEdge> graph = graphGenerator.getGraph();

        Vertex start = null;
        Vertex target = null;

        for (Vertex vertex : graph.vertexSet()) {
            if (vertex.getName().equals("3")) {
                start = vertex;
            } else if (vertex.getName().equals("77")) {
                target = vertex;
            }
        }
        org.junit.Assert.assertNotNull(start);
        org.junit.Assert.assertNotNull(target);

        Dijkstra dijkstra = new Dijkstra(graph, start);
        dijkstra.run();
        System.out.println("Djikstra Target distance:" + target.getMarker().getDistance());
        printPath(start, target);

        FloydWarshallNew floydWarshall = new FloydWarshallNew(graph);
        floydWarshall.run();
        List<Vertex> path = floydWarshall.path(start, target);
        System.out.println("fw: " + path + ", distance: " + floydWarshall.getPathLength());

        if (path.size() != target.getDistance()) {
            File file = new File("./graph_test_error.txt");
            file.delete();
            new GraphIO().saveGraphAsFile(graph, file);
        }
        //System.out.println("FloydWarshall Target distance:" + target.getMarker().getDistance());
        //printPath(start,target);

    }
}
