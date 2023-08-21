package com.zhukovsd.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Basic graph implementation. Graph consists of vertexes and pairs of opposite-directional edges between them.
 * If vertex A connected to vertex B, then vertex A holds edge AB, and vertex B holds edge BA. Situations, when A
 * connected to B, but B not connected to A, are not allowed.
 * @param <E> type of {@link Vertex vertex} in graph
 */
public class Graph<E extends Vertex<E>> {
    /**
     * A list of connected {@link Vertex vertexes}.
     */
    public final List<E> vertexList = new ArrayList<>();

    /**
     * Map that consists of reverse {@link Edge} edges pairs. Used for fast access to reverse edge for given edge.
     */
    public final HashMap<Edge<E>, Edge<E>> reverseEdgesMap = new HashMap<>();

    /**
     * Method adds {@link Vertex vertex} to the current graph.
     * @param vertex vertex to add
     */
    public void addVertex(E vertex) {
        vertexList.add(vertex);
    }

    /**
     * Method connects 2 {@link Vertex vertexes} by creating 2 opposite-directional {@link Edge edges}.
     * Method assumes that given vertexes belongs to current graph.
     * @param left first vertex to connect
     * @param right second vertex to connect
     */
    public void connectToEachOther(E left, E right) {
        if (!isConnected(left, right)) {
            Edge<E> leftEdge = new Edge<>(left, right);
            Edge<E> rightEdge = new Edge<>(right, left);

            left.edgeList.add(leftEdge);
            right.edgeList.add(rightEdge);

            reverseEdgesMap.put(leftEdge, rightEdge);
            reverseEdgesMap.put(rightEdge, leftEdge);
        }
    }

    /**
     * Method disconnects 2 {@link Vertex vertexes} by removing corresponding {@link Edge edge}
     * from <code>left</code> vertex {@link Vertex#edgeList edgeList}.
     * @param left source of removed edge
     * @param right destination of removed edge
     */
    private Edge<E> disconnect(E left, E right) {
        for (Edge<E> edge : left.edgeList) {
            if (edge.destination == right) {
                left.edgeList.remove(edge);
                return edge;
            }
        }

        return null;
    }

    /**
     * Method disconnects 2 {@link Vertex vertexes} by removing corresponding {@link Edge edges}
     * from both vertexes {@link Vertex#edgeList edgeLists}.
     * @param left source of removed edge
     * @param right destination of removed edge
     */
    public void disconnectFromEachOther(E left, E right) {
        Edge<E> leftEdge = disconnect(left, right);
        Edge<E> rightEdge = disconnect(right, left);

        reverseEdgesMap.remove(leftEdge);
        reverseEdgesMap.remove(rightEdge);
    }

    /**
     * Method checks if given {@link Vertex vertexes} connected within the current graph. If so, {@link #reverseEdgesMap}
     * contains left[right] vertexes pair.
     * @param left left vertex to check
     * @param right right vertex to check
     * @return check result
     */
    public boolean isConnected(E left, E right) {
        return (reverseEdgesMap.containsKey(new Edge<>(left, right)));
    }
}
