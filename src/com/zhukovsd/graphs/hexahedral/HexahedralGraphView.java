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
    /**
     * Outer indent from border of the view to the most outer vertex.
     */
    private int indent;

    /**
     * Interval between adjacent vertexes.
     */
    private int vertexInterval;

    public HexahedralGraphView(EmbeddedGraph<HexahedralVertex> graph, int indent, int vertexInterval) {
        super(graph);

        this.indent = indent;
        this.vertexInterval = vertexInterval;
    }

    @Override
    protected HexahedralGraph getGraph() {
        return ((HexahedralGraph) super.getGraph());
    }

    @Override
    public Point getSize() {
        int halfCount = ((getGraph().latticeColumnCount + 1) / 2);
        int wholeCount = (getGraph().latticeColumnCount / 2) - 1;

        return new Point(
                (wholeCount * vertexInterval) + (halfCount * vertexInterval / 2) + indent * 2,
                ((int) Math.round(((getGraph().latticeRowCount - 1) * vertexInterval) * sqrt(3) / 2)) + 2 * indent
        );
    }

    @Override
    public void adjustSizeByWidth(int availableWidth) {
        if (getSize().x > availableWidth) {
            vertexInterval = ((int) ((availableWidth - 2 * indent) / (0.75 * (getGraph().latticeColumnCount - 1))));
            if (vertexInterval < 4) vertexInterval = 4;
        }
    }

    @Override
    public Point calculateVertexPosition(HexahedralVertex vertex) {
        int wholeCount = (vertex.latticeColumnIndex / 2);
        int halfCount = (vertex.latticeColumnIndex + 1) / 2;

        return new Point(
                (wholeCount * vertexInterval) + (halfCount * vertexInterval / 2) + indent,
                ((int) Math.round((vertex.latticeRowIndex * vertexInterval) * sqrt(3) / 2)) + indent
        );
    }
}
