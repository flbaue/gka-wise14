package aufgabe1.algorithms;

import aufgabe1.Vertex;
import aufgabe1.io.GraphIO;
import junit.framework.TestCase;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedPseudograph;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.List;

public class FloydWarshallTest extends TestCase {


    {

    }

    @Test
    public void testFloydWarshall() throws Exception {
        DirectedWeightedPseudograph<Vertex, DefaultWeightedEdge> graph = new DirectedWeightedPseudograph(DefaultWeightedEdge.class);

        Vertex a = new Vertex("A");
        Vertex b = new Vertex("B");
        Vertex c = new Vertex("C");
        Vertex d = new Vertex("D");

        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);

        graph.setEdgeWeight(graph.addEdge(a, b), 1);
        graph.setEdgeWeight(graph.addEdge(a, c), 4);
        graph.setEdgeWeight(graph.addEdge(b, d), 1);
        graph.setEdgeWeight(graph.addEdge(c, d), -3);

        FloydWarshallNew floydWarshall = new FloydWarshallNew(graph);
        floydWarshall.run();
        floydWarshall.path(a,d);
        System.out.println(floydWarshall.getPathLength());

        Dijkstra dijkstra = new Dijkstra(graph,a);
        dijkstra.run();
        System.out.println(d.getDistance());

    }


    @Test
    public void testFWDebug() throws Exception {
        GraphIO graphIO = new GraphIO();
        URL url = getClass().getResource("/debug_graph3.txt");
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

        FloydWarshallNew floydWarshallNew = new FloydWarshallNew(graph);
        floydWarshallNew.run();

        List<Vertex> path = floydWarshallNew.path(start, target);
        System.out.println(path);
        System.out.println(floydWarshallNew.getPathLength());
        org.junit.Assert.assertEquals(26,floydWarshallNew.getPathLength(), 0.01);
    }

}