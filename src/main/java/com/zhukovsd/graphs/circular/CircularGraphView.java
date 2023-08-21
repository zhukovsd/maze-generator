package com.zhukovsd.graphs.circular;

import com.zhukovsd.graphs.embedded.EmbeddedGraph;
import com.zhukovsd.graphs.embedded.EmbeddedGraphView;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * View of {@link CircularGraph graph} in form of circular lattice.
 */
public class CircularGraphView extends EmbeddedGraphView<CircularVertex> {
    /**
     * Minimal radial interval within the circular graph view.
     */
    static final int MIN_RADIAL_INTERVAL = 4;

    /**
     * Padding from image borders to outermost vertexes.
     */
    int borderIndent;

    /**
     * Interval between concentric circles of graph.
     */
    int radialInterval;

    /**
     * Create circular graph view for given {@link CircularGraph graph} with given size parameters.
     * @param graph circular graph.
     * @param borderIndent {@link #borderIndent}
     * @param radialInterval {@link #radialInterval}
     */
    public CircularGraphView(EmbeddedGraph<CircularVertex> graph, int borderIndent, int radialInterval) {
        super(graph);

        this.borderIndent = borderIndent;
        this.radialInterval = radialInterval;
    }

    /**
     * Get represented graph casted to Circular type.
     * @return typed graph
     */
    @Override
    protected CircularGraph getGraph() {
        return ((CircularGraph) super.getGraph());
    }

    /**
     * Calculating size of image, representing current graph view.
     * @return image size as {@link Point} object
     */
    @Override
    public Point getSize() {
        return new Point(
                borderIndent * 2 + radialInterval * (2 * getGraph().circleCount - 2) + ((int) Math.round(radialInterval * getGraph().centerFactor)) * 2,
                borderIndent * 2 + radialInterval * (2 * getGraph().circleCount - 2) + ((int) Math.round(radialInterval * getGraph().centerFactor)) * 2
        );
    }

    /**
     * Method for adjusting current view width by changing its {@link #radialInterval}.
     * @param availableWidth width to adjust to
     */
    @Override
    public void adjustSizeByWidth(int availableWidth) {
        if (getSize().x > availableWidth) {
            radialInterval = (int) ((availableWidth - 2 * borderIndent) / (2 * getGraph().circleCount - 2 + 2 * getGraph().centerFactor));

            if (radialInterval < MIN_RADIAL_INTERVAL) radialInterval = MIN_RADIAL_INTERVAL;
        }
    }

    /**
     * Calculate position on vertex within current graph view.
     * @param vertex vertex to calculate position of
     * @return vertex position as {@link Point} object
     */
    @Override
    public Point calculateVertexPosition(CircularVertex vertex) {
        Point2D.Double position = vertex.getPosition();

        return new Point(
                getSize().x / 2 + (int) Math.round(position.x * radialInterval),
                getSize().y / 2 + (int) Math.round(position.y * radialInterval)
        );
    }
}
