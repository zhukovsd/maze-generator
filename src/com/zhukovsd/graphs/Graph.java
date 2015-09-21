package com.zhukovsd.graphs;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic graph implementation.
 * @param <E> type of {@link Vertex vertex} in graph
 */
public class Graph<E extends Vertex> {
    /**
     * A list of connected {@link Vertex vertexes}
     */
    public final List<E> vertexList = new ArrayList<E>();
}
