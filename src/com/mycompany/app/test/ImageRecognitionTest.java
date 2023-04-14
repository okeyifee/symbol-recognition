package com.mycompany.app.test;

import com.mycompany.app.main.ImageRecognition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ImageRecognitionTest {

    private ImageRecognition imageRecognition;

    @BeforeEach
    public void setUp() {
        imageRecognition = new ImageRecognition();
    }

    @Test
    public void testRecognizeImageWithNullInputShouldThrowException() {
        Exception exception = assertThrows(RuntimeException.class, () -> imageRecognition.recognizeImage(new int[][]{}));
        assertEquals("Invalid input supplied.", exception.getMessage());
    }

    @Test
    public void testRecognizeImageWithMatrixLessThan10Rows() {

        int[][] inputMatrix = {
                {0,0,0,0,0,0,0,0,0,0},
                {0,1,1,1,1,1,1,1,0,0},
                {0,1,1,1,1,1,1,1,0,0},
                {0,1,1,1,1,1,1,1,0,0},
                {0,1,1,0,1,1,1,1,0,0},
                {0,1,1,1,1,1,1,1,0,0},
                {0,1,1,1,1,1,1,1,0,0},
                {0,1,1,1,1,1,1,1,0,0},
                {0,1,1,1,1,1,1,1,0,0}
        };

        Exception exception = assertThrows(RuntimeException.class, () -> imageRecognition.recognizeImage(inputMatrix));
        assertEquals("Invalid input supplied.", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("provideMatrixDataWithLessThan10Columns")
    void testRecognizeImageWithMatrixLessThan10Columns(int[][] inputMatrix, String expectedExceptionMessage) {

        Exception exception = assertThrows(RuntimeException.class, () -> imageRecognition.recognizeImage(inputMatrix));
        assertEquals(expectedExceptionMessage, exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("provideMatrixDataWithFilledRectangleAndUnfilledArea")
    public void testRecognizeImageGivenMatrixWithFilledRectangleAndUnfilledArea(int[][] inputMatrix, String expectedSymbol) {

        final String result = imageRecognition.recognizeImage(inputMatrix);
        assertEquals(expectedSymbol, result);
    }

    @ParameterizedTest
    @MethodSource("provideMatrixDataWithOverlappingRectangle")
    public void testRecognizeImageGivenMatrixWithOverlappingRectangle(int[][] inputMatrix, String expectedSymbol) {

        final String result = imageRecognition.recognizeImage(inputMatrix);
        assertEquals(expectedSymbol, result);
    }

    @ParameterizedTest
    @MethodSource("provideMatrixDataWithFilledRectangle")
    public void testRecognizeImageGivenMatrixWithFilledRectangle(int[][] inputMatrix, String expectedSymbol) {

        final String result = imageRecognition.recognizeImage(inputMatrix);
        assertEquals(expectedSymbol, result);
    }

    @ParameterizedTest
    @MethodSource("provideMatrixDataWithTwoRectanglesOnTopOfEachOther")
    public void testRecognizeImageGivenMatrixWithTwoRectanglesOnTopOfEachOther(int[][] inputMatrix, String expectedSymbol) {

        final String result = imageRecognition.recognizeImage(inputMatrix);
        assertEquals(expectedSymbol, result);
    }

    @ParameterizedTest
    @MethodSource("provideMatrixDataWithAnyConfiguration")
    public void testRecognizeImageGivenAnyConfiguration(int[][] inputMatrix, String expectedSymbol) {

        final String result = imageRecognition.recognizeImage(inputMatrix);
        assertEquals(expectedSymbol, result);
    }

    @Test
    public void testLoadLetterMatrixFromFile() {
        int[][] expectedMatrix = {
                {0,0,0,0,0,0,0,0,0,0},
                {0,1,1,1,1,1,1,1,0,0},
                {0,1,1,1,1,1,1,1,0,0},
                {0,1,1,1,1,1,1,1,0,0},
                {0,1,1,0,1,1,1,1,0,0},
                {0,1,1,1,1,1,1,1,0,0},
                {0,1,1,1,1,1,1,1,0,0},
                {0,1,1,1,1,1,1,1,0,0},
                {0,1,1,1,1,1,1,1,0,0},
                {0,1,1,1,1,1,1,1,0,0}
        };

        int[][] result = imageRecognition.loadLetterMatrixFromFile(new File("src/com/mycompany/app/test/resources/O.txt"));
        assertArrayEquals(expectedMatrix, result);
    }

    private static Stream<Arguments> provideMatrixDataWithLessThan10Columns() {
        int[][] inputMatrix = {
                {0,0,0,0,0,0,0,0,0},
                {0,1,1,1,1,1,1,1,0},
                {0,1,1,1,1,1,1,1,0},
                {0,1,1,1,1,1,1,1,0},
                {0,1,1,0,1,1,1,1,0},
                {0,1,1,1,1,1,1,1,0},
                {0,1,1,1,1,1,1,1,0},
                {0,1,1,1,1,1,1,1,0},
                {0,1,1,1,1,1,1,1,0},
                {0,1,1,1,1,1,1,1,0}
        };

        int[][] inputMatrix2 = {
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0}
        };
        return Stream.of(
                Arguments.of(inputMatrix, "Invalid input supplied."),
                Arguments.of(inputMatrix2, "Invalid input supplied.")
        );
    }

    private static Stream<Arguments> provideMatrixDataWithFilledRectangleAndUnfilledArea() {
        int[][] inputMatrix1 = {
                {0,0,0,0,0,0,0,0,0,0},
                {0,1,1,1,1,1,1,1,0,0},
                {0,1,1,1,1,1,1,1,0,0},
                {0,1,1,1,1,1,1,1,0,0},
                {0,1,1,0,1,1,1,1,0,0},
                {0,1,1,1,1,1,1,1,0,0},
                {0,1,1,1,1,1,1,1,0,0},
                {0,1,1,1,1,1,1,1,0,0},
                {0,1,1,1,1,1,1,1,0,0},
                {0,1,1,1,1,1,1,1,0,0},
        };

        int[][] inputMatrix2 = {
                {0,0,0,0,0,0,0,0,0,0},
                {0,1,1,1,1,1,1,1,0,0},
                {0,1,1,1,1,1,1,1,0,0},
                {0,1,1,1,1,1,1,1,0,0},
                {0,1,1,1,1,1,1,1,0,0},
                {0,1,1,1,1,1,1,1,0,0},
                {0,1,1,1,1,1,1,1,0,0},
                {0,1,1,1,1,1,0,1,0,0},
                {0,1,1,1,1,1,1,1,0,0},
                {0,1,1,1,1,1,1,1,0,0},
        };

        return Stream.of(
                Arguments.of(inputMatrix1, "O"),
                Arguments.of(inputMatrix2, "O")
        );
    }

    private static Stream<Arguments> provideMatrixDataWithOverlappingRectangle() {
        int[][] inputMatrix1 = {
                {0,1,1,1,0,0,0,0,0,0},
                {0,1,1,1,0,0,0,0,0,0},
                {0,1,1,1,0,0,0,0,0,0},
                {0,1,1,1,0,0,0,0,0,0},
                {0,1,1,1,0,0,0,0,0,0},
                {0,1,1,1,0,0,0,0,0,0},
                {0,1,1,1,0,0,0,0,0,0},
                {0,1,1,1,0,0,0,0,0,0},
                {0,1,1,1,0,0,0,0,0,0},
                {0,1,1,1,1,1,1,1,0,0},
        };

        int[][] inputMatrix2 = {
                {0,1,1,1,0,0,0,0,0,0},
                {0,1,1,1,0,0,0,0,0,0},
                {0,1,1,1,0,0,0,0,0,0},
                {0,1,1,1,0,0,0,0,0,0},
                {0,1,1,1,0,0,0,0,0,0},
                {0,1,1,1,0,0,0,0,0,0},
                {0,1,1,1,0,0,0,0,0,0},
                {0,1,1,1,0,0,0,0,0,0},
                {0,1,1,1,1,1,1,1,0,0},
                {0,1,1,1,1,1,1,1,0,0},
        };

        return Stream.of(
                Arguments.of(inputMatrix1, "L"),
                Arguments.of(inputMatrix2, "L")
        );
    }

    private static Stream<Arguments> provideMatrixDataWithFilledRectangle() {
        int[][] inputMatrix1 = {
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,1,1,1,1,1,1,0,0,0},
                {0,1,1,1,1,1,1,0,0,0},
                {0,1,1,1,1,1,1,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0}
        };

        int[][] inputMatrix2 = {
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,1,1,1,1,1,1,0,0,0},
                {0,1,1,1,1,1,1,0,0,0},
                {0,1,1,1,1,1,1,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0}
        };

        int[][] inputMatrix3 = {
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,1,1,1,1,1,1,0,0,0},
                {0,1,1,1,1,1,1,0,0,0},
                {0,1,1,1,1,1,1,0,0,0}
        };

        return Stream.of(
                Arguments.of(inputMatrix1, "I"),
                Arguments.of(inputMatrix2, "I"),
                Arguments.of(inputMatrix3, "I")
        );
    }

    private static Stream<Arguments> provideMatrixDataWithTwoRectanglesOnTopOfEachOther() {
        int[][] inputMatrix1 = {
                {0,0,1,1,1,1,1,1,1,1},
                {0,0,1,1,1,1,1,1,1,1},
                {0,0,0,0,0,0,1,1,1,0},
                {0,0,0,0,0,0,1,1,1,0},
                {0,0,0,0,0,0,1,1,1,0},
                {0,0,0,0,0,0,1,1,1,0},
                {0,0,0,0,0,0,1,1,1,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0}
        };

        int[][] inputMatrix2 = {
                {0,0,1,1,1,1,1,1,1,1},
                {0,0,1,1,1,1,1,1,1,1},
                {0,0,0,0,1,1,1,1,1,0},
                {0,0,0,0,1,1,1,1,1,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0}
        };

        int[][] inputMatrix3 = {
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,1,1,1,1,1,1,1,1},
                {0,0,1,1,1,1,1,1,1,1},
                {0,0,0,0,0,0,1,1,1,0},
                {0,0,0,0,0,0,1,1,1,0},
                {0,0,0,0,0,0,1,1,1,0},
                {0,0,0,0,0,0,1,1,1,0},
                {0,0,0,0,0,0,1,1,1,0},
                {0,0,0,0,0,0,0,0,0,0}
        };

        return Stream.of(
                Arguments.of(inputMatrix1, "T"),
                Arguments.of(inputMatrix2, "T"),
                Arguments.of(inputMatrix3, "T")
        );
    }

    private static Stream<Arguments> provideMatrixDataWithAnyConfiguration() {
        int[][] inputMatrix1 = {
                {0,0,0,0,0,0,0,0,0,0},
                {0,1,1,1,1,1,1,1,0,0},
                {0,1,1,1,1,1,1,0,0,0},
                {0,1,1,1,1,1,1,1,0,0},
                {0,1,1,1,1,1,1,1,0,0},
                {0,1,1,1,1,1,1,0,0,0},
                {0,1,1,1,1,1,1,1,0,0},
                {0,1,1,1,1,1,0,1,0,0},
                {0,1,1,1,1,1,1,1,0,0},
                {0,1,1,1,1,1,1,0,0,0},
        };

        int[][] inputMatrix2 = {
                {0,0,1,1,1,1,1,1,1,1},
                {0,0,1,1,1,1,1,1,1,1},
                {0,0,0,1,1,1,1,1,1,0},
                {0,0,0,1,1,1,1,1,1,0},
                {0,0,0,1,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,1,0,0,0,0,0,0},
                {0,0,0,1,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,1,0,0},
                {0,0,0,0,0,0,0,0,0,1}
        };

        int[][] inputMatrix3 = {
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,1,1,1,1,1,1,1,1},
                {0,0,1,1,1,1,1,1,1,1},
                {0,0,0,0,1,1,1,1,1,0},
                {0,0,0,0,0,0,1,1,1,0},
                {0,0,0,0,0,0,1,1,1,0},
                {0,0,0,0,0,0,1,1,1,0},
                {0,0,0,0,0,0,1,1,1,0},
                {0,0,0,0,0,0,0,0,0,0}
        };

        return Stream.of(
                Arguments.of(inputMatrix1, "X"),
                Arguments.of(inputMatrix2, "X"),
                Arguments.of(inputMatrix3, "X")
        );
    }
}