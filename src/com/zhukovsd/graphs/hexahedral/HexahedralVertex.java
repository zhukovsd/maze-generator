package com.zhukovsd.graphs.hexahedral;

import com.zhukovsd.graphs.embedded.EmbeddedVertex;

import java.awt.geom.Point2D;

/**
 * Created by ZhukovSD on 15.11.2015.
 */
public class HexahedralVertex extends EmbeddedVertex<HexahedralVertex> {
    /**
     *
     */
    int latticeRowIndex;

    /**
     *
     */
    int latticeColumnIndex;

    /**
     *
     * @param latticeRowIndex
     * @param latticeColumnIndex
     */
    public HexahedralVertex(int latticeRowIndex, int latticeColumnIndex) {
        this.latticeRowIndex = latticeRowIndex;
        this.latticeColumnIndex = latticeColumnIndex;
    }

    /**
     *
     * @return
     */
    @Override
    public Point2D.Double getPosition() {
        return new Point2D.Double(latticeRowIndex, latticeColumnIndex);
    }
}
