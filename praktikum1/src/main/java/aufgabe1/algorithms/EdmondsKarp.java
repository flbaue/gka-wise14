package aufgabe1.algorithms;

import aufgabe1.Vertex;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.*;

/**
 * Created by florian on 08.12.14.
 */
public class EdmondsKarp {

    private final DirectedGraph<Vertex, DefaultWeightedEdge> graph;
    private final Vertex source;
    private final Vertex terminal;
    private Node sourceNode;
    private Node terminalNode;
    private List<Node> nodes;
    private long startTime;
    private long endTime;

    public EdmondsKarp(DirectedGraph<Vertex, DefaultWeightedEdge> graph, Vertex source, Vertex terminal) {
        this.graph = graph;
        this.source = source;
        this.terminal = terminal;

        setup();
    }

    private void setup() {
        nodes = new ArrayList<>(graph.vertexSet().size());
        Map<Vertex, Node> vertexNodeMap = new HashMap<>(graph.vertexSet().size());

        for (Vertex vertex : graph.vertexSet()) {
            Node node = new Node(vertex);
            nodes.add(node);
            vertexNodeMap.put(vertex, node);
            if (vertex == source) {
                sourceNode = node;
            }
            if (vertex == terminal) {
                terminalNode = node;
            }
        }

        for (Node node : nodes) {
            Vertex v = node.prototype;
            for (DefaultWeightedEdge e : graph.outgoingEdgesOf(v)) {
                Vertex target = graph.getEdgeTarget(e);
                Node targetNode = vertexNodeMap.get(target);
                Arc arc = new Arc(node, targetNode, (int) graph.getEdgeWeight(e));
                if (!node.arcs.contains(arc)) {
                    node.arcs.add(arc);
                } else {
                    for (Arc oArc : node.arcs) {
                        if (oArc.equals(arc)) {
                            oArc.capacity += arc.capacity;
                        }
                    }
                }

            }
        }
    }

    public int findMaxFlow() {
        startTime = System.currentTimeMillis();

        Path path = findShortestPath();
        while (path != null) {
            //flood path with min path capacity
            augmentPath(path);

            //find next shortest path
            path = findShortestPath();
        }

        int maxFlow = 0;
        for (Arc arc : sourceNode.arcs) {
            maxFlow += arc.flow;
        }

        endTime = System.currentTimeMillis();
        return maxFlow;
    }

    private void augmentPath(Path path) {
        for (Arc arc : path.arcs) {
            arc.capacity -= path.minCapacity;
            arc.flow += path.minCapacity;
        }
    }

    private Path findShortestPath() {
        // BFS

        //TODO remove predecessors & visited;
        cleanNodes();

        Queue<Node> queue = new LinkedList<>();
        Path path = new Path();
        queue.add(sourceNode);
        sourceNode.predecessor = sourceNode;
        sourceNode.visited = true;

        while (queue.size() > 0) {
            Node node = queue.poll();

            for (Arc arc : node.arcs) {
                if (arc.terminal.visited == false && arc.capacity > 0) {
                    arc.terminal.predecessor = node;
                    arc.terminal.visited = true;
                    queue.add(arc.terminal);
                    if (arc.terminal == terminalNode) {
                        queue.clear();
                        break;
                    }
                }
            }
        }

        if (terminalNode.predecessor == null) {
            return null;
        }

        Node t = terminalNode;
        do {
            Node s = t.predecessor;
            Arc a = s.arcTo(t);
            path.arcs.addFirst(a);
            path.minCapacity = (a.capacity < path.minCapacity || path.minCapacity == 0) ? a.capacity : path.minCapacity;
            t = t.predecessor;
        } while (t != sourceNode);

        return path;
    }

    public long getRuntimeMillis() {
        return endTime - startTime;
    }

    private void cleanNodes() {
        for (Node node : nodes) {
            node.predecessor = null;
            node.visited = false;
        }
    }


    class Node {
        final Vertex prototype;
        Set<Arc> arcs = new HashSet<>();
        boolean visited = false;
        Node predecessor = null;

        public Node(Vertex prototype) {
            this.prototype = prototype;
        }

        public Arc arcTo(Node t) {
            for (Arc arc : arcs) {
                if (arc.terminal == t)
                    return arc;
            }
            throw new RuntimeException("No arc to given terminal");
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node node = (Node) o;

            if (!prototype.equals(node.prototype)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return prototype.hashCode();
        }
    }

    class Arc {
        final Node source;
        final Node terminal;
        int capacity;
        int flow;

        public Arc(Node source, Node terminal, int capacity) {
            this.source = source;
            this.terminal = terminal;
            this.capacity = capacity;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Arc arc = (Arc) o;

            if (!source.equals(arc.source)) return false;
            if (!terminal.equals(arc.terminal)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = source.hashCode();
            result = 31 * result + terminal.hashCode();
            return result;
        }
    }

    class Path {
        LinkedList<Arc> arcs = new LinkedList<>();
        int minCapacity = 0;
    }
}
