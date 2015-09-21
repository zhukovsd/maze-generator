package com.zhukovsd.graphs;

import java.awt.*;

/**
 * Representation of {@link Graph graph}, which can be painted on java.awt.Graphic object.
 * @param <T> type of represented {@link Graph graph}
 * @param <U> type of {@link Vertex vertex} of represented {@link Graph graph}
 */
public abstract class GraphView<T extends Graph<U>, U extends Vertex> {
    /**
     * {@link Graph Graph} object, represented by current view. Immutable.
     */
    public final T graph;

    /**
     * Image width required to represent current view
     */
    public final int width = 0;

    /**
     * Image height required to represent current view
     */
    public final int height = 0;

    public GraphView(T graph) {
        this.graph = graph;
    }

    /**
     * Abstract method for calculating size of image, representing current view. Must be overrode in derived class
     * @return image size as {@link Point} object
     */
    protected abstract Point calculateSize();

    /**
     * Abstract method for calculating vertex position in current view representation. Must be overrode in derived class
     * @param vertex vertex to calculate position of
     * @return vertex position as {@link Point} object
     */
    protected abstract Point calculateVertexPosition(Vertex vertex);

    /**
     * Paint given vertex on given {@link Graphics graphics} object
     * @param graphics {@link Graphics graphics} object to paint on
     */
    protected void paintVertex(Graphics graphics, U vertex) {
        Point position = calculateVertexPosition(vertex);
        graphics.drawLine(position.x, position.y, position.x, position.y);
    }

    /**
     * Paint given link on given {@link Graphics graphics} object
     * @param graphics {@link Graphics graphics} object to paint on
     */
    protected void paintEdge(Graphics graphics, Edge edge) {
        Point sourcePosition = calculateVertexPosition(edge.destination);
        Point destinationPosition = calculateVertexPosition(edge.source);

        graphics.drawLine(sourcePosition.x, sourcePosition.y, destinationPosition.x, destinationPosition.y);
    }

    /**
     * Paint whole view on given {@link Graphics graphics} object
     * @param graphics {@link Graphics graphics} object to paint on
     */
    public void paint(Graphics graphics) {
        for (U vertex : graph.vertexList) {
            paintVertex(graphics, vertex);

            for (Edge edge : vertex.edgeList) {
                paintEdge(graphics, edge);
            }
        }
    }
}