package com.zhukovsd.graphs.rectangular;

import com.zhukovsd.Point;
import com.zhukovsd.graphs.embedded.EmbeddedVertex;

/**
 * Vertex of graph in form of rectangular lattice, which position described by row and column indexes in this lattice.
 */
public class RectangularVertex extends EmbeddedVertex<RectangularVertex> {
    /**
     * Row index of current vertex position in the rectangular lattice.
     */
    private int row;

    /**
     * Column index of current vertex position in the rectangular lattice.
     */
    private int column;

    /**
     * Create new rectangular vertex with given position in the rectangular lattice.
     * @param row row index
     * @param column column index
     */
    public RectangularVertex(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Retrieve position for current vertex in the coordinate space. Position for rectangular vertex set by it's
     * row and column indexes in rectangular lattice.
     * @return vertex position as {@link Point point} object
     */
    @Override
    public Point getPosition() {
        return new Point(column, row);
    }
}
