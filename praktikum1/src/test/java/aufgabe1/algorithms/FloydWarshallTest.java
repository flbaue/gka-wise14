package aufgabe1.algorithms;

import aufgabe1.Vertex;
import junit.framework.TestCase;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedPseudograph;
import org.junit.Test;

public class FloydWarshallTest extends TestCase {


    @Test
    public void testFloydWarshall() throws Exception {
        DirectedWeightedPseudograph<Vertex, DefaultWeightedEdge> graph = new DirectedWeightedPseudograph(DefaultWeightedEdge.class);

        Vertex a = new Vertex("A");
        Vertex b = new Vertex("B");
        Vertex c = new Vertex("C");
        Vertex d = new Vertex("D");
        Vertex e = new Vertex("E");

        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);
        graph.addVertex(e);

        graph.setEdgeWeight(graph.addEdge(a, b), 2);
        graph.setEdgeWeight(graph.addEdge(a, c), 3);
        graph.setEdgeWeight(graph.addEdge(b, d), 9);
        graph.setEdgeWeight(graph.addEdge(c, b), 7);
        graph.setEdgeWeight(graph.addEdge(c, e), 5);
        graph.setEdgeWeight(graph.addEdge(e, d), 1);

        FloydWarshall floydWarshall = new FloydWarshall(graph);
        floydWarshall.run();

    }

}