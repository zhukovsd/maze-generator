package com.zhukovsd.generator.customized;

import com.zhukovsd.generator.MazeGeometry;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZhukovSD on 19.12.2016.
 */
public class CustomizedMazeParameters {
    public static final String WIDTH = "width";
    public static final String HEIGHT = "height";

    public final MazeGeometry kind;
    private final Map<String, Integer> params = new HashMap<>();

    public final double desiredPathRatio, pathRatioEpsilon;

    public CustomizedMazeParameters(MazeGeometry kind, double desiredPathRatio, double pathRatioEpsilon) {
        this.kind = kind;
        this.desiredPathRatio = desiredPathRatio;
        this.pathRatioEpsilon = pathRatioEpsilon;
    }

    public Integer sizeParam(String name) {
        return params.get(name);
    }

    public static CustomizedMazeParameters makeRectangularMazeParams(
            int width, int height, double desiredPathRatio, double pathRatioEpsilon
    ) {
        CustomizedMazeParameters result = new CustomizedMazeParameters(MazeGeometry.RECTANGULAR, desiredPathRatio,
                pathRatioEpsilon);

        result.params.put(WIDTH, width);
        result.params.put(HEIGHT, width);

        return result;
    }
}