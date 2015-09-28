package com.zhukovsd.graphs;

import java.util.ArrayList;

/**
 * List of {@link Edge edges}
 */
public class EdgeList extends ArrayList<Edge> {
    /**
     * Get preceding edge for given edge index. Consider current list as cycled. In this case previous edge for index = 0
     * is last last of current list
     * @param index method finds edge that precedes edge by this index
     * @return found edge
     */
    public Edge previous(int index) {
        if (index > 0)
            return get(index - 1);
        else
            return get(size() - 1);
    }

    /**
     * Get preceding edge for given edge index. Consider current list as cycled. In this case previous edge for index = 0
     * is last last of current list
     * @param edge method finds edge that precedes this edge
     * @return found edge
     */
    public Edge previous(Edge edge) {
        return previous(indexOf(edge));
    }
}
