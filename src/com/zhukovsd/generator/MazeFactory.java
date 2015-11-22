package com.zhukovsd.generator;

import com.zhukovsd.graphs.circular.*;
import com.zhukovsd.graphs.hexahedral.*;
import com.zhukovsd.graphs.rectangular.*;

/**
 * Factory for mazes, use for creating any possible maze with given params.
 */
public class MazeFactory {
    /**
     * Create rectangular maze.
     * @param rowCount row count
     * @param columnCount column count
     * @param viewBorderIndent outer indent for view
     * @param viewVertexSpacing indent between vertexes in view
     * @return created maze
     */
    public static Maze<RectangularVertex> createRectangularMaze(
            int rowCount, int columnCount, int viewBorderIndent, int viewVertexSpacing
    ) {
        RectangularGraph graph = new RectangularGraph(rowCount, columnCount);
        RectangularGraphView view = new RectangularGraphView(graph, viewBorderIndent, viewVertexSpacing);

        Maze<RectangularVertex> result = new Maze<>(graph, view);

        result.entryVertex = result.dualGraph.vertexList.get((graph.columnCount - 1) / 2);
        result.exitVertex = result.dualGraph.vertexList.get(result.dualGraph.vertexList.size() - 1 - (graph.columnCount - 1) / 2);

        return result;
    }

    /**
     * Create circular maze.
     * @param circleCount circle count
     * @param centerFactor center factor
     * @param chordFactor chord factor
     * @param viewBorderIndent outer intent for view
     * @param viewRadialInterval intent between circles in view
     * @return created maze
     */
    public static Maze<CircularVertex> createCircularMaze(
            int circleCount, double centerFactor, double chordFactor, int viewBorderIndent, int viewRadialInterval
    ) {
        CircularGraph graph = new CircularGraph(circleCount, centerFactor, chordFactor);
        CircularGraphView view = new CircularGraphView(graph, viewBorderIndent, viewRadialInterval);

        Maze<CircularVertex> result = new Maze<>(graph, view);
        result.mazeGenerationOptions.pathTreeExcludedVertexes.add(result.dualGraph.vertexList.get(1));

        return result;
    }

    /**
     * Create hexahedral maze.
     * @param rowCount row count of hexahedral maze lattice
     * @param columnCount column count of hexahedral maze lattice
     * @param viewBorderIndent outer indent for view
     * @param viewVertexSpacing indent between vertexes in view
     * @return created maze
     */
    public static Maze<HexahedralVertex> createHexahedralMaze(
            int rowCount, int columnCount, int viewBorderIndent, int viewVertexSpacing
    ) {
        HexahedralGraph graph = new HexahedralGraph(rowCount, columnCount);
        HexahedralGraphView view = new HexahedralGraphView(graph, viewBorderIndent, viewVertexSpacing);

        return new Maze<>(graph, view);
    }
}
