package com.zhukovsd.graphs.hexahedral;

import com.zhukovsd.graphs.embedded.EmbeddedGraph;
import com.zhukovsd.graphs.embedded.EmbeddedGraphView;
import com.zhukovsd.graphs.rectangular.RectangularGraph;

import java.awt.*;
import static java.lang.Math.*;

/**
 * Created by ZhukovSD on 15.11.2015.
 */
public class HexahedralGraphView extends EmbeddedGraphView<HexahedralVertex> {
    public HexahedralGraphView(EmbeddedGraph<HexahedralVertex> graph) {
        super(graph);
    }

    @Override
    protected HexahedralGraph getGraph() {
        return ((HexahedralGraph) super.getGraph());
    }

    @Override
    public Point getSize() {
        // 380 = (10 - 1) * 40 + 10 * 2 = 380

        int halfCount = ((getGraph().latticeColumnCount + 1) / 2);
        int wholeCount = (getGraph().latticeColumnCount / 2) - 1;

        return new Point(
                (wholeCount * 40) + (halfCount * 20) + 10 * 2,
                ((int) Math.round((getGraph().latticeRowCount * 40) * sqrt(3) / 2)) + 10
        );
    }

    @Override
    public Point calculateVertexPosition(HexahedralVertex vertex) {
        int halfCount = ((vertex.latticeColumnIndex + 1) / 2);
        int wholeCount = (vertex.latticeColumnIndex / 2);

//        System.out.println("column = " + vertex.latticeColumnIndex + ", halfCount = " + halfCount);

        return new Point(
//                ((int) Math.round((vertex.latticeColumnIndex * 40) * sqrt(3) / 2)),
//                vertex.latticeColumnIndex * 40 + 10,
                (wholeCount * 40) + (halfCount * 20) + 10,
                ((int) Math.round((vertex.latticeRowIndex * 40) * sqrt(3) / 2)) + 10
        );
    }

    @Override
    public void adjustSizeByWidth(int availableWidth) {

    }
}
