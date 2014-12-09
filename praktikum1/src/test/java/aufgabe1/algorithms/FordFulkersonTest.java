package aufgabe1.algorithms;

import aufgabe1.Vertex;
import aufgabe1.io.GraphIO;
import junit.framework.TestCase;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedPseudograph;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.net.URL;

public class FordFulkersonTest extends TestCase {

    @Test
    public void testRun() throws Exception {
        DirectedWeightedPseudograph<Vertex, DefaultWeightedEdge> graph = new DirectedWeightedPseudograph(DefaultWeightedEdge.class);

        Vertex a = new Vertex("A");
        Vertex b = new Vertex("B");
        Vertex c = new Vertex("C");
        Vertex d = new Vertex("D");
        Vertex s = new Vertex("S");
        Vertex t = new Vertex("T");

        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);
        graph.addVertex(s);
        graph.addVertex(t);

        graph.setEdgeWeight(graph.addEdge(s, a), 9);
        graph.setEdgeWeight(graph.addEdge(s, b), 9);
        graph.setEdgeWeight(graph.addEdge(a, c), 8);
        graph.setEdgeWeight(graph.addEdge(a, b), 10);
        graph.setEdgeWeight(graph.addEdge(b, c), 1);
        graph.setEdgeWeight(graph.addEdge(b, d), 3);
        graph.setEdgeWeight(graph.addEdge(c, t), 10);
        graph.setEdgeWeight(graph.addEdge(d, c), 8);
        graph.setEdgeWeight(graph.addEdge(d, t), 7);

        FordFulkerson fordFulkerson = new FordFulkerson(graph);
        fordFulkerson.run(s, t);

        Assert.assertEquals(12, fordFulkerson.getMaxFlow());
    }

    @Test
    public void testRun2() throws Exception {
        DirectedWeightedPseudograph<Vertex, DefaultWeightedEdge> graph = new DirectedWeightedPseudograph(DefaultWeightedEdge.class);

        Vertex s = new Vertex("s");
        graph.addVertex(s);
        Vertex u = new Vertex("u");
        graph.addVertex(u);
        Vertex v = new Vertex("v");
        graph.addVertex(v);
        Vertex t = new Vertex("t");
        graph.addVertex(t);


        graph.setEdgeWeight(graph.addEdge(s, u), 10);
        graph.setEdgeWeight(graph.addEdge(s, u), 10);
        graph.setEdgeWeight(graph.addEdge(s, v), 10);
        graph.setEdgeWeight(graph.addEdge(u, t), 10);
        graph.setEdgeWeight(graph.addEdge(u, v), 30);
        graph.setEdgeWeight(graph.addEdge(v, t), 20);


        FordFulkerson fordFulkerson = new FordFulkerson(graph);
        fordFulkerson.run(s, t);

        Assert.assertEquals(30, fordFulkerson.getMaxFlow());
    }

    @Test
    public void testRun3() throws Exception {
        GraphIO graphIO = new GraphIO();
        URL url = getClass().getResource("/graph4.gka.txt");
        File file = new File(url.toURI());

        Graph<Vertex, DefaultWeightedEdge> graph = graphIO.readGraphFromFile(file);

        Vertex q = graph.vertexSet().stream().filter(vertex -> vertex.getName().equals("q")).findFirst().get();
        Vertex s = graph.vertexSet().stream().filter(vertex -> vertex.getName().equals("s")).findFirst().get();

        FordFulkerson fordFulkerson = new FordFulkerson(graph);
        fordFulkerson.run(q, s);

        Assert.assertEquals(26, fordFulkerson.getMaxFlow());
    }
}