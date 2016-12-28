package com.zhukovsd.generator.customized;

import com.zhukovsd.generator.Maze;
import com.zhukovsd.generator.MazeFactory;
import com.zhukovsd.generator.MazeGraphKind;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Locale;

/**
 * Created by ZhukovSD on 19.12.2016.
 */
public class CustomizedMazeGenerator {
    private final CustomizedMazeParameters params;
    private final String title;

    public Maze<?> maze = null;

    public CustomizedMazeGenerator(CustomizedMazeParameters params, String title) {
        this.params = params;
        this.title = title;
    }

    private Maze<?> createEmptyMaze() {
//        switch (params.kind) {
//            case RECTANGULAR:
                return MazeFactory.createRectangularMaze(
                    params.sizeParam(CustomizedMazeParameters.WIDTH),
                    params.sizeParam(CustomizedMazeParameters.HEIGHT),
                    10, 20
                );

//            default:
//                return null;
//        }
    }

    public CustomizedMazeGenerator generate() {
        boolean mazeFound = false;

        double highestRatio = 0, pathRatio = 0;
        int attemptsCount = 0;

        while (!mazeFound) {
            maze = createEmptyMaze();
            maze.generate();

            pathRatio = ((double) maze.shortestPath.getEdgeList().size()) / ((double) maze.pathSpanningTree.getEdgeList().size());
            highestRatio = Math.max(pathRatio, highestRatio);

            double desiredRatio = this.params.desiredPathRatio;
            double ratioEpsilon = this.params.pathRatioEpsilon;

            mazeFound = (pathRatio > desiredRatio - ratioEpsilon) && (pathRatio < desiredRatio + ratioEpsilon);

            attemptsCount++;

            if (attemptsCount % 500 == 0) {
                System.out.println("count = " + attemptsCount + ", highestRatio = " + highestRatio);
            }
        }

        System.out.println("pathRatio = " + pathRatio + ", count = " + attemptsCount);

        return this;
    }

    public static BufferedImage expandToTop(BufferedImage img, int newW, int newH) {
        BufferedImage result = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = result.createGraphics();
        g.setBackground(new Color(255, 255, 255, 255));
        g.clearRect(0, 0, newW, newH);
        g.drawImage(img, 0, newH - img.getHeight(), img.getWidth(), img.getHeight(), null);

        g.dispose();

        return result;
    }

    public BufferedImage render() {
        Point size = maze.getSize();

        BufferedImage image = new BufferedImage(size.x, size.y, BufferedImage.TYPE_INT_ARGB);

        Graphics2D graphics = image.createGraphics();
        graphics.setBackground(new Color(255, 255, 255, 255));
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        graphics.clearRect(0, 0, size.x, size.y);
        maze.paint(graphics, EnumSet.of(MazeGraphKind.RESULTING_GRAPH, MazeGraphKind.SHORTEST_PATH));

        graphics.dispose();

        image = expandToTop(image, size.x, size.y + 30);
        graphics = image.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("Courier New", Font.PLAIN, 20));

        graphics.drawString(String.format(
            new Locale("en", "US"),
            "maze %s, %dx%d, %.2f" + Character.toString((char)177) + "%.3f", this.title,
            params.sizeParam(CustomizedMazeParameters.WIDTH),
            params.sizeParam(CustomizedMazeParameters.HEIGHT),
            params.desiredPathRatio, params.pathRatioEpsilon
        ), 10, 25);

        return image;
    }

    public static void main(String[] args) throws IOException {
        ArrayList<CustomizedMazeParameters> tasks = new ArrayList<>(Arrays.asList(
                CustomizedMazeParameters.makeRectangularMazeParams(20, 20, 0.21, 0.01), // 1
                CustomizedMazeParameters.makeRectangularMazeParams(21, 21, 0.22, 0.01), // 2
                CustomizedMazeParameters.makeRectangularMazeParams(22, 22, 0.23, 0.01), // 3
                CustomizedMazeParameters.makeRectangularMazeParams(23, 23, 0.24, 0.01), // 4
                CustomizedMazeParameters.makeRectangularMazeParams(24, 24, 0.25, 0.01), // 5
                CustomizedMazeParameters.makeRectangularMazeParams(25, 25, 0.26, 0.01), // 6
                CustomizedMazeParameters.makeRectangularMazeParams(26, 26, 0.27, 0.01), // 7
                CustomizedMazeParameters.makeRectangularMazeParams(27, 27, 0.28, 0.01), // 8
                CustomizedMazeParameters.makeRectangularMazeParams(28, 28, 0.29, 0.01), // 9
                CustomizedMazeParameters.makeRectangularMazeParams(29, 29, 0.30, 0.01) // 10
        ));

        for (int i = 0; i < tasks.size(); i++) {
            BufferedImage image = new CustomizedMazeGenerator(
                    tasks.get(i), String.valueOf(i + 1) + "/" + String.valueOf(tasks.size())
            ).generate().render();

            ImageIO.write(image, "png", new File("D:/Mazes/" + (i + 1) + ".png"));
        }
    }
}
