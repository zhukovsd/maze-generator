package com.zhukovsd.graphs.subgraphs;

import com.zhukovsd.graphs.Edge;
import com.zhukovsd.graphs.Graph;
import com.zhukovsd.graphs.Vertex;

import java.util.ArrayList;

/**
 * Factory useful for instantiating specific {@link SubGraph subgraphs} such as spanning tree.
 */
public class SubGraphFactory {
    /**
     * Enum for implemented shortest path finding algorithms.
     */
    public enum ShortestPathAlgorithm {
        /**
         * Dijkstra algorithm implementation, <a href="https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm">wiki article</a>.
         * More general, works for graphs with loops, graphs with weighed edges, but really slow compared to BFS,
         * approximately 10 times slower than BFS.
         */
        DIJKSTRA,
        /**
         * Path search with breadth first search - basic algorithm for graph traversing,
         * <a href="https://en.wikipedia.org/wiki/Breadth-first_search">wiki article</a>. Works only for minimal spanning trees, i.e.
         * graphs without loops, can't consider edges weights, but really fast (approximately 10 times faster than Dijkstra).
         */
        BREADTH_FIRST_SEARCH
    }

    /**
     * Create spanning tree for given graph, spanning tree is a subgraph of given {@link Graph graph},
     * which contains all of it's {@link Vertex vertexes} without loops.
     * @param <T> type of vertex in parent graph
     * @param parentGraph parent graph
     * @return resulting spanning tree
     */
    public static <T extends Vertex<T>> SubGraph<T> createSpanningTree(Graph<T> parentGraph, ArrayList<T> excludedVertexes) {
        SubGraph<T> result = new SubGraph<>(parentGraph);
        result.findSpanningTree(excludedVertexes);

        return result;
    }

    /**
     * Create full subgraph for given {@link Graph graph}, which means that it's contains all of it's {@link Vertex vertexes}
     * and {@link Edge edges}.
     * @param <T> type of vertex in parent graph
     * @param parentGraph parent graph
     * @return resulting subgraph
     */
    public static <T extends Vertex<T>> SubGraph<T> createFullSubGraph(Graph<T> parentGraph) {
        SubGraph<T> result = new SubGraph<>(parentGraph);

        for (T vertex : parentGraph.vertexList) {
            result.addVertex(new SubGraphVertex<>(vertex));
        }

        for (T vertex : parentGraph.vertexList) {
            for (Edge<T> edge : vertex.edgeList) {
                result.connect(result.parentVertexesMap.get(edge.source), result.parentVertexesMap.get(edge.destination));
            }
        }

        return result;
    }

    /**
     * Find shortest path between given {@link Vertex vertexes} in given {@link Graph graph}.
     * @param <T> type of vertex in parent graph
     * @param parentGraph parent graph to find path in
     * @param pathSourceVertex vertex to start path from
     * @param pathDestinationVertex destination vertex for path
     * @param algorithm algorithm to be used to find shortest path in graph, see {@link com.zhukovsd.graphs.subgraphs.SubGraphFactory.ShortestPathAlgorithm}
     * @return resulting path
     */
    public static <T extends Vertex<T>> SubGraph<T> createShortestPath(Graph<T> parentGraph, T pathSourceVertex,
        T pathDestinationVertex, ShortestPathAlgorithm algorithm
    ) {
        SubGraph<T> result = new SubGraph<>(parentGraph);

        if (algorithm == ShortestPathAlgorithm.DIJKSTRA)
            result.findShortestPathDijkstra(pathSourceVertex, pathDestinationVertex);
        else if (algorithm == ShortestPathAlgorithm.BREADTH_FIRST_SEARCH)
            result.findShortestPathBFS(pathSourceVertex, pathDestinationVertex);

        return result;
    }
}
