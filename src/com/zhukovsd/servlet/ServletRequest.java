package com.zhukovsd.servlet;

import com.google.gson.Gson;
import com.zhukovsd.generator.MazeGeometry;
import com.zhukovsd.generator.MazeGraphKind;

import java.util.ArrayList;
import java.util.EnumSet;

/**
 * Class, which represents deserialized data from get request.
 */
public class ServletRequest {
    /**
     * Minimal correct requested row count for rectangular maze.
     */
    static final int MIN_RECT_ROW_COUNT = 3;

    /**
     * Maximal correct requested row count for rectangular maze.
     */
    static final int MAX_RECT_ROW_COUNT = 300;

    /**
     * Minimal correct requested column count for rectangular maze.
     */
    static final int MIN_RECT_COLUMN_COUNT = 3;

    /**
     * Maximal correct requested column count for rectangular maze.
     */
    static final int MAX_RECT_COLUMN_COUNT = 300;

    /**
     * Minimal correct circle count for circular maze.
     */
    static final int MIN_CIRC_CIRCLE_COUNT = 2;

    /**
     * Maximal correct circle count for circular maze.
     */
    static final int MAX_CIRC_CIRCLE_COUNT = 150;

    /**
     * Inner class, describing requested maze size.
     */
    class RequestMazeSize {
        /**
         * Row count of rectangular maze.
         */
        int rowCount;

        /**
         * Column count of rectangular maze.
         */
        int columnCount;

        /**
         * Circle count of circular maze.
         */
        int circleCount;
    }

    /**
     * Maze geometry. Integer value is an index of {@link MazeGeometry} enum type.
     */
    private int geometry;

    /**
     * Requested graph kinds. Integer values are indexes of {@link MazeGraphKind} enum type.
     */
    @SuppressWarnings("MismatchedReadAndWriteOfArray")
    private int[] views;

    /**
     * Maze size object.
     */
    RequestMazeSize size;

    /**
     * Width, available for maze displaying on client-size. Width of maze-view should be equal or less than this value,
     * if possible.
     */
    int availableWidth;

    /**
     * Get typed maze geometry value.
     * @return maze geometry
     */
    MazeGeometry getGeometry() {
        return MazeGeometry.values()[geometry];
    }

    /**
     * Get typed graph kinds.
     * @return graph kinds
     */
    EnumSet<MazeGraphKind> getViews() {
        ArrayList<MazeGraphKind> kindArray = new ArrayList<>();

        for (int enumIndex : views) {
            kindArray.add(MazeGraphKind.values()[enumIndex]);
        }

        return EnumSet.copyOf(kindArray);
    }

    /**
     * Check is requested maze size are correct and lays inside server constraints, presented in constants of current class.
     * @return checking result
     */
    boolean isSizeCorrect() {
        if (size == null)
            return false;

        // switch !
        if (getGeometry() == MazeGeometry.RECTANGULAR) {
            //noinspection RedundantIfStatement
            if ((size.rowCount < MIN_RECT_ROW_COUNT) || (size.rowCount > MAX_RECT_ROW_COUNT))
                return false;

            //noinspection RedundantIfStatement
            if ((size.columnCount < MIN_RECT_COLUMN_COUNT) || (size.columnCount > MAX_RECT_COLUMN_COUNT))
                return false;

            return true;
        } else if (getGeometry() == MazeGeometry.CIRCULAR) {
            //noinspection RedundantIfStatement
            if ((size.circleCount < MIN_CIRC_CIRCLE_COUNT) || (size.circleCount > MAX_CIRC_CIRCLE_COUNT))
                return false;

            return true;
        } else {
            return true;
        }
    }

    /**
     * Deserialize class instance from string.
     * @param json URL-decoded string
     * @return deserialized class instance
     */
    static ServletRequest createFromJSON(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, ServletRequest.class);
    }
}
