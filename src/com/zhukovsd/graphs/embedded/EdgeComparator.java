package com.zhukovsd.graphs.embedded;

import com.zhukovsd.graphs.Edge;
import java.util.Comparator;

/**
 * Comparator implementation for <code>Edge</code> class. Can be used for sorting collections of <code>Edges</code>
 */
class EdgeComparator implements Comparator<Edge> {
    @Override
    /**
     * Comparing 2 edges. Only edges of <code>EmbeddedVertex</code> class can be compared, return 0 otherwise.
     * Method expects that edges have 1 common vertex.
     */
    public int compare(Edge o1, Edge o2) {
        if ((o1.source instanceof EmbeddedVertex) && (o2.source instanceof EmbeddedVertex)) {
            // compare o1.dest and o2.dest relatively to o2.source
            // even if not o1.dest vertex is common for both edges, result will be correct
            return ((EmbeddedVertex) o1.destination).compare(
                    ((EmbeddedVertex) o2.source), ((EmbeddedVertex) o2.destination)
            );
        }

        return 0;
    }
}
