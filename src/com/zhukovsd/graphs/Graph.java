package com.zhukovsd.graphs;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic graph implementation.
 * @param <E> type of {@link Vertex vertex} in graph
 */
public class Graph<E extends Vertex<E>> {
    /**
     * A list of connected {@link Vertex vertexes}.
     */
    public final List<E> vertexList = new ArrayList<>();

    /**
     * Method adds {@link Vertex vertex} to the current graph.
     * @param vertex vertex to add
     */
    public void addVertex(E vertex) {
        vertexList.add(vertex);
    }

    /**
     * Method connects 2 {@link Vertex vertexes} by creating directional {@link Edge edge}.
     * Method assumes that given vertexes belongs to current graph.
     * @param left left vertex, source for new directional edge connecting given vertexes
     * @param right right vertex, destination for new directional edge connecting given vertexes
     * @return new edge, connecting given vertexes
     */
    public Edge<E> connect(E left, E right) {
        Edge<E> edge = new Edge<>(left, right);
        left.edgeList.add(edge);

        return edge;
    }

    /**
     * Method connects 2 {@link Vertex vertexes} by creating 2 directional {@link Edge edges}.
     * Method assumes that given vertexes belongs to current graph.
     * @param left first vertex to connect
     * @param right second vertex to connect
     */
    public void connectToEachOther(E left, E right) {
        connect(left, right);
        connect(right, left);
    }

    /**
     * Method disconnects 2 {@link Vertex vertexes} by removing corresponding {@link Edge edge}
     * from <code>left</code> vertex {@link Vertex#edgeList edgeList}.
     * @param left source of removed edge
     * @param right destination of removed edge
     */
    public void disconnect(E left, E right) {
        for (Edge<E> edge : left.edgeList) {
            if (edge.destination == right) {
                left.edgeList.remove(edge);
                break;
            }
        }
    }

    /**
     * Method disconnects 2 {@link Vertex vertexes} by removing corresponding {@link Edge edges}
     * from both vertexes {@link Vertex#edgeList edgeLists}.
     * @param left source of removed edge
     * @param right destination of removed edge
     */
    public void disconnectFromEachOther(E left, E right) {
        disconnect(left, right);
        disconnect(right, left);
    }
}
