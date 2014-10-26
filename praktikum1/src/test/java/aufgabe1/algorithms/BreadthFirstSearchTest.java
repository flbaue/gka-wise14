package aufgabe1.algorithms;

import aufgabe1.Vertex;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedPseudograph;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BreadthFirstSearchTest {

    @Test
    public void testIterator() throws Exception {
        WeightedPseudograph<Vertex, DefaultWeightedEdge> graph = new WeightedPseudograph(DefaultWeightedEdge.class);

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

        graph.addEdge(a, b);
        graph.addEdge(a, c);
        graph.addEdge(b, d);
        graph.addEdge(b, e);
        graph.addEdge(b, c);
        graph.addEdge(c, e);

        BreadthFirstSearch bfs = new BreadthFirstSearch(graph, a);
        Iterator<Vertex> iterator = bfs.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(a, iterator.next());

        while (iterator.hasNext()) {
            iterator.next();
        }

        assertTrue(a.isVisited());
        assertTrue(b.isVisited());
        assertTrue(c.isVisited());
        assertTrue(d.isVisited());
        assertTrue(e.isVisited());

        assertEquals(b, d.getPredecessor());
    }
}