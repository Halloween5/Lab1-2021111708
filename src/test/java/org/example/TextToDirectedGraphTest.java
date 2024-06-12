package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TextToDirectedGraphTest {

    private static TextToDirectedGraph graphHelper;
    private static Map<String, Map<String, Integer>> directedGraph;

    @BeforeAll
    static void setup() throws IOException {
        graphHelper = new TextToDirectedGraph();
        String testFilePath = "text.txt";
        createTestFile(testFilePath);
        directedGraph = graphHelper.createDirectedGraph(testFilePath);
    }

    @Test
    void testBridgeWordsExist1() {
        String expectedOutput = "The bridge words from wordb to wordd are: wordc";
        assertEquals(expectedOutput, getOutput(() -> graphHelper.queryBridgeWords("wordb", "wordd", directedGraph)));
    }

    @Test
    void testBridgeWordsExist2() {
        String expectedOutput = "The bridge words from worda to wordc are: wordb, wordd";
        assertEquals(expectedOutput, getOutput(() -> graphHelper.queryBridgeWords("worda", "wordc", directedGraph)));
    }

    @Test
    void testNoBridgeWords() {
        String expectedOutput = "No bridge words from wordd to worde!";
        assertEquals(expectedOutput, getOutput(() -> graphHelper.queryBridgeWords("wordd", "worde", directedGraph)));
    }

    @Test
    void testWord1NotInGraph() {
        String expectedOutput = "No bridge words from nonexistent to wordb!";
        assertEquals(expectedOutput, getOutput(() -> graphHelper.queryBridgeWords("nonexistent", "wordb", directedGraph)));
    }

    @Test
    void testWord2NotInGraph() {
        String expectedOutput = "No bridge words from worda to nonexistent!";
        assertEquals(expectedOutput, getOutput(() -> graphHelper.queryBridgeWords("worda", "nonexistent", directedGraph)));
    }

    @Test
    void testBothWordsNotInGraph() {
        String expectedOutput = "No bridge words from nonexistent1 to nonexistent2!";
        assertEquals(expectedOutput, getOutput(() -> graphHelper.queryBridgeWords("nonexistent1", "nonexistent2", directedGraph)));
    }

    @Test
    void testEmptyWord1() {
        String expectedOutput = "No bridge words from  to wordb!";
        assertEquals(expectedOutput, getOutput(() -> graphHelper.queryBridgeWords("", "wordb", directedGraph)));
    }

    @Test
    void testEmptyWord2() {
        String expectedOutput = "No bridge words from worda to !";
        assertEquals(expectedOutput, getOutput(() -> graphHelper.queryBridgeWords("worda", "", directedGraph)));
    }

    @Test
    void testSpecialCharacterInWord1() {
        String expectedOutput = "No bridge words from @special to wordb!";
        assertEquals(expectedOutput, getOutput(() -> graphHelper.queryBridgeWords("@special", "wordb", directedGraph)));
    }

    @Test
    void testSpecialCharacterInWord2() {
        String expectedOutput = "No bridge words from worda to #special!";
        assertEquals(expectedOutput, getOutput(() -> graphHelper.queryBridgeWords("worda", "#special", directedGraph)));
    }

    @Test
    void testShortestWord() {
        String expectedOutput = "No bridge words from a to b!";
        assertEquals(expectedOutput, getOutput(() -> graphHelper.queryBridgeWords("a", "b", directedGraph)));
    }

    @Test
    void testLongestWord() {
        String expectedOutput = "No bridge words from longworda to longwordb!";
        assertEquals(expectedOutput, getOutput(() -> graphHelper.queryBridgeWords("longworda", "longwordb", directedGraph)));
    }

    private String getOutput(Runnable runnable) {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        runnable.run();
        System.setOut(System.out);
        return outContent.toString().trim();
    }

    private static void createTestFile(String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("worda wordb wordc wordd worde worda wordd wordc");
        }
    }

}