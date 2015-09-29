package com.zhukovsd.graphs.embedded;

import com.zhukovsd.graphs.GraphView;

import java.awt.Point;

/**
 * Representation of {@link EmbeddedGraph graph}, which can be painted on java.awt.Graphic object.
 * @param <T> type of {@link EmbeddedVertex vertex} of represented {@link EmbeddedGraph graph}
 */
public abstract class EmbeddedGraphView<T extends EmbeddedVertex> extends GraphView<EmbeddedGraph<T>, T> {
    /**
     * Create graph representation of given {@link EmbeddedGraph embedded graph}
     * @param graph graph to represent
     */
    public EmbeddedGraphView(EmbeddedGraph<T> graph) {
        super(graph);
    }

    /**
     * Calculate center point for given face, position is relative to current graphic representation. Used by
     * dual graph graph view
     * @param face given face
     * @return face center
     */
    public Point calculateFaceCenter(Face face) {
        Point faceCenter = new Point();

        // face center is arithmetic mean of face vertexes position's
        for (EmbeddedVertex vertex : face.vertexList) {
            Point position = calculateVertexPosition((T) vertex);

            faceCenter.x += position.x;
            faceCenter.y += position.y;
        }

        faceCenter.x /= face.vertexList.size();
        faceCenter.y /= face.vertexList.size();

        return faceCenter;
    }
}
