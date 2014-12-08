package aufgabe1.algorithms;

import aufgabe1.Vertex;
import junit.framework.TestCase;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedPseudograph;

public class FordFulkersonTest extends TestCase {
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


    }
}