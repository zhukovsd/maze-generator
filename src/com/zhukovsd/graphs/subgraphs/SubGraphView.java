package com.zhukovsd.graphs.subgraphs;

import com.zhukovsd.graphs.Graph;
import com.zhukovsd.graphs.GraphView;
import com.zhukovsd.graphs.Vertex;

import java.awt.*;

/**
 * Created by ZhukovSD on 30.09.2015.
 */
public class SubGraphView<T extends Vertex, U extends Graph<T>, V extends GraphView<U, T>> extends GraphView<SubGraph<T>, SubGraphVertex> {
    private final V parentView;

    /**
     *
     */
    public SubGraphView(SubGraph<T> graph, V parentView) {
        super(graph);
        this.parentView = parentView;
    }

    @Override
    public Point calculateVertexPosition(SubGraphVertex vertex) {
        return parentView.calculateVertexPosition(((T) vertex.parentVertex));
    }

    @Override
    public Point getSize() {
        return parentView.getSize();
    }

}
