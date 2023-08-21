package com.zhukovsd.generator;

import java.awt.*;
import java.util.EnumSet;
import java.util.HashMap;

/**
 * General maze which may be drawn.
 */
public abstract class DrawableMaze {
    /**
     * Options for painting maze, list of colors and strokes for each graph of the maze.
     */
    class MazePaintOptions {
        /**
         * Options for painting particular graph.
         */
        class GraphPaintOptions {
            /**
             * Color for painting corresponding graph.
             */
            Color color;

            /**
             * Stroke for painting corresponding graph.
             */
            BasicStroke stroke;

            public GraphPaintOptions(Color color, BasicStroke stroke) {
                this.color = color;
                this.stroke = stroke;
            }
        }

        /**
         * Map, which consists of pairs of graph kind and options for its painting.
         */
        private HashMap<MazeGraphKind, GraphPaintOptions> graphPaintOptionsMap = new HashMap<>();

        {
            // initialize default paint options for all graphs
            graphPaintOptionsMap.put(MazeGraphKind.INITIAL_GRAPH, new GraphPaintOptions(Color.BLACK, new BasicStroke(1)));
            graphPaintOptionsMap.put(MazeGraphKind.DUAL_GRAPH, new GraphPaintOptions(Color.RED,
                    new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{1, 3}, 0)));
            graphPaintOptionsMap.put(MazeGraphKind.PATH_SPANNING_TREE, new GraphPaintOptions(Color.BLUE, new BasicStroke(1)));
            graphPaintOptionsMap.put(MazeGraphKind.SHORTEST_PATH, new GraphPaintOptions(new Color(0, 200, 0), new BasicStroke(1)));
            graphPaintOptionsMap.put(MazeGraphKind.RESULTING_GRAPH, new GraphPaintOptions(Color.BLACK, new BasicStroke(1)));
        }

        /**
         * Get {@link GraphPaintOptions graphic options} for particular {@link MazeGraphKind graph kind}.
         * @param key given graph kind
         * @return found graphic options
         */
        public GraphPaintOptions get(MazeGraphKind key) {
            return graphPaintOptionsMap.get(key);
        }
    }

    /**
     * Paint options for current maze.
     */
    public final MazePaintOptions mazePaintOptions = new MazePaintOptions();

    /**
     * Size of graphic representation of current maze.
     * @return size
     */
    public abstract Point getSize();

    /**
     * Abstract method for maze painting.
     * @param graphics graphics options to paint on
     * @param graphs set of kinds of graphs to be painted
     */
    public abstract void paint(Graphics2D graphics, EnumSet<MazeGraphKind> graphs);

    /**
     * Abstract method for adjusting size of graphic representation for current maze.
     * @param availableWidth width to adjust to
     */
    public abstract void adjustSizeByWidth(int availableWidth);

    /**
     * Abstract method for actual maze generation.
     */
    public abstract void generate();
}
