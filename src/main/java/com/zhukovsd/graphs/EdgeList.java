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

    /**
     * Get succeeding {@link Edge edge} for given edge index. Consider current list as cycled. In this case
     * next edge for index = 0 is the last item of the list.
     * @param index method finds edge that precedes edge by this index
     * @return found edge
     */
    public Edge<E> next(int index) {
        if (index < size() - 1)
            return get(index + 1);
        else
            return get(0);
    }

    /**
     * Get succeeding edge for given edge. Consider current list as cycled. In this case next edge for index = 0
     * is the last item of the list.
     * @param edge method finds edge that precedes this edge
     * @return found edge
     */
    public Edge<E> next(Edge edge) {
        return next(indexOf(edge));
    }

    /**
     * Check if current list consists exactly of the same edges (ignoring order) as given list.
     * @param list given list to check
     * @return checking result
     */
    public boolean isSameListOfUnidirectionalEdges(EdgeList<E> list) {
        return (this.containsAll(list) && (list.containsAll(this)));
    }
}
