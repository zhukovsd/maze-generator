package com.zhukovsd.graphs.embedded;

import com.zhukovsd.graphs.*;

import java.awt.geom.Point2D;

/**
 * Abstract graph vertex, embedded to the coordinate space.
 */
public abstract class EmbeddedVertex extends Vertex {
    /**
     * Retrieve position for current vertex in the coordinate space
     * @return vertex position as {@link Point2D.Double point} object
     */
    protected abstract Point2D.Double getPosition();

    /**
     * Compare 2 vertexes positions relatively to <code>center</code> vertex position. If in current vertex ->right vertex arc
     * right vertex closer to current vertex in clockwise direction, return -1, if closer in counterclockwise direction,
     * return 1, return 0 if equals
     * @param center center node, current and right vertexes will be compares relatively to it
     * @param right right node to compare
     * @return comparison result
     */
    public int compare(EmbeddedVertex center, EmbeddedVertex right) {
        Point2D.Double a = getPosition();
        Point2D.Double b = right.getPosition();
        Point2D.Double c = center.getPosition();

        double r = (a.x - c.x) * (b.y - c.y) - (b.x - c.x) * (a.y - c.y);

        if (r > 0) return 1;
        else if (r < 0) return -1;
        else return 0;
    }
}

