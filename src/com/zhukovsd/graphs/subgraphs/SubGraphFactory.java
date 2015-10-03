package com.zhukovsd.graphs.subgraphs;

import com.zhukovsd.graphs.Edge;
import com.zhukovsd.graphs.Graph;
import com.zhukovsd.graphs.Vertex;

/**
 * Factory useful for instantiating specific {@link SubGraph subgraphs} such as spanning tree.
 */
public class SubGraphFactory {
    /**
     * create spanning tree for given graph, spanning tree is a subgraph of given {@link Graph graph},
     * which contains all of it's {@link Vertex vertexes} without loops.
     * @param parentGraph parent graph
     * @param <T> type of vertex in parent graph
     * @return resulting spanning tree
     */
    public static <T extends Vertex<T>> SubGraph<T> createSpanningTree(Graph<T> parentGraph) {
        SubGraph<T> result = new SubGraph<>(parentGraph);
        result.findSpanningTree();

        return result;
    }

    /**
     * create full subgraph for given {@link Graph graph}, which means that it's contains all of it's {@link Vertex vertexes}
     * and {@link Edge edges}.
     * @param parentGraph parent graph
     * @param <T> type of vertex in parent graph
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
}
