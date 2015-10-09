package com.zhukovsd.graphs.circular;

import com.zhukovsd.graphs.embedded.EmbeddedVertex;

import java.awt.geom.Point2D;

/**
 *  Vertex of graph in form of circular lattice, which position described by polar coordinates.
 */
public class CircularVertex extends EmbeddedVertex<CircularVertex> {
    public double r, q;

    public CircularVertex(double r, double q) {
        this.r = r;
        this.q = q;
    }

    /**
     * Retrieve position for current vertex in the coordinate space. Method converts polar coordinates to cartesian.
     * @return vertex position as {@link Point2D.Double point} object
     */
    @Override
    public Point2D.Double getPosition() {
        return new Point2D.Double(r * Math.cos(q), r * Math.sin(q));
    }
}
