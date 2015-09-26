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

    /**
     * Method to connect two vertexes by creating directional edge. Method assumes given vertexes belongs to current graph.
     * @param left left vertex, source for new directional edge connecting given vertexes
     * @param right right vertex, destination for new directional edge connecting given vertexes
     */
    public void connect(Vertex left, Vertex right) {
        left.edgeList.add(new Edge(left, right));
    }
}
