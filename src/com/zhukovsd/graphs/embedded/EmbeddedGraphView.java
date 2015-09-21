package com.zhukovsd.graphs.embedded;

import com.zhukovsd.graphs.GraphView;

/**
 * Representation of {@link EmbeddedGraph graph}, which can be painted on java.awt.Graphic object.
 * @param <T> type of {@link EmbeddedVertex vertex} of represented {@link EmbeddedGraph graph}
 */
public abstract class EmbeddedGraphView<T extends EmbeddedVertex> extends GraphView<EmbeddedGraph<T>, T> {
    public EmbeddedGraphView(EmbeddedGraph<T> graph) {
        super(graph);
    }
}
