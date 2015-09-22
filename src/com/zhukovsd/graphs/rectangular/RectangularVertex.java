package com.zhukovsd.graphs.rectangular;

import com.zhukovsd.graphs.embedded.EmbeddedVertex;

import java.awt.*;

/**
 * Created by ZhukovSD on 21.09.2015.
 */
class RectangularVertex extends EmbeddedVertex {
    private int row, column;

    public RectangularVertex(int row, int column) {
        this.row = row;
        this.column = column;
    }

    @Override
    protected Point getPosition() {
        return new Point(column, row);
    }
}
