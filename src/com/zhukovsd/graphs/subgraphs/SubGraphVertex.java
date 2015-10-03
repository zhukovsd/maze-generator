package com.zhukovsd.graphs.subgraphs;

import com.zhukovsd.graphs.Vertex;

/**
 * Vertex of subgraph. It corresponds to particular vertex of parent graph.
 * @param <T> self-referential type used as type parameter for inner fields
 */
public class SubGraphVertex<T extends Vertex<T>> extends Vertex<SubGraphVertex<T>> {
    /**
     * Vertex of outer graph, current sub graph vertex corresponds to this vertex.
     */
    public final T parentVertex;

    /**
     * Create sub graph vertex for given parent graph vertex.
     * @param parentVertex parent graph vertex
     */
    public SubGraphVertex(T parentVertex) {
        this.parentVertex = parentVertex;
    }
}
