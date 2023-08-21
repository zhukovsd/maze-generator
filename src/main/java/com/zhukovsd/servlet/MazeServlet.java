package com.zhukovsd.servlet;

import com.zhukovsd.generator.DrawableMaze;
import com.zhukovsd.generator.MazeFactory;
import com.zhukovsd.generator.MazeGraphKind;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLDecoder;
import java.util.EnumSet;

/**
 * Servlet implementation of web-service generating mazes.
 */
@WebServlet("/Maze")
public class MazeServlet extends HttpServlet {
    /**
     * Request callback implementation. Request "data" parameter is a string  described in {@link ServletRequest ServletRequest class},
     * response format described in {@link ServletResponse ServletResponse class}.
     * @param req request object
     * @param resp response object
     * @throws ServletException possible exception type to be raised
     * @throws IOException possible exception type to be raised
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        // allow cross-domain ajax request
        resp.setHeader("Access-Control-Allow-Origin", "*");

        ServletOutputStream out = resp.getOutputStream();
        // request result is a serialized ServletResponse object
        ServletResponse servletResponse;

        try {
            // request "data" param is a json object, deserializable into ServletRequest object
            ServletRequest requestData = ServletRequest.createFromJSON(URLDecoder.decode(req.getParameter("data"), "UTF-8"));

            System.out.println(req.getRemoteAddr() + ", " + URLDecoder.decode(req.getParameter("data"), "UTF-8"));

            if (requestData.isSizeCorrect()) {
                DrawableMaze maze;

                switch (requestData.getGeometry()) {
                    case RECTANGULAR:
                        maze = MazeFactory.createRectangularMaze(requestData.size.rowCount, requestData.size.columnCount, 10, 20);
                        break;

                    case CIRCULAR:
                        maze = MazeFactory.createCircularMaze(requestData.size.circleCount, 1.5, 1.35, 10, 20);
                        break;

                    default:
                        maze = MazeFactory.createHexahedralMaze(requestData.size.rowCount, requestData.size.columnCount, 10, 20);
                        break;
                }

                maze.adjustSizeByWidth(requestData.availableWidth);
                maze.generate();

                Point size = maze.getSize();
                BufferedImage image = new BufferedImage(size.x, size.y, BufferedImage.TYPE_INT_ARGB);

                Graphics2D graphics = image.createGraphics();

                graphics.setBackground(new Color(255, 255, 255, 0));
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                EnumSet<MazeGraphKind> views = requestData.getViews();

                servletResponse = ServletResponse.createSuccessResponse();

                // paint all requested graphs and add it's graphic representations to servlet response object
                for (MazeGraphKind graphKind : views) {
                    graphics.clearRect(0, 0, size.x, size.y);
                    maze.paint(graphics, EnumSet.of(graphKind));

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ImageIO.write(image, "png", byteArrayOutputStream);
                    byteArrayOutputStream.close();

                    servletResponse.addGraph(graphKind, byteArrayOutputStream.toByteArray());
                }
            } else {
                servletResponse = ServletResponse.createErrorResponse(ServletResponse.STATUS_INVALID_SIZE_PARAMETERS, "Maze size parameters are incorrect");
            }

            out.write(servletResponse.toJson().getBytes());
        } catch (Exception e) {
            servletResponse = ServletResponse.createErrorResponse(ServletResponse.STATUS_UNKNOWN_ERROR, "Unexpected error");
            out.write(servletResponse.toJson().getBytes());
        } finally {
            out.close();
        }
    }
}
