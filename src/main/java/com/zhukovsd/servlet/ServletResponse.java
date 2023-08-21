package com.zhukovsd.servlet;

import com.google.gson.Gson;
import com.zhukovsd.generator.MazeGraphKind;
import org.apache.tomcat.util.codec.binary.Base64;

import java.util.ArrayList;

/**
 * Class, which represents servlet response to the client. Sends to client as serialized string in response to its request.
 */
public class ServletResponse {
    /**
     * Success {@link #status} value.
     */
    static final int STATUS_SUCCESS = 0;

    /**
     * Unknown error {@link #status} value. Returns in case of unexpected exception.
     */
    static final int STATUS_UNKNOWN_ERROR = 1;

    /**
     * Invalid size parameters {@link #status} value.
     */
    static final int STATUS_INVALID_SIZE_PARAMETERS = 2;

    /**
     * Inner class, describing single requested graph view.
     */
    class ResponseMazeGraph {
        /**
         * Type of current graph view.
         */
        int graphKind;

        /**
         * Base-64 encoded png image of graph view graphic representation.
         */
        String graphImageString;

        /**
         * Create instance for given graph kind and image string.
         * @param graphKind graph kind
         * @param graphImageString base-64 encoded png image as string
         */
        public ResponseMazeGraph(int graphKind, String graphImageString) {
            this.graphKind = graphKind;
            this.graphImageString = graphImageString;
        }
    }

    /**
     * Response status. Codes described in constant values of this class.
     */
    private int status;

    /**
     * Custom response message.
     */
    private String message;

    /**
     * Graph views array of requested graph kinds.
     */
    private ArrayList<ResponseMazeGraph> graphs;

    /**
     * Hidden default constructor.
     */
    private ServletResponse() {}

    /**
     * Create response object with success status and initialized {@link #graphs field}.
     * @return created object
     */
    static ServletResponse createSuccessResponse() {
        ServletResponse result = new ServletResponse();
        result.status = STATUS_SUCCESS;
        result.graphs = new ArrayList<>();

        return result;
    }

    /**
     * Create response object with given error code and message.
     * @param responseStatus error response status
     * @param message error message
     * @return created object
     */
    static ServletResponse createErrorResponse(int responseStatus, String message) {
        ServletResponse result = new ServletResponse();
        result.status = responseStatus;
        result.message = message;

        return result;
    }

    /**
     * Add graph data to current object
     * @param graphKind graph kind
     * @param imageData base-64 encoded png image as byte array
     */
    void addGraph(MazeGraphKind graphKind, byte[] imageData) {
        graphs.add(
            new ResponseMazeGraph(graphKind.ordinal(), Base64.encodeBase64String(imageData))
        );
    }

    /**
     * Serialize current object to json.
     * @return json string
     */
    String toJson() {
        Gson gson = new Gson();

        return gson.toJson(this);
    }
}
