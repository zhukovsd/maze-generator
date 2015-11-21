package com.zhukovsd.graphs.hexahedral;

import com.zhukovsd.graphs.embedded.EmbeddedGraph;
import com.zhukovsd.graphs.embedded.EmbeddedGraphView;
import com.zhukovsd.graphs.rectangular.RectangularGraph;

import java.awt.*;
import static java.lang.Math.*;

/**
 * View of graph in form of hexahedral lattice.
 */
public class HexahedralGraphView extends EmbeddedGraphView<HexahedralVertex> {
    /**
     * Minimal node spacing for hexahedral graph view.
     */
    static final int MIN_VERTEX_INTERVAL = 5;

    /**
     * Outer indent from border of the view to the most outer vertex.
     */
    private int indent;

    /**
     * Interval between adjacent vertexes.
     */
    private int vertexInterval;

    /**
     * Create hexahedral graph view for given {@link HexahedralGraph graph} and given size parameters
     * @param graph hexahedral graph
     * @param indent indent
     * @param vertexInterval vertex interval
     */
    public HexahedralGraphView(EmbeddedGraph<HexahedralVertex> graph, int indent, int vertexInterval) {
        super(graph);

        this.indent = indent;
        this.vertexInterval = vertexInterval;
    }

    /**
     * Get represented graph casted to HexahedralGraph type.
     * @return typed graph
     */
    @Override
    protected HexahedralGraph getGraph() {
        return ((HexahedralGraph) super.getGraph());
    }

    /**
     * Method for calculating size of image, representing current view.
     * @return image size as {@link Point} object
     */
    @Override
    public Point getSize() {
        int halfCount = ((getGraph().latticeColumnCount + 1) / 2);
        int wholeCount = (getGraph().latticeColumnCount / 2) - 1;

        return new Point(
                (wholeCount * vertexInterval) + (halfCount * vertexInterval / 2) + indent * 2,
                ((int) Math.round(((getGraph().latticeRowCount - 1) * vertexInterval) * sqrt(3) / 2)) + 2 * indent
        );
    }

    /**
     * Method for adjusting current view width by changing its size vertex interval.
     * @param availableWidth width to adjust to
     */
    @Override
    public void adjustSizeByWidth(int availableWidth) {
        if (getSize().x > availableWidth) {
            vertexInterval = ((int) ((availableWidth - 2 * indent) / (0.75 * (getGraph().latticeColumnCount - 1))));
            if (vertexInterval < MIN_VERTEX_INTERVAL) vertexInterval = MIN_VERTEX_INTERVAL;
        }
    }

    /**
     * Method for calculating vertex position in current view representation.
     * @param vertex vertex to calculate position of
     * @return vertex position as {@link Point} object
     */
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
