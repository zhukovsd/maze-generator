package com.zhukovsd.graphs.dual;

import com.zhukovsd.graphs.Edge;
import com.zhukovsd.graphs.GraphView;
import com.zhukovsd.graphs.embedded.EmbeddedGraphView;
import com.zhukovsd.graphs.embedded.EmbeddedVertex;

import java.awt.*;

/**
 * Representation of {@link DualGraph dual graph}, which can be painted on java.awt.Graphic2D object.
 * Dual graph view may only exist in context of outer graph view.
 * @param <T> type of vertex in outer graph for represented {@link DualGraph dual graph}
 */
public class DualGraphView<T extends EmbeddedVertex<T>> extends GraphView<DualGraph<T>, DualVertex<T>> {
    private final EmbeddedGraphView<T> outerView;

    /**
     * Create graph representation of given {@link DualGraph dual graph}
     * @param graph graph to represent
     */
    public DualGraphView(DualGraph<T> graph, EmbeddedGraphView<T> outerView) {
        super(graph);

        this.outerView = outerView;
    }

    /**
     * Method for calculating size of image, representing current view. Size of dual graph view equals
     * to size of outer graph view
     * @return image size as {@link Point} object
     */
    @Override
    public Point getSize() {
        return outerView.getSize();
    }

    /**
     * Method for calculating vertex position in current view representation. Vertex position in dual graph view equals
     * to center of corresponding face of outer graph view
     * @param vertex vertex to calculate position of
     * @return vertex position as {@link Point} object
     */
    @Override
    public Point calculateVertexPosition(DualVertex<T> vertex) {
        return outerView.calculateFaceCenter(vertex.face);
    }
}
