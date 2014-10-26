package aufgabe1.io;

import aufgabe1.Vertex;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedPseudograph;
import org.junit.Test;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GraphIOTest {

    @Test
    public void testReadGraphFromFile() throws Exception {
        URL url = getClass().getResource("/SimpleTestGraph.txt");
        File file = new File(url.toURI());

        GraphIO graphIO = new GraphIO();
        Graph<Vertex, DefaultWeightedEdge> graph = graphIO.readGraphFromFile(file);

        assertEquals(2, graph.edgeSet().size());
        assertEquals(3, graph.vertexSet().size());
        assertTrue(graph instanceof DirectedWeightedPseudograph);
    }

    @Test
    public void testSaveGraphAsFile() throws Exception {
        DirectedWeightedPseudograph<Vertex, DefaultWeightedEdge> graph = new DirectedWeightedPseudograph(DefaultWeightedEdge.class);

        Vertex a = new Vertex("A");
        Vertex b = new Vertex("B");
        Vertex c = new Vertex("C");

        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);

        DefaultWeightedEdge e;
        e = graph.addEdge(a, b);
        graph.setEdgeWeight(e, 2);
        graph.addEdge(b, c);
        graph.addEdge(c, c);
        graph.addEdge(a, c);
        graph.addEdge(c, a);

        File file = new File("graph.txt");
        GraphIO graphIO = new GraphIO();
        graphIO.saveGraphAsFile(graph, file);
    }
}