package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

//Simple graph implementation for CodeWars Kata
public class SimpleGraph {
	/**
	 * RI
	 * vertices -> list of strings holding the vertices of the graph, can o
	 * graphAL -> Adjacency list, vertices and list of their edges
	 * size -> integer representing size of graph
	 */
	private Set<String> vertices;
	private Map<String, List<SimpleEdge>> graphAL; 
	private int size; 
	
	public SimpleGraph() {
		this.vertices = new HashSet<>();
		this.graphAL = new HashMap<>();
		
	}
	
	// Adds a vertex given a string, and creates the adjacency list object
	public void addVertex(String vertex) {
		this.vertices.add(vertex);
		this.graphAL.put(vertex, new ArrayList<>());
		this.size++;
	}
	
	//Method to add an edge to the graph
	//Will add any sources/targets not already added to graph
	public void addEdge(String source, String target, int weight) {
		if(!vertices.contains(source)) {
			addVertex(source);
		}
		if(!vertices.contains(target)) {
			addVertex(target);
		}
		graphAL.get(source).add(new SimpleEdge(source,target,weight));
	}

	// returns list of given vertex edges
	public List<SimpleEdge> getAdjacentVertices(String vertex){
		return this.graphAL.get(vertex);
	}
	
}

// Simple edge class for Directed graph
class SimpleEdge{
	/**
	 * RI: 
	 * String target -> representing target vertex of edge
	 * String source -> source of edge, canot be target (not checked for)
	 * int weight -> weight of edge between source-> target
	 * 
	 */
	
	private String target;
	private String source;
	private int weight;
	
	public SimpleEdge(String s, String t, int weight) {
		this.weight = weight;
		this.target = t;
		this.source = s;
	}
	
	// --------
	//getter methods
	// --------
	public int getWeight() {
		return new Integer(this.weight);
	}
	
	public String getTarget() {
		return new String(this.target);
	}
	
}