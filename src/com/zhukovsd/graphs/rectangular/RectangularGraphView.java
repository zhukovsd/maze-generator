package com.zhukovsd.graphs.rectangular;

import com.zhukovsd.graphs.embedded.EmbeddedGraph;
import com.zhukovsd.graphs.embedded.EmbeddedGraphView;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * View of graph in form of rectangular lattice.
 */
public class RectangularGraphView extends EmbeddedGraphView<RectangularVertex> {
    private int indent, nodeSpacing;

    public RectangularGraphView(EmbeddedGraph<RectangularVertex> graph, int indent, int nodeSpacing) {
        super(graph);

        this.indent = indent;
        this.nodeSpacing = nodeSpacing;
    }

    @Override
    protected RectangularGraph getGraph() {
        return ((RectangularGraph) super.getGraph());
    }

    @Override
    public Point getSize() {
        return new Point(
                2 * indent + nodeSpacing * (getGraph().columnCount - 1),
                2 * indent + nodeSpacing * (getGraph().rowCount - 1)
        );
    }

    @Override
    protected Point calculateVertexPosition(RectangularVertex vertex) {
        Point2D.Double position = vertex.getPosition();

        return new Point(
                indent + (int) Math.round(position.x * nodeSpacing), indent + (int) Math.round(position.y * nodeSpacing)
        );
    }
}
