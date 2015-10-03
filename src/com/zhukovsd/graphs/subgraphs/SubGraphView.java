package com.zhukovsd.graphs.subgraphs;

import com.zhukovsd.graphs.Graph;
import com.zhukovsd.graphs.GraphView;
import com.zhukovsd.graphs.Vertex;

import java.awt.*;

/**
 * Representation of {@link SubGraph subgraph}, which can be painted on java.awt.Graphic2D object.
 * Subgraph view may only exist in context of parent graph view.
 * @param <T> type of vertex in graph, which is parent for represented {@link SubGraph subgraph}
 * @param <U> type of graph, which is parent for represented {@link SubGraph subgraph}
 * @param <V> type of graph view, which is representation for parent graph for represented {@link SubGraph subgraph}
 */
public class SubGraphView<T extends Vertex<T>, U extends Graph<T>, V extends GraphView<U, T>> extends GraphView<SubGraph<T>, SubGraphVertex<T>> {
    private final V parentView;

    /**
     * Create graph representation of given {@link SubGraph subgraph} relative to it's {@link #parentView parent view}.
     * @param graph graph to represent
     * @param parentView representation of {@link SubGraph#parentGraph parent graph}
     */
    public SubGraphView(SubGraph<T> graph, V parentView) {
        super(graph);
        this.parentView = parentView;
    }

    /**
     * Method for calculating vertex position in current view representation. Vertex position in sub graph view equals
     * to position of corresponding vertex in {@link #parentView parent graph view}.
     * @param vertex vertex to calculate position of
     * @return vertex position as {@link Point} object
     */
    @Override
    public Point calculateVertexPosition(SubGraphVertex<T> vertex) {
        return parentView.calculateVertexPosition(vertex.parentVertex);
    }

    /**
     * Method for calculating size of image, representing current view. Size of sub graph view equals
     * to size of {@link #parentView parent graph view}.
     * @return image size as {@link Point} object
     */
    @Override
    public Point getSize() {
        return parentView.getSize();
    }

}
