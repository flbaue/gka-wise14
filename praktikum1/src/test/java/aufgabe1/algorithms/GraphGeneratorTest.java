package aufgabe1.algorithms;

import aufgabe1.Vertex;
import com.mxgraph.swing.handler.mxVertexHandler;
import junit.framework.TestCase;
import org.junit.Assert;

import java.util.Arrays;

public class GraphGeneratorTest extends TestCase {

    public void testGenerate() throws Exception {

        GraphGenerator graphGenerator = new GraphGenerator(5999, 100, true);
        graphGenerator.generate();

        Assert.assertEquals(5999, graphGenerator.getGraph().edgeSet().size());
        Assert.assertEquals(100, graphGenerator.getGraph().vertexSet().size());

        //Shortest path with edge weight 1 from 1 -> 20
        graphGenerator.addShortestPath(Arrays.asList(new Vertex("1"), new Vertex("20")));
        Assert.assertEquals(6000, graphGenerator.getGraph().edgeSet().size());
        Assert.assertEquals(100, graphGenerator.getGraph().vertexSet().size());

        //Shortest path with edge weight 1 from 1 -> 20 -> 15
        graphGenerator.addShortestPath(Arrays.asList(new Vertex("1"), new Vertex("20"), new Vertex("15")));
        Assert.assertEquals(6002, graphGenerator.getGraph().edgeSet().size());
        Assert.assertEquals(100, graphGenerator.getGraph().vertexSet().size());
    }
}