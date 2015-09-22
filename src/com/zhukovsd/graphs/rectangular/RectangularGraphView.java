package com.zhukovsd.graphs.rectangular;

import com.zhukovsd.graphs.Vertex;
import com.zhukovsd.graphs.embedded.EmbeddedGraph;
import com.zhukovsd.graphs.embedded.EmbeddedGraphView;

import java.awt.*;

/**
 * Created by ZhukovSD on 22.09.2015.
 */
public class RectangularGraphView extends EmbeddedGraphView<RectangularVertex> {
    private int indent, nodeSpacing;

    public RectangularGraphView(EmbeddedGraph<RectangularVertex> graph, int indent, int nodeSpacing) {
        super(graph);

        this.indent = indent;
        this.nodeSpacing = nodeSpacing;
    }

//    @Override
//    protected RectangularGraph getGraph() {
//        return ((RectangularGraph) super.getGraph());
//    }

    @Override
    public Point getSize() {
//        return new Point(
//                2 * indent + nodeSpacing * (getGraph().columnCount), 2 * indent + nodeSpacing * (getGraph().rowCount)
//        );
        return null;
    }

    @Override
    protected Point calculateVertexPosition(Vertex vertex) {
        return null;
    }
}
