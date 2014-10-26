package aufgabe1.io;

import aufgabe1.Vertex;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedPseudograph;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
        if (file.exists())
            file.delete();
        GraphIO graphIO = new GraphIO();
        graphIO.saveGraphAsFile(graph, file);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            assertEquals("A -> B: 2;", line);

            line = reader.readLine();
            assertEquals("B -> C: 1;", line);

            line = reader.readLine();
            assertEquals("C -> C: 1;", line);

            line = reader.readLine();
            assertEquals("A -> C: 1;", line);

            line = reader.readLine();
            assertEquals("C -> A: 1;", line);
        }

        file.delete();
    }

    @Test
    public void testGraphIOWithGraphGka6() throws Exception {
        GraphIO graphIO = new GraphIO();
        URL url = getClass().getResource("/graph6.gka.txt");
        File file = new File(url.toURI());

        Graph<Vertex, DefaultWeightedEdge> graph = graphIO.readGraphFromFile(file);

        assertEquals(12, graph.vertexSet().size());
        assertEquals(15, graph.edgeSet().size());
    }

    @Test
    public void testGraphIOWithGraphGka2() throws Exception {
        GraphIO graphIO = new GraphIO();
        URL url = getClass().getResource("/graph2.gka.txt");
        File file = new File(url.toURI());

        Graph<Vertex, DefaultWeightedEdge> graph = graphIO.readGraphFromFile(file);

        assertEquals(11, graph.vertexSet().size());
        assertEquals(38, graph.edgeSet().size());
    }
}