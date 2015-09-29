package com.zhukovsd.graphs.dual;

import com.zhukovsd.graphs.Vertex;
import com.zhukovsd.graphs.embedded.Face;

/**
 * Vertex of dual graph.
 */
public class DualVertex extends Vertex {
    /**
     * Corresponding face of outer graph.
     */
    public final Face face;

    public DualVertex(Face face) {
        this.face = face;
    }
}
