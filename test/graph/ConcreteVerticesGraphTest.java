/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import org.junit.Test;

import graph.ConcreteVerticesGraph;
import graph.Graph;
import graph.Vertex;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph<String>();
    }
    
    /*
     * Testing ConcreteVerticesGraph...
     */
    @Test
    public void testAdd(){
        //This test
        //only 1 input, and tests must be legal clients
        //  1. Input is already in graph
        //  2. Input is not already in graph
        
        Graph<String> G1 = emptyInstance();
        Graph<String> G2 = emptyInstance();
        Set<String> S1 = new HashSet<String>();
        
        String input1 = "string1";
        String input2 = "string2";
        assertEquals("expected a successful add", G1.add(input1), true);
        assertEquals("expected a successful add", G2.add(input1), true);
        
        S1.add(input1);
        S1.add(input2);
        
        assertEquals("expected same vertices", G1.vertices(),G2.vertices());
        
        assertEquals("expected a failed add", G1.add(input1), false);
        assertEquals("expected same vertices after adding existing vertex", G1.vertices(),G2.vertices());

        assertEquals("expected a successful add", G2.add(input2), true);

        assertEquals("expected G2 to now contain second input", S1, G2.vertices());
    }
    
    /**
     * 
     */
    @Test
    public void testSet(){
        /**tests the set edge functionality of the graph class
        / Vertices present/not present
        /1. Both vertices present in graph -> T3
        /2. One vertex not present in graph -> T1
        /3. Neither vertex present in graph -> T2
         *
         * Weight value
         * 1. Weight is zero -> T3
         * 2. Weight > 0 -> T1
         * 3. Weight >>>0 -> T2
         * 
         *  Vertex values
         * 1. target and source are same -> not a valid test
         * 
         *  Edge replacement
         * 1. Edge already exists -> T3
         * 2. Edge does not exist -> T2,T1
         * 3. Edge exists in opposite direction -> T4
        **/
        
        String v1 = "v1";
        String v2 = "v2";
        String v3 = "v3";
        String v4 = "v4";
        
        int w0 = 0;
        int w1 = 65;
        int w2 = Integer.MAX_VALUE;
        
        Graph<String> G1 = emptyInstance();
        
        //T1
        G1.add(v1);
        assertEquals("expected previous weight to be 0", G1.set(v1, v2, w1),0);
        assertEquals("expected new vertex to be added", G1.vertices(), new HashSet<String>(Arrays.asList(v1,v2)));
        
        //T2
        assertEquals("expected previous weight to be 0", G1.set(v3, v4, w2),0);
        assertEquals("expected new vertices to be added", G1.vertices(), new HashSet<String>(Arrays.asList(v1,v2,v3,v4)));
        
        //T3
        assertEquals("expected previous weight to be " + String.valueOf(w2), G1.set(v3, v4, w0),w2);
        assertEquals("expected no change in vertexes", G1.vertices(), new HashSet<String>(Arrays.asList(v1,v2,v3,v4)));
        
        Graph<String> G2 = emptyInstance();
        G2.add(v1);
        G2.add(v2);
        G2.add(v3);
        G2.set(v1, v2,32);
        //T4
        assertEquals("expected previous weight to be 32", G2.set(v2, v1, 20),32);
        assertEquals("expected previous weight to be 20", G2.set(v1, v2, 32),20);
        G2.set(v1, v3,20);
        G2.set(v1, v4,30);
        System.out.println(G1.toString());
        System.out.println(G2.toString());

    }
    
    @Test
    public void testRemove(){
        /**
         *  
         *  TODO test mutation does not occur in any of these methods
         *  Input value
         *  1. Vertex is present
         *  2. vertex is not present
         *  
         *  
         *  
         */
        
        Graph<String> G1 = emptyInstance();
        
        String v1 = "v1";
        String v2 = "v2";
        String v3 = "v3";
        String v4 = "v4";
        
        G1.add(v1);
        G1.add(v2);
        G1.add(v3);
        
        //T1
        assertEquals("expected true, and v3 to be removed", G1.remove(v3), true);
        assertEquals("expected G1 to now have only v1 and v2", G1.vertices(),new HashSet<>(Arrays.asList(v1,v2)));
        
        Set<String> S1 = G1.vertices();
        
        //T2 
        assertEquals("expected false since v4 not in list", G1.remove(v4), false);
        assertEquals("expected same vertices as before", G1.vertices(),S1);
    }
    
    @Test 
    public void testVertices(){
        /**
         * Empty is already tested
         * 1. 1 label
         * 2. multiple labels
         */
        Graph<String> G1 = emptyInstance();
        String v1 = "v1";
        String v2 = "v2";
        Set<String> S1 = new HashSet<>();
        
        G1.add(v1);
        S1.add(v1);
        
        assertEquals("added just v1", G1.vertices(), S1);
        G1.add(v2);
        S1.add(v2);
        assertEquals("added just v1", G1.vertices(), S1);
    }
    
    @Test
    public void testSources(){
        /**
         * Graph config:
         * 1. Graph has 1 vertex ->T4
         * 2. Graph has 2 vertices
         * returns a map of the label to the weight from that source to the given target
         *  Vertex sanitation
         *  1. vertex is not in graph  -> T1
         *  2. vertex has no sources -> T2
         *  3. vertex has one or more sources  ->T3
         *  
         *  Weights
         *  1. weights are the same ->T3
         *  2. weight = 0 -> T3
         *  3. general ->T2
         */
        
        
        Graph<String> G1 = emptyInstance();
        Graph<String> G2 = emptyInstance();
        Map<String, Integer> M1 = new HashMap<>(); 
        Map<String, Integer> M2 = new HashMap<>(); 
        M1.clear();
        String v1 = "v1";
        String v2 = "v2";
        String v3 = "v3";
        String v4 = "v4";
        
        G1.add(v1);
        G1.add(v2);
        G1.add(v3);
        //T1
        assertEquals("expected empty map. Got " + G1.sources(v4) + "expected " + M1, G1.sources(v4), M1);
        
        //T2
        assertEquals("expected empty map", G1.sources(v3), M1);
        
        G1.set(v1, v2, 0);
        G1.set(v3, v2, 65);
        M1.put(v3, 65);
        G1.set(v4, v2, 65);
        M1.put(v4, 65);
        
        //T3
        assertEquals("expected populated map", G1.sources(v2), M1);
        
        G2.add(v1);
        
        //T4
        assertEquals("expected populated map", G2.sources(v2), M2);
        
        G2.set(v1,v2,45);
        M1.clear();
        M1.put(v1,45);
        //T5
        assertEquals("expected populated map", G2.sources(v2), M1);
                
    }
    @Test
    public void testTargets(){
        //Test method for the get targets routine
        /**
         * Graph config
         * 1. Graph has 1 element ->t4
         * 2. Graph has 2 elements ->T5
         * 
         * Source value
         * 1. vertex not in graph ->T1
         * 2. vertex with no targets ->T2
         * 3. vertex with one or more targets  ->T3
         * 
         * Weight value
         * 1. Weights same ->T3
         * 2. weights 0 ->T5 
         */

        Graph<String> G1 = emptyInstance();
        Graph<String> G2 = emptyInstance();
        Map<String, Integer> M1 = new HashMap<>(); 
        Map<String, Integer> M2 = new HashMap<>(); 
        String v1 = "v1";
        String v2 = "v2";
        String v3 = "v3";
        String v4 = "v4";
        
        G1.add(v1);
        G1.add(v2);
        G1.add(v3);
        //T1
        assertEquals("expected empty map", G1.targets(v4), M1);
        
        //T2
        assertEquals("expected empty map", G1.targets(v3), M1);
        
        G1.set(v1, v2, 0);
        G1.set(v1, v3, 65);
        M1.put(v3, 65);
        G1.set(v1, v4, 65);
        M1.put(v4, 65);
        
        //T3
        assertEquals("expected populated map, got " + G1.targets(v1), G1.targets(v1), M1);
        
        G2.add(v1);
        
        //T4
        assertEquals("expected blank map", G2.targets(v2), M2);
        
        G2.set(v1,v2,45);
        M1.clear();
        M1.put(v2,45);
        //T5
        assertEquals("expected populated map", G2.targets(v1), M1);
        
       
        
    }
    // Testing strategy for ConcreteVerticesGraph.toString()
    //   TODO
    
    // TODO tests for ConcreteVerticesGraph.toString()
    
    /*
     * Testing Vertex...
     */
    
    // Testing strategy for Vertex
    //   TODO
    @Test
    public void testVerticesConsctructorAndGetLabel(){
        Vertex<String> v1 = new Vertex<>("newLabel 1");
        Vertex<String> v2 = new Vertex<>("newLabel 2");
        Vertex<String> v3 = new Vertex<>("newLabel 3");
        
        assertTrue("expected newLabel 1", v1.getLabel().equals("newLabel 1"));
        assertTrue("expected newLabel 2", v2.getLabel().equals("newLabel 2"));
        assertTrue("expected newLabel 1", v3.getLabel().equals("newLabel 3"));
    }
    @Test
    public void sourcesTest(){
        /**
         * Tests the get and set sources method for the vertex class
         * 
         * getSources:
         * 1. Vertex has 0 sources ->T1
         * 2. Vertex has 1 source -> T2
         * 3. Vertex has many sources ->T3
         * 
         * SetSources:
         * Deletion:
         * 1. Deleting source that exists already -> T5
         * 2. deleting source that does not exist ->T6
         * 
         * Setting sources:
         * 1. setting brand new sourceT->T2, T7
         * 2. resetting source that exists -> T4
         * 
         */
        
        Vertex<String> v1 =  new Vertex<>("v1");
        Vertex<String> v2 =  new Vertex<>("v2");
        Vertex<String> v3 =  new Vertex<>("v3");
        Vertex<String> v4 =  new Vertex<>("v4");
        Map<String, Integer> M1 = new HashMap<>();
        //T1
        assertEquals("Expected blank map", M1, v1.getSources());
        
        v1.setSource(v2.getLabel(), 12);
        M1.put(v2.getLabel(), 12);
        //T2
        assertEquals("Expected 1 source", M1, v1.getSources());
        v1.setSource(v3.getLabel(), 14);
        M1.put(v3.getLabel(), 14);
        v1.setSource(v4.getLabel(), 12);
        M1.put(v4.getLabel(), 12);
        //T3
        assertEquals("Expected map with 3 sources", M1, v1.getSources());

            
        //-------SetSources test-----
        //T4
        int reset1 = v1.setSource(v4.getLabel(), 1);
        M1.put(v4.getLabel(),1);
        assertTrue("expected 12", reset1 == 12);
        assertEquals("Expected map v4 weight to change", M1, v1.getSources());
        
        //t5
        assertTrue("expected 1", v1.setSource(v4.getLabel(), 0) == 1);
        
        //T6
        assertTrue("expected 0", v1.setSource(v4.getLabel(), 0) == 0);

        //T7
        
        assertTrue("expected 0", v1.setSource(v4.getLabel(), 12) == 0);
        
        
    }
    // TODO tests for operations of Vertex
    
}
