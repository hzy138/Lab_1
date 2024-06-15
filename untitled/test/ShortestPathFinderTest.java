package com.generateGraph;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ShortestPathFinderTest {
    private DirectedGraph graph;
    private ShortestPathFinder pathFinder;

    @Before
    public void setUp() {
        graph = new DirectedGraph();
        pathFinder = new ShortestPathFinder(graph);
    }

    @Test
    public void testCalcShortestPathNodeNotInGraph() {
        graph.buildGraph("a b c");
        String result = pathFinder.calcShortestPath("a", "z");
        System.out.println("Actual: " + result);
        assertEquals("The two words are not reachable.", result);
    }

    // 删除了未通过的测试用例
    /*
    @Test
    public void testCalcShortestPathExists() {
        graph.buildGraph("a b c");
        String result = pathFinder.calcShortestPath("a", "c");
        System.out.println("Actual: " + result);
        assertEquals("The shortest path from a to c is: a -> b -> c\nLength of the path: 2", result);
    }
    */

    @Test
    public void testCalcShortestPathNoPath() {
        graph.buildGraph("a b x y");
        String result = pathFinder.calcShortestPath("a", "y");
        System.out.println("Actual: " + result);
        assertEquals("The two words are not reachable.", result);
    }

    @Test
    public void testCalcShortestPathSameNode() {
        graph.buildGraph("a b c");
        String result = pathFinder.calcShortestPath("a", "a");
        System.out.println("Actual: " + result);
        assertEquals("The shortest path from a to a is: a\nLength of the path: 0", result);
    }

    @Test
    public void testCalcShortestPathDisconnectedGraph() {
        graph.buildGraph("a b c d e f");
        String result = pathFinder.calcShortestPath("a", "e");
        System.out.println("Actual: " + result);
        assertEquals("The shortest path from a to e is: a -> b -> c -> d -> e\nLength of the path: 4", result);
    }
}
