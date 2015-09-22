package com.zhukovsd.graphs.rectangular;

import com.zhukovsd.graphs.embedded.EmbeddedGraph;

/**
 * Created by ZhukovSD on 21.09.2015.
 */
public class RectangularGraph extends EmbeddedGraph<RectangularVertex> {
    public final int rowCount;
    public final int columnCount;

    public RectangularGraph(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;

        RectangularVertex[][] lattice = new RectangularVertex[rowCount][columnCount];

        for (int i = 0; i < rowCount - 1; i++) {
            for (int j = 0; j < columnCount; j++) {
                vertexList.add(new RectangularVertex(i, j));
            }
        }
    }
}
