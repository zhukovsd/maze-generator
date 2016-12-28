package com.zhukovsd;

import com.zhukovsd.generator.Maze;
import com.zhukovsd.generator.MazeFactory;
import com.zhukovsd.generator.MazeGraphKind;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EnumSet;

/**
 * Created by ZhukovSD on 19.09.2015.
 */
public class Main {
    /**
     * entry point
     * @param args arguments from console
     */
    public static void main(String[] args) throws IOException {
//        System.out.println("Hello, world!");

        Maze<?> maze = null;
        boolean flag = false;

        double highestRatio = 0;
        int attemptsCount = 0;

        while (!flag) {
//            maze = MazeFactory.createRectangularMaze(30, 30, 10, 20);
//            maze = MazeFactory.createCircularMaze(25, 1.5, 1.35, 10, 20);
            maze = MazeFactory.createHexahedralMaze(50, 50, 10, 20);

            maze.generate();

            double pathRatio = ((double) maze.shortestPath.getEdgeList().size()) / ((double) maze.pathSpanningTree.getEdgeList().size());
            highestRatio = Math.max(pathRatio, highestRatio);

            // 0.3 for rect, 0.15 for circular
            flag = pathRatio > 0.4;
            attemptsCount++;

            if (attemptsCount % 500 == 0) {
                System.out.println("count = " + attemptsCount + ", highestRatio = " + highestRatio);
            }
        }

        System.out.println("pathRatio = " + highestRatio + ", count = " + attemptsCount);

        Point size = maze.getSize();

        BufferedImage image = new BufferedImage(size.x, size.y, BufferedImage.TYPE_INT_ARGB);

        Graphics2D graphics = image.createGraphics();
        graphics.setBackground(new Color(255, 255, 255, 255));
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        graphics.clearRect(0, 0, size.x, size.y);
        maze.paint(graphics, EnumSet.of(MazeGraphKind.RESULTING_GRAPH, MazeGraphKind.SHORTEST_PATH));

        ImageIO.write(image, "png", new File("D:/1.png"));
    }
}
