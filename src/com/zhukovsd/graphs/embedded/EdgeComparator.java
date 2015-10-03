package com.zhukovsd.graphs.embedded;

import com.zhukovsd.graphs.Edge;

import java.util.Comparator;

/**
 * Comparator implementation for {@link Edge edge} objects, which connects {@link EmbeddedVertex embedded vertexes},
 * used to sort collections of edges in counterclockwise order.
 * @param <E> type parameter of edges to compare
 */
class EdgeComparator<E extends EmbeddedVertex<E>> implements Comparator<Edge<E>> {
    /**
     * Compare 2 edges. Method expects that edges have 1 common vertex.
     */
    @Override
    public int compare(Edge<E> o1, Edge<E> o2) {
        return o1.destination.compare(o2.source, o2.destination);
    }
}
