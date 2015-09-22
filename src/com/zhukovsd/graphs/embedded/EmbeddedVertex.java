package com.zhukovsd.graphs.embedded;

import com.zhukovsd.graphs.Vertex;
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
}
