package com.mycompany.app.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageRecognition {

    public final String recognizeImage(int[][] input) {
        if (input == null || input.length != 10 || input[0].length != 10){
            throw new RuntimeException("Invalid input supplied.");
        }

        if (isFilledRectangle(input)) return "I";
        if (isFilledRectangleWithUnfilledAreaInside(input)) return "O";
        if (areTwoRectanglesOverlapping(input)) return "T";
//        if (isFilledRectangleWithUnfilledAreaAndBrokenBorder(input)) return "C";
        if (isTwoRectanglesLyingOnTopOfEachOther(input)) return "L";
        return "X";  // If none of the above conditions match, it must be an "X" symbol
    }

    /**
     * This method checks if a filled rectangle exist from given matrix.
     *
     * @param matrix a 2D matrix.
     *
     * @return true if a filled rectangle exist else false.
     */
    private boolean isFilledRectangle(int[][] matrix) {
        Map<String, Integer> mapOfCoordinates = coordinatesOfOneInRectangle(matrix);

        int numRectangles = 0;
        int startCol = mapOfCoordinates.get("startY");
        int startRow = mapOfCoordinates.get("startX");
        int endCol = mapOfCoordinates.get("endY");
        int endRow = mapOfCoordinates.get("endX");

        // Check if the rectangle is valid
        if (startRow == -1 || endRow == -1) {
            return false; // no 1's found in the matrix
        }

        for (int i = startRow; i <= endRow; i++) {
            for (int j = startCol; j <= endCol; j++) {
                if (matrix[i][j] == 0) {
                    return false; // the rectangle is not filled with 1's
                }
            }
        }

        // Check if there are any additional rectangles
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 1 && (i < startRow || i > endRow || j < startCol || j > endCol)) {
                    numRectangles++;
                    if (numRectangles > 1) {
                        return false; // more than one rectangle found
                    }
                    // start new rectangle
                    startRow = i;
                    startCol = j;
                    endRow = i;
                    endCol = j;
                }
            }
        }
        return true;
    }

    /**
     * This method checks if a filled rectangle with unfilled area inside exist from given matrix.
     *
     * @param matrix a 2D matrix.
     *
     * @return true if a filled rectangle with unfilled area inside exist else false.
     */
    private boolean isFilledRectangleWithUnfilledAreaInside(int[][] matrix) {
        final Map<String, Integer> coordinates = coordinatesOfOneInRectangle(matrix);

        final int startX = coordinates.get("startX");
        final int startY = coordinates.get("startY");
        final int endX = coordinates.get("endX");
        final int endY = coordinates.get("endY");

        // Check if the start and end of 1's form a valid rectangle with a zero inside
        if (startX == -1 || endX == -1) {
            return false; // no 1's found
        }
        for (int i = startX; i <= endX; i++) {
            if (matrix[i][startY] == 0 || matrix[i][endY] == 0) {
                return false; // invalid rectangle
            }
        }
        for (int j = startY; j <= endY; j++) {
            if (matrix[startX][j] == 0 || matrix[endX][j] == 0) {
                return false; // invalid rectangle
            }
        }
        for (int i = startX + 1; i < endX; i++) {
            for (int j = startY + 1; j < endY; j++) {
                if (matrix[i][j] == 0) {
                    return true; // valid rectangle found with a zero inside
                }
            }
        }
        return false; // no zero inside the rectangle
    }

    /**
     * This method checks if 2 rectangles are lying on top of each other.
     *
     * @param sequence a 2D matrix.
     *
     * @return true if 2 rectangles are lying on top of each other else false.
     */
    private boolean isTwoRectanglesLyingOnTopOfEachOther(int[][] sequence) {
        final int length = sequence.length;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (sequence[i][j] == 1 && (i >= length - 1 || j >= length - 1 || sequence[i + 1][j] != 1
                        || sequence[i][j + 1] != 1 || sequence[i + 1][j + 1] != 1))
                {
                    // rectangles don't overlap, return false
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * This method checks if 2 rectangles overlaps from given matrix.
     *
     * @param matrix a 2D matrix.
     *
     * @return true if found else false.
     */
    private boolean areTwoRectanglesOverlapping(int[][] matrix) {
        int numRectangles = 0;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 1) {
                    // Found the top-left corner of a rectangle
                    numRectangles++;
                    if (numRectangles > 2) {
                        return false;
                    }
                    markRectangleAsVisited(matrix, i, j);
                }
            }
        }
        return numRectangles == 2;
    }

    /**
     * This method checks if a filled rectangle with unfilled area and broken border exist from given matrix.
     *
     * @param sequenceX a 2D matrix.
     *
     * @return true if found else false.
     */
    private boolean isFilledRectangleWithUnfilledAreaAndBrokenBorder(int[][] sequenceX) {
        int numRows = sequenceX.length;
        int numCols = sequenceX[0].length;
        int startRow = -1, endRow = -1, startCol = -1, endCol = -1;

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (sequenceX[i][j] == 1) {
                    if (startRow == -1) {
                        startRow = i;
                    }
                    if (startCol == -1 || j < startCol) {
                        startCol = j;
                    }
                    if (endCol == -1 || j > endCol) {
                        endCol = j;
                    }
                }
            }
            if (startRow != -1 && endRow == -1) {
                endRow = i;
            }
        }

        // Check if the rectangle is filled and there is an unfilled area inside
        if (startRow != -1 && endRow != -1 && startCol != -1 && endCol != -1) {
            for (int i = startRow; i <= endRow; i++) {
                for (int j = startCol; j <= endCol; j++) {
                    if (sequenceX[i][j] != 1) {
                        return false; // The rectangle is not filled
                    }
                    if (i > startRow && i < endRow && j > startCol && j < endCol && sequenceX[i][j] != 0) {
                        return false; // There is no unfilled area inside the rectangle
                    }
                }
            }
            return true; // The rectangle is filled and there is an unfilled area inside
        }
        return false; // No rectangle found
    }

    /**
     * This method reads the file to an array of integer (2D matrix).
     *
     * @param file the file to be read.
     *
     * @return int[][] a 2D matrix of values from file.
     */
    public int[][] loadLetterMatrixFromFile(File file) {
        int[][] matrix = new int[10][10];
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int row = 0;
            while ((line = br.readLine()) != null) {
                for (int col = 0; col < line.length(); col++) {
                    matrix[row][col] = Integer.parseInt(String.valueOf(line.charAt(col)));
                }
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matrix;
    }

    /**
     * This method returns the coordinates of the rectangle containing only 1's.
     *
     * @param matrix the input 2D matrix.
     *
     * @return Map<String, Integer> containing the coordinate keys and values
     */
    private Map<String, Integer> coordinatesOfOneInRectangle(int[][] matrix) {
        Map<String, Integer> coordinates= new HashMap<>();
        int startX = -1, startY = -1, endX = -1, endY = -1;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 1) {
                    if (startX == -1) {
                        startX = i;
                        startY = j;
                    }
                    endX = i;
                    endY = j;
                }
            }
        }
        coordinates.put("startX", startX);
        coordinates.put("startY", startY);
        coordinates.put("endX", endX);
        coordinates.put("endY", endY);
        return coordinates;
    }

    private void markRectangleAsVisited(int[][] grid, int top, int left) {
        int bottom = top;
        while (bottom < grid.length && grid[bottom][left] == 1) {
            int right = left;
            while (right < grid[0].length && grid[bottom][right] == 1) {
                grid[bottom][right] = 0;
                right++;
            }
            bottom++;
        }
    }
}
