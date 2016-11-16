package com.zhukovsd.graphs.hexahedral;

import com.zhukovsd.Point;
import com.zhukovsd.graphs.embedded.EmbeddedVertex;

import static java.lang.Math.sqrt;

/**
 * Vertex of graph in form of hexahedral lattice, which position described by row and column indexes in this lattice.
 */
public class HexahedralVertex extends EmbeddedVertex<HexahedralVertex> {
    /**
     * Row index of current vertex position in the hexahedral lattice.
     */
    int latticeRowIndex;

    /**
     * Column index of current vertex position in the hexahedral lattice.
     */
    int latticeColumnIndex;

    /**
     * Create new hexahedral vertex with given position in the hexahedral lattice.
     * @param latticeRowIndex row index
     * @param latticeColumnIndex column index
     */
    public HexahedralVertex(int latticeRowIndex, int latticeColumnIndex) {
        this.latticeRowIndex = latticeRowIndex;
        this.latticeColumnIndex = latticeColumnIndex;
    }

    /**
     * Retrieve position for current vertex in the coordinate space. Position for hexahedral vertex set by it's
     * row and column indexes in hexahedral lattice.
     * @return vertex position as {@link Point point} object
     */
    @Override
    public Point getPosition() {
        int wholeCount = (latticeColumnIndex / 2);
        int halfCount = (latticeColumnIndex + 1) / 2;

        return new Point((wholeCount) + (((double) halfCount) / 2), latticeRowIndex * sqrt(3) / 2);
    }
}
