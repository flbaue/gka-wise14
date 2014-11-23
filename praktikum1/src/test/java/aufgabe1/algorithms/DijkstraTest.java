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
                org.junit.Assert.assertEquals(384, vertex.getDistance());
            } else if (vertex.getName().equals("Kiel")) {
                org.junit.Assert.assertEquals(432, vertex.getDistance());
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

    @Test
    public void debug() throws Exception {
        GraphIO graphIO = new GraphIO();
        URL url = getClass().getResource("/debug_graph.txt");
        File file = new File(url.toURI());
        Graph<Vertex, DefaultWeightedEdge> graph = graphIO.readGraphFromFile(file);
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

        Dijkstra dijkstra1 = new Dijkstra(graph,start);
        dijkstra1.run();

        System.out.println("Djikstra Target distance:" + target.getMarker().getDistance());
        printPath(start, target);

    }

    private void printPath(Vertex start, Vertex target) {
        String path;
        Vertex tmp;
        path = target.getName();
        tmp = target;
        while (!start.equals(tmp)) {
            tmp = tmp.getPredecessor();
            path += " -> " + tmp.getName();
        }
        System.out.println("Path: " + path);
    }
}