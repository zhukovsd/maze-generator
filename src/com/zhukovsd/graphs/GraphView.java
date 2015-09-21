package com.zhukovsd.graphs;

import java.awt.*;

/**
 * Graphic representation of graph, which can be painted of Graphic object
 * @param <T> type of Vertex of represented graph
 * @param <U> type of represented Graph
 */
public abstract class GraphView<T extends Vertex, U extends Graph<T>> {
    public final U graph;
    public final int width = 0;
    public final int height = 0;

    public GraphView(U graph) {
        this.graph = graph;
    }

    protected abstract Point calculateSize();
    protected abstract Point calculateVertexPosition(Vertex vertex);

    protected void paintVertex(Graphics graphics, T vertex) {
        Point position = calculateVertexPosition(vertex);
        graphics.drawLine(position.x, position.y, position.x, position.y);
    }

    protected void paintEdge(Graphics graphics, Edge edge) {
        Point sourcePosition = calculateVertexPosition(edge.destination);
        Point destinationPosition = calculateVertexPosition(edge.source);

        graphics.drawLine(sourcePosition.x, sourcePosition.y, destinationPosition.x, destinationPosition.y);
    }

    public void paint(Graphics graphics) {
        for (T vertex : graph.vertexList) {
            paintVertex(graphics, vertex);

            for (Edge edge : vertex.edgeList) {
                paintEdge(graphics, edge);
            }
        }
    }
}