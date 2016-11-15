package com.zhukovsd.graphs.circular;

import com.zhukovsd.Point;
import com.zhukovsd.graphs.embedded.EmbeddedGraph;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Circular graph, composed of concentric circles and radial "spokes".
 */
public class CircularGraph extends EmbeddedGraph<CircularVertex> {
    /**
     * Number of concentric circles in graph
     */
    int circleCount;

    /**
     * Multiplier for interval between center and 0th circle.
     */
    double centerFactor;

    /**
     * Minimal ratio or chord, connecting 2 adjacent vertexes of same circle and radius of that circle. The less factor is,
     * the denser "spokes" are arranged. Outer circles has greater radius then inner, so outer circles has more vertexes on it.
     */
    double chordFactor;

    /**
     * Get vertex count for given concentric circle index. Outer circles composed of greater vertexes number then inner.
     * Vertex count may be only power of 2, vertexes on all vertexes forms "spokes". "Spoke" connect vertexes with same
     * angular coordinate.
     * @param index given circle index
     * @return vertex count for given circle index
     */
    private int getVertexCountByCircle(int index) {
        boolean isFound = false;
        int exponent = 1;

        while (!isFound) {
            // length of chord, connecting 2 adjacent vertexes of common circle
            // length of chord is 2R*sin(a/2), where a is angle, equals to 2pi / 2^exponent
            double chordLength = 2 * (index + centerFactor) * Math.sin(Math.PI / Math.pow(2, exponent));

            if (chordLength <= chordFactor)
                isFound = true;
            else
                exponent += 1;
        }

        return (int) Math.round(Math.pow(2, exponent));
    }

    /**
     * Create circular graph, composed of concentric circles and radial "spokes", which connects vertexes with same
     * angular coordinate.
     *
     * Create circular graph, composed by given <code>circleCount</code> concentric circles, with interval from center to
     * 0th circle equals to <code>centerFactor</code> default intervals between circles, and
     * @param circleCount concentric circle count
     * @param centerFactor factor for interval from center to 0th circle (interval = centerFactor * default radius between circles)
     * @param chordFactor factor defining number of vertexes on circle by it's index (described in {@link #getVertexCountByCircle(int)}
     */
    public CircularGraph(int circleCount, double centerFactor, double chordFactor) {
        this.circleCount = circleCount;
        this.centerFactor = centerFactor;
        this.chordFactor = chordFactor;

        // list of maps, each map contains pairs of angular coordinate and vertex. Maps used to connect vertexes
        // with same angular coordinate, which lays on adjacent concentric circles
        ArrayList<LinkedHashMap<Double, CircularVertex>> lattice = new ArrayList<>();

        Point size = new Point(
                (2 * circleCount - 2) + Math.round(centerFactor * 2),
                (2 * circleCount - 2) + Math.round(centerFactor * 2)
        );

        for (int i = 0; i < circleCount; i++) {
            lattice.add(new LinkedHashMap<Double, CircularVertex>());

            int vertexCount = getVertexCountByCircle(i);
            for (int j = 0; j < vertexCount; j++) {
                double angle = 2 * Math.PI / vertexCount * j;
                CircularVertex vertex = new CircularVertex(i + centerFactor, angle, size);

                lattice.get(i).put(angle, vertex);
                addVertex(vertex);

                // connect vertex to previous circle's vertex with same angular coordinate, if it's exists
                if ((i > 0) && (lattice.get(i-1).containsKey(angle)))
                    connectToEachOther(vertex, lattice.get(i-1).get(angle));

                // connect vertex with previous vertex of same circle
                if (j > 0) connectToEachOther(vertex, lattice.get(i).get(2 * Math.PI / vertexCount * (j - 1)));

                // connect last vertex of current circle with it's first vertex
                if (j == vertexCount - 1)
                    connectToEachOther(vertex, lattice.get(i).get(new Double(0)));
            }
        }
    }
}
