package aufgabe1.io;

import aufgabe1.Vertex;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedPseudograph;
import org.junit.Test;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.*;

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

    }
}