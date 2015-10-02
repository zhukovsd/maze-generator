package com.zhukovsd.graphs.subgraphs;

import com.zhukovsd.graphs.Vertex;

/**
 * Created by ZhukovSD on 30.09.2015.
 */
public class SubGraphVertex extends Vertex {
    public final Vertex parentVertex;

    public SubGraphVertex(Vertex parentVertex) {
        this.parentVertex = parentVertex;
    }
}
