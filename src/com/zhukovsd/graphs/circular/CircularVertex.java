package com.zhukovsd.graphs.circular;

import com.zhukovsd.Point;
import com.zhukovsd.graphs.embedded.EmbeddedVertex;

/**
 *  Vertex of graph in form of circular lattice, which position described by polar coordinates.
 */
public class CircularVertex extends EmbeddedVertex<CircularVertex> {
    /**
     * Polar coordinate of current vertex, describing its distance from center.
     */
    public double r;

    /**
     * Polar coordinate of current vertex, describing its angle from reference direction.
     */
    public double q;

    /**
     * Create circular vertex with given polar coordinates.
     * @param r {@link #r}
     * @param q {@link #q}
     */
    public CircularVertex(double r, double q) {
        this.r = r;
        this.q = q;
    }

    /**
     * Retrieve position for current vertex in the coordinate space. Method converts polar coordinates to cartesian.
     * @return vertex position as {@link Point point} object
     */
    @Override
    public Point getPosition() {
        return new Point(r * Math.cos(q), r * Math.sin(q));
    }
}
