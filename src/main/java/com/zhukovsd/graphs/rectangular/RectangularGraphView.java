package com.zhukovsd.graphs.rectangular;

import com.zhukovsd.graphs.embedded.EmbeddedGraph;
import com.zhukovsd.graphs.embedded.EmbeddedGraphView;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * View of graph in form of rectangular lattice.
 */
public class RectangularGraphView extends EmbeddedGraphView<RectangularVertex> {
    /**
     * Minimal node spacing for rectangular graph view.
     */
    static final int MIN_VERTEX_INTERVAL = 4;

    /**
     * Outer indent from border of the view to the most outer vertex.
     */
    private int indent;

    /**
     * Interval between adjacent vertexes.
     */
    private int vertexInterval;

    /**
     * Create rectangular graph view for given {@link RectangularGraph graph} and given size parameters
     * @param graph rectangular graph.
     * @param indent indent
     * @param vertexInterval vertex interval
     */
    public RectangularGraphView(EmbeddedGraph<RectangularVertex> graph, int indent, int vertexInterval) {
        super(graph);

        this.indent = indent;
        this.vertexInterval = vertexInterval;
    }

    /**
     * Get represented graph casted to RectangularGraph type.
     * @return typed graph
     */
    @Override
    protected RectangularGraph getGraph() {
        return ((RectangularGraph) super.getGraph());
    }

    /**
     * Method for calculating size of image, representing current view.
     * @return image size as {@link Point} object
     */
    @Override
    public Point getSize() {
        return new Point(
                2 * indent + vertexInterval * (getGraph().columnCount - 1),
                2 * indent + vertexInterval * (getGraph().rowCount - 1)
        );
    }

    /**
     * Abstract method for adjusting current view width by changing its size vertex interval.
     * @param availableWidth width to adjust to
     */
    @Override
    public void adjustSizeByWidth(int availableWidth) {
        if (getSize().x > availableWidth) {
            vertexInterval = (availableWidth - indent * 2 ) / (getGraph().columnCount - 1);

            if (vertexInterval < MIN_VERTEX_INTERVAL) vertexInterval = MIN_VERTEX_INTERVAL;
        }
    }

    /**
     * Abstract method for calculating vertex position in current view representation. Must be overrode in derived class.
     * @param vertex vertex to calculate position of
     * @return vertex position as {@link Point} object
     */
    @Override
    public Point calculateVertexPosition(RectangularVertex vertex) {
        Point2D.Double position = vertex.getPosition();

        return new Point(
                indent + (int) Math.round(position.x * vertexInterval), indent + (int) Math.round(position.y * vertexInterval)
        );
    }
}
