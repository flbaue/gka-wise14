package aufgabe1.algorithms;

import aufgabe1.Vertex;
import aufgabe1.io.GraphIO;
import junit.framework.TestCase;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedPseudograph;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;

public class GraphGeneratorTest extends TestCase {

    @Test
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

    @Test
    public void testGenerateNetwork() throws Exception {

        GraphGenerator graphGenerator = new GraphGenerator(800, 50, true);
        graphGenerator.generate();
        DirectedWeightedPseudograph<Vertex, DefaultWeightedEdge> graph = (DirectedWeightedPseudograph<Vertex, DefaultWeightedEdge>) graphGenerator.getGraph();
        Vertex[] vertexes = graphGenerator.convertGraphToNetwork(graph);

        assertNotNull(vertexes[0]);
        assertNotNull(vertexes[1]);

        EdmondsKarp edmondsKarp = new EdmondsKarp(graph, vertexes[0], vertexes[1]);
        int maxFlow = edmondsKarp.findMaxFlow();
        System.out.println(maxFlow);
        GraphIO graphIO = new GraphIO();
        if (maxFlow <= 0 || maxFlow > 100)
            graphIO.saveGraphAsFile(graph, new File("./graph_test_debug.txt"));

        assertTrue(maxFlow >= 0);

//        graphIO.saveGraphAsFile(graph, new File("./BigNet_4_2.txt"));
    }

    @Test
    public void testCompareRuntime() throws Exception {

        for (int i = 0; i < 1; i++) {
            System.out.println("Iteration " + i);

            GraphGenerator graphGenerator = new GraphGenerator(300000, 2500, true);
            graphGenerator.generate();
            DirectedWeightedPseudograph<Vertex, DefaultWeightedEdge> graph = (DirectedWeightedPseudograph<Vertex, DefaultWeightedEdge>) graphGenerator.getGraph();
            Vertex[] vertexes = graphGenerator.convertGraphToNetwork(graph);

            assertNotNull(vertexes[0]);
            assertNotNull(vertexes[1]);

            EdmondsKarp edmondsKarp = new EdmondsKarp(graph, vertexes[0], vertexes[1]);
            int maxFlow = edmondsKarp.findMaxFlow();
            long edmondskarpRuntime = edmondsKarp.getRuntimeMillis();
            System.out.println("Edmonds & Karp max flow:" + maxFlow);
            System.out.println("Edmonds & Karp runtime:" + edmondskarpRuntime);

            FordFulkerson fordFulkerson = new FordFulkerson(graph);
            fordFulkerson.run(vertexes[0], vertexes[1]);

            int maxFlow2 = fordFulkerson.getMaxFlow();
            long fordFulkersonRuntime = fordFulkerson.getRuntimeMillis();
            System.out.println("FordFulkerson max flow:" + maxFlow2);
            System.out.println("FordFulkerson runtime:" + fordFulkersonRuntime);

            org.junit.Assert.assertEquals(maxFlow, maxFlow2);

        /*GraphIO graphIO = new GraphIO();
        if (maxFlow <= 0 || maxFlow > 100)
            graphIO.saveGraphAsFile(graph, new File("./graph_test_debug.txt"));

        assertTrue(maxFlow >= 0);

        graphIO.saveGraphAsFile(graph, new File("./BigNet_4_2.txt"));*/
        }
    }

//    @Test
//    public void testGenerateNetworkDebug() throws Exception {
//
//        GraphIO graphIO = new GraphIO();
//        DirectedWeightedPseudograph<Vertex, DefaultWeightedEdge> graph = (DirectedWeightedPseudograph<Vertex, DefaultWeightedEdge>) graphIO.readGraphFromFile(new File("./graph_test_error.txt"));
//
//
//        Vertex source = null;
//        Vertex terminal = null;
//        for (Vertex v : graph.vertexSet()){
//            if(v.getName().equals("generatedNetworkSource")){
//                source = v;
//            }
//            if(v.getName().equals("generatedNetworkTerminal")){
//                terminal = v;
//            }
//        }
//        assertNotNull(source);
//        assertNotNull(terminal);
//
//        EdmondsKarp edmondsKarp = new EdmondsKarp(graph, source, terminal);
//        int maxFlow = edmondsKarp.findMaxFlow();
//        System.out.println(maxFlow);
//
//    }
}