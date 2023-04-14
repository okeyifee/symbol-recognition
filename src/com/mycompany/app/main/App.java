package com.mycompany.app.main;

import java.io.File;
import java.util.HashMap;

/**
 * The idea of the application consists in matrices of 0's and 1's representing letters.
 * Make your own algorithm to guess the correct letters or return X when there is no answer.
 * 
 * We'll value not only the algorithm, also code simplicity, structure, comments, etc.
 * 
 * Test cases are welcome if you have some spare time.
 */
public class App{

    public App() {}

    public static void main(String[] args) {
        App app = new App();
        ImageRecognition imageRecognition = new ImageRecognition();

        System.out.println(app.getMessage());
        System.out.println(app.letters);

        //Continue here or with other classes...
        //Using third-party libraries is allowed

        for (File file: app.letters.values()){
            int[][] letterMatrix = imageRecognition.loadLetterMatrixFromFile(file);
            System.out.println(imageRecognition.recognizeImage(letterMatrix));
        }
    }

    private final HashMap<String, File> letters = loadLetters();

    private HashMap<String, File> loadLetters() {
        final String path = "src/com/mycompany/app/main/resources/";
        File CFile = new File(path + "C.txt");
        File IFile = new File(path + "I.txt");
        File LFile = new File(path + "L.txt");
        File OFile = new File(path + "O.txt");
        File TFile = new File(path + "T.txt");
        File XFile = new File(path + "X.txt");
        
        assert CFile.exists();
        assert IFile.exists();
        assert LFile.exists();
        assert OFile.exists();
        assert TFile.exists();
        assert XFile.exists();

        HashMap<String, File> map = new HashMap<>();
        map.put("C", CFile);
        map.put("I", IFile);
        map.put("L", LFile);
        map.put("O", OFile);
        map.put("T", TFile);
        map.put("X", XFile);
        return map;
    }

    private String getMessage() {
        return "There we go...";
    }
}