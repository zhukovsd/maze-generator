package com.zhukovsd.graphs.embedded;

import com.zhukovsd.Point;
import com.zhukovsd.graphs.*;

/**
 * Abstract graph vertex, embedded to the coordinate space.
 * @param <T> - self-referential type used as type parameter for {@link #edgeList}
 */
public abstract class EmbeddedVertex<T extends EmbeddedVertex<T>> extends Vertex<T> {
    /**
     * Retrieve position for current vertex in the coordinate space.
     * @return vertex position as {@link Point point} object
     */
    public abstract Point getPosition();

    /**
     * Compare 2 vertexes positions relatively to <code>center</code> vertex position.
     * Consider <i>current vertex-right vertex</i> arc with center in <code>center</code> vertex.
     * If in this arc right vertex closer to current vertex in clockwise direction, return -1,
     * if closer in counterclockwise direction, return 1, return 0 if equals.
     * <a href="http://stackoverflow.com/questions/6989100/sort-points-in-clockwise-order">Algorithm source</a>.
     * @param center center node, current and right vertexes will be compares relatively to it
     * @param right right node to compare
     * @return comparison result
     */
    public int compare(T center, T right) {
        Point a = getPosition();
        Point b = right.getPosition();
        Point c = center.getPosition();

        double r = (a.x - c.x) * (b.y - c.y) - (b.x - c.x) * (a.y - c.y);

        if (r > 0) return 1;
        else if (r < 0) return -1;
        else return 0;
    }
}