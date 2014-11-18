package aufgabe1.algorithms;

import aufgabe1.Vertex;
import aufgabe1.io.GraphIO;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.Set;

public class DijkstraTest {

    private Dijkstra dijkstra;

    @Test
    public void testRun() throws Exception {
        GraphIO graphIO = new GraphIO();
        URL url = getClass().getResource("/graph3.gka.txt");
        File file = new File(url.toURI());

        Vertex startVertex = null;
        Graph<Vertex, DefaultWeightedEdge> graph = graphIO.readGraphFromFile(file);
        for (Vertex vertex : graph.vertexSet()) {
            if (vertex.getName().equals("Hamburg")) {
                startVertex = vertex;
            }
        }
        org.junit.Assert.assertNotNull(startVertex);

        dijkstra = new Dijkstra(graph, startVertex);
        dijkstra.run();

        org.junit.Assert.assertEquals(102, dijkstra.getGraphDereferences());

        Set<Vertex> vertexSet = graph.vertexSet();
        int counter = vertexSet.size() - 2;
        for (Vertex vertex : vertexSet) {
            if (vertex.getName().equals("Norderstedt")) {
                org.junit.Assert.assertEquals(3, vertex.getDistance());
            } else if (vertex.getName().equals("Kiel")) {
                org.junit.Assert.assertEquals(4, vertex.getDistance());
            } else {
                counter -= 1;
                if (counter < 0) {
                    throw new AssertionError();
                }
            }
        }
    }

    @Test
    public void testPrepareVertexes() throws Exception {
        GraphIO graphIO = new GraphIO();
        URL url = getClass().getResource("/graph3.gka.txt");
        File file = new File(url.toURI());

        Vertex startVertex = null;
        Graph<Vertex, DefaultWeightedEdge> graph = graphIO.readGraphFromFile(file);
        for (Vertex vertex : graph.vertexSet()) {
            if (vertex.getName().equals("Hamburg")) {
                startVertex = vertex;
            }
        }
        org.junit.Assert.assertNotNull(startVertex);
        dijkstra = new Dijkstra(graph, startVertex);
        dijkstra.run();

        for (Vertex vertex : graph.vertexSet()) {
            org.junit.Assert.assertNotNull(vertex.getPredecessor());
        }

        dijkstra.prepareVertexes();

        for (Vertex vertex : graph.vertexSet()) {
            if (vertex.getName().equals("Hamburg")) {
                org.junit.Assert.assertEquals("Hamburg", vertex.getPredecessor().getName());
            } else {
                org.junit.Assert.assertNull(vertex.getPredecessor());
            }
        }
    }
}