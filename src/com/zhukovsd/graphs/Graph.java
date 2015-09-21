package com.zhukovsd.graphs;

import java.util.ArrayList;
import java.util.List;

/**
 * A list of connected vertexes
 * @param <E> type of Vertex in graph
 */
public class Graph<E extends Vertex> {
    public final List<E> vertexList = new ArrayList<E>();
}
