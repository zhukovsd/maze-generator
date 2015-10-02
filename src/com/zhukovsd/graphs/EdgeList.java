package com.zhukovsd.graphs;

import java.util.ArrayList;

/**
 * List of {@link Edge edges}.
 * @param <E> type of {@link Vertex vertexes} connected by edges stored in the list
 */
public class EdgeList<E extends Vertex<E>> extends ArrayList<Edge<E>> {
    /**
     * Get preceding {@link Edge edge} for given edge index. Consider current list as cycled. In this case
     * previous edge for index = 0 is the last item of the list.
     * @param index method finds edge that precedes edge by this index
     * @return found edge
     */
    public Edge<E> previous(int index) {
        if (index > 0)
            return get(index - 1);
        else
            return get(size() - 1);
    }

    /**
     * Get preceding edge for given edge. Consider current list as cycled. In this case previous edge for index = 0
     * is the last item of the list.
     * @param edge method finds edge that precedes this edge
     * @return found edge
     */
    public Edge<E> previous(Edge edge) {
        return previous(indexOf(edge));
    }
}
