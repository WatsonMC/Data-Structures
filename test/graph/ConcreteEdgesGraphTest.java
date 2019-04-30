package graph;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import graph.ConcreteEdgesGraph;
import graph.Edge;
import graph.Graph;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override 
    public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph<String>();
    }
    
    
    /*
     * Testing ConcreteEdgesGraph...
     */
    
    // Testing strategy for ConcreteEdgesGraph.toString()
    //   TODO
    
    // TODO tests for ConcreteEdgesGraph.toString()
    
    /*
     * Testing Edge...
     */
    
    // Testing strategy for Edge
    //   TODO
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
        System.out.println("look here" + G1.toString());
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
    
    
    @Test 
    public void testEdgeConstruction(){
        //used to test whether the edge constructor works as intended
        int weight = 1;
        String source = "source";
        String target = "target";
        
        Edge e1 = new Edge(source,target,weight);
    }
    
    @Test
    public void testGetSetMethods(){
 
        int weight = 1;
        String source = "source";
        String target = "target";
        
        Edge e1 = new Edge(source,target,weight);
        
        //Test get Source   
        assertTrue("expect source = source, got" + e1.getSource(), e1.getSource().equals("source"));
        
        //Test get target
        assertTrue("expect target = target,", e1.getTarget().equals("target"));
        
        //Test get weight
        assertTrue("expect weight is 1", e1.getWeight() == 1);
        
        //Test set method
        int weight2 = 143;
        int weight3 = 0;
        //Edge is immutable, cannot change weight
     //   e1.setWeight(weight2);
       // assertTrue("expect weight is 1", e1.getWeight() == 143);  
        
       // e1.setWeight(weight3);
        //assertTrue("expect weight is 1", e1.getWeight() == 0);    
        
    }
    
    @Test
    public void testEdgePrint(){
        int weight = 123;
        String source = "a";
        String target = "b";
        
        Edge e1 = new Edge(source,target,weight);
        System.out.println(e1.toString());
        String result = "a --123--> b";
        
        assertTrue("expected "+ result + " got " + e1.toString(),e1.toString().equals(result));
        
   
    
    }
   
        
   }
    
    // TODO tests for operations of Edge
    
