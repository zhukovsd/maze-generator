package com.zhukovsd.graphs.embedded;

import com.zhukovsd.graphs.GraphView;

import java.awt.Point;

/**
 * Representation of {@link EmbeddedGraph embedded graph}, which can be painted on java.awt.Graphics2D object.
 * @param <T> type of {@link EmbeddedVertex vertex} of represented {@link EmbeddedGraph embedded graph graph}
 */
public abstract class EmbeddedGraphView<T extends EmbeddedVertex<T>> extends GraphView<EmbeddedGraph<T>, T> {
    /**
     * Create graph representation of given {@link EmbeddedGraph embedded graph}.
     * @param graph embedded graph to represent
     */
    public EmbeddedGraphView(EmbeddedGraph<T> graph) {
        super(graph);
    }

    /**
     * Calculate center point for given face in coordinates of current representation.
     * @param face given face
     * @return {@link Point point} of face center
     */
    public Point calculateFaceCenter(Face<T> face) {
        Point faceCenter = new Point();

        // face center is arithmetic mean of face vertexes position's
        for (T vertex : face.vertexList) {
            Point position = calculateVertexPosition(vertex);

            faceCenter.x += position.x;
            faceCenter.y += position.y;
        }

        faceCenter.x /= face.vertexList.size();
        faceCenter.y /= face.vertexList.size();

        return faceCenter;
    }
}
