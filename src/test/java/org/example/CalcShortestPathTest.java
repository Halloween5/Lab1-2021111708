package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CalcShortestPathTest {

    private static TextToDirectedGraph graphHelper;
    private static Map<String, Map<String, Integer>> directedGraph;

    @BeforeAll
    static void setup() throws IOException {
        graphHelper = new TextToDirectedGraph();
        String testFilePath = "test.txt";
        createTestFile(testFilePath);
        directedGraph = graphHelper.createDirectedGraph(testFilePath);
    }

    @Test
    void testWord1AndWord2NotInGraph() {
        String expectedOutput = "图中没有这个单词！";
        assertEquals(expectedOutput, getOutput(() -> graphHelper.calcShortestPath("nonexistent1", "nonexistent2", directedGraph)));
    }

    @Test
    void testWord1InGraphWord2NotInGraph() {
        String expectedOutput = "图中没有这个单词！";
        assertEquals(expectedOutput, getOutput(() -> graphHelper.calcShortestPath("worda", "nonexistent", directedGraph)));
    }

    @Test
    void testWord1NotInGraphWord2InGraph() {
        String expectedOutput = "图中没有这个单词！";
        assertEquals(expectedOutput, getOutput(() -> graphHelper.calcShortestPath("nonexistent", "wordb", directedGraph)));
    }

    @Test
    void testWordsInGraphButNoPath() {
        String expectedOutput = "最短路径为: worda wordd \r\n" +
                "路径长度为: 1\r\n" +
                "图已生成，并在文件 DirectedGraph.png 中高亮显示最短路径";
        assertEquals(expectedOutput, getOutput(() -> graphHelper.calcShortestPath("worda", "wordd", directedGraph)));
    }

    @Test
    void testShortestPathExists() {
        String expectedOutput = "最短路径为: worda wordb wordc \r\n路径长度为: 2\r\n图已生成，并在文件 DirectedGraph.png 中高亮显示最短路径";
        assertEquals(expectedOutput, getOutput(() -> graphHelper.calcShortestPath("worda", "wordc", directedGraph)));
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
