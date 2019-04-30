package undirectedWeightedGraph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import graph.Graph;
import undirectedWeightedGraph.UndirectedWeightedGraph;

public abstract class UndirectedWeightedGraphInstanceTest {
	/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
	 * Redistribution of original or derived work requires permission of course staff.
	 */
	    /*
	     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
	     */
	   public abstract UndirectedWeightedGraph<String> emptyInstance();
	    
	    /*
	     * Testing ConcreteVerticesGraph...
	     */
	    @Test
	    public void testAdd(){
	        //This test
	        //only 1 input, and tests must be legal clients
	        //  1. Input is already in graph
	        //  2. Input is not already in graph
	        
	    	UndirectedWeightedGraph<String> G1 = emptyInstance();
	        UndirectedWeightedGraph<String> G2 = emptyInstance();
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
	         * 1. target and source are same -> not a valid test, throws error
	         * 
	         *  Edge replacement
	         * 1. Edge already exists -> T3
	         * 2. Edge does not exist -> T2,T1
	         * 3. Edge exists, but has been set in different directions ->T4
	        **/
	        
	        String v1 = "v1";
	        String v2 = "v2";
	        String v3 = "v3";
	        String v4 = "v4";
	        
	        int w0 = 0;
	        int w1 = 65;
	        int w2 = Integer.MAX_VALUE;
	        
	        UndirectedWeightedGraph<String> G1 = emptyInstance();
	        
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
	        
	        UndirectedWeightedGraph<String> G2 = emptyInstance();
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
	    
	    public static String makeVertexLabel(Integer x,Integer y) {
			return new String(x+"," + y);
	    }
	    @Test
	    public void testRemove(){
	        /**
	         *  TODO test mutation does not occur in any of these methods
	         *  Input value
	         *  1. Vertex is present
	         *  2. vertex is not present
	         *  
	         *  3. edges are deleted correctly
	         */
	    	//T3
			Graph<String> gr1 = UndirectedWeightedGraph.empty();
			gr1.add(makeVertexLabel(0, 0));
			gr1.add(makeVertexLabel(0, 1));
			gr1.add(makeVertexLabel(0, 2));
			gr1.add(makeVertexLabel(1, 0));
			gr1.add(makeVertexLabel(1, 1));
			gr1.add(makeVertexLabel(1, 2));
			gr1.add(makeVertexLabel(2, 0));
			gr1.add(makeVertexLabel(2, 1));
			gr1.add(makeVertexLabel(2, 2));
			
			gr1.set("0,0","0,1",1);
			gr1.set("0,0","1,1",1);
			gr1.set("0,0","1,0",1);
			
			gr1.set("1,0","0,0",1);
			gr1.set("1,0","2,0",1);
			gr1.set("1,0","1,1",1);
			gr1.set("1,0","0,1",1);
			gr1.set("1,0","2,1",1);
			
			gr1.remove("1,1");
			Map<String,Integer> originDistances = new HashMap<>();
			Map<String,Integer> oneZeroDistance= new HashMap<>();
			
			originDistances.put("1,0",1);
			originDistances.put("0,1",1);
			
			oneZeroDistance.put("0,1",1);
			oneZeroDistance.put("0,0",1);
			oneZeroDistance.put("2,1",1);
			oneZeroDistance.put("2,0",1);
			
			assertEqualWithMessage(originDistances,gr1.targets(makeVertexLabel(0, 0)));
			assertEqualWithMessage(originDistances,gr1.sources(makeVertexLabel(0, 0)));
			assertEqualWithMessage(oneZeroDistance,gr1.targets(makeVertexLabel(1, 0)));
			assertEqualWithMessage(oneZeroDistance,gr1.sources(makeVertexLabel(1, 0)));
	    	
	        UndirectedWeightedGraph<String> G1 = emptyInstance();
	        
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
	        
	        G1.set(v1, v2, 1);
	        G1.set(v1, v3, 1);
	        G1.set(v1, v4, 1);
	        
	        G1.remove(v1);
	        assertTrue(G1.neighbours(v2).equals(new HashMap<String,Integer>()));
	        assertTrue(G1.neighbours(v3).equals(new HashMap<String,Integer>()));
	        assertTrue(G1.neighbours(v4).equals(new HashMap<String,Integer>()));
	    }
	    
	    public static void assertEqualWithMessage(Object o1, Object o2) {
	    	String notEq = "=/=";
	    	assertTrue(o1.toString() + notEq + o2.toString(), o1.equals(o2));
	    }
	    
	    @Test 
	    public void testVertices(){
	        /**
	         * Empty is already tested
	         * 1. 1 label
	         * 2. multiple labels
	         */
	    	UndirectedWeightedGraph<String> G1 = emptyInstance();
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
	    public void testNeighbours() {
	    	/**
	    	 * Vertex sanitation:
	    	 * 1. Vertex is not in graph ->T1
	    	 * 2. Vertex has no neighbours ->T2
	    	 * 3. Vertex has one or more neighbours ->T3
	    	 * 4. Neighbours removal with set 0 ->T4
	    	 * 
	    	 * Bi-directionality
	    	 * 1. edge set from one command is registered from both neighbours -> T4
	    	 */
	    	UndirectedWeightedGraph<String> G1 = emptyInstance();
	        Map<String, Integer> M1 = new HashMap<>(); 
	        Map<String, Integer> M2 = new HashMap<>(); 
	        String v1 = "v1";
	        String v2 = "v2";
	        String v3 = "v3";
	        String v4 = "v4";
	        
	        G1.add(v1);
	    	
	        //T2 Test that neighbours are empty when no edges set 
	        assertTrue(G1.neighbours(v1).isEmpty());
	        
	        
	        //T1 Test when vertex is not in graph
	        assertTrue(G1.neighbours(v4).isEmpty());
	        
	        G1.add(v2);
	        G1.add(v3);
	        G1.add(v4);
	        
	        //Adding edges
	        G1.set(v1, v2, 1);
	        G1.set(v1, v3, 1);
	        G1.set(v1, v4, 1);
	        //expected result
	        M1.put(v2,1);
	        M1.put(v3,1);
	        M1.put(v4,1);
	        
	        //T3 multiple neighbours expected
	        assertTrue(G1.neighbours(v1).equals(M1));
	        
	        //T4 Bi-directionality
	        M2.put(v1, 1);
	        assertTrue(G1.neighbours(v2).equals(M2));
	        
	        G1.set(v1, v2, 0);
	        M2.clear();
	        M1.remove(v2);
	        //Check that the neighbour has been removed from both sides
	        assertTrue("Expected: " + M2.toString()+ " but was: " + G1.neighbours(v2).toString(),G1.neighbours(v2).equals(M2));
	        assertTrue(G1.neighbours(v1).equals(M1));

	    }
	    
	  	    // Testing strategy for ConcreteVerticesGraph.toString()
	    //   TODO
	    
	    // TODO tests for ConcreteVerticesGraph.toString()
	    
	    /*
	     * Testing Vertex...
	     */
	    
	    // Testing strategy for Vertex
	    //   TODO

	    
	 
	    

}
