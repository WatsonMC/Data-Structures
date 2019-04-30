package undirectedWeightedGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

import org.junit.experimental.theories.Theories;

import java.util.HashSet;
public class ConcreteVerticesUndirectedWeightedGraph<L> implements UndirectedWeightedGraph<L>{
	/**
	 * An implementation of UndirectedWeighted\Graph.
	 * 
	 * <p>PS2 instructions: you MUST use the provided rep.
	 */
	    private final List<Vertex<L>> vertices = new ArrayList<>();
	    private Map<L, Vertex<L>> vertexLabelMap = new HashMap<>();
	    
	    // Abstraction function:
	    //   TODO
	    // Representation invariant:
	    //   TODO
	    // Safety from rep exposure:
	    //   TODO
	    
	    // TODO constructor
	        
	    // TODO checkRep
	    
	    @Override public boolean add(L vertex) {
	        //true if it was new
	        //false if it exists
	    	if(vertexLabelMap.keySet().contains(vertex)) {
	    		return false;
	    	}
	    	else {
	    		Vertex newVertex = new Vertex<L>(vertex);
	    		vertices.add(newVertex);
	    		vertexLabelMap.put(vertex, newVertex);
	    		return true;
	    	}
	        //for(Vertex<L> v: vertices){
	          //  if(vertex.equals(v.getLabel())){
	        //        return false;
	       //     }
	    //    }
	    //    vertices.add(new Vertex<L>(vertex));
	    //    return true;
	    }
	    
	    
	    /**
	     * Add, change, or remove a weighted undirected edge in this graph.
	     * If weight is nonzero, add an edge or update the weight of that edge;
	     * vertices with the given labels are added to the graph if they do not
	     * already exist.
	     * If weight is zero, remove the edge if it exists (the graph is not
	     * otherwise modified).
	     * 
	     * @param source label of the source vertex
	     * @param target label of the target vertex
	     * @param weight nonnegative weight of the edge
	     * @return the previous weight of the edge, or zero if there was no such
	     *         edge
	     */
	    @Override public int set(L source, L target, int weight) {
	        //first check whether edge exists
	        boolean isNewEdge;
	        boolean targetInGraph;
	        boolean sourceInGraph;
	        targetInGraph = this.add(target);
	        sourceInGraph =this.add(source);
	        isNewEdge = targetInGraph || sourceInGraph;
	        
	        //new edge
	        if(isNewEdge || !this.edgeExists(source, target)){
	            int prevWeight = 0;
	            
	            if(weight == 0){
	                //do nothing
	                return prevWeight;
	            }
	            else{
	                //add edge
	                    //add source and target to each others neighbours
	            	this.findVertex(source).setNeighbour(target, weight);
	            	this.findVertex(target).setNeighbour(source, weight);
	                    //add source to target
	                return prevWeight;
	            }
	        }
	        else{
	            //not a new edge
	            int prevWeight = this.findVertexWeight(source,target);

	            if(weight == 0){
	                //remove edge from vertices
	                this.deleteEdge(source,target);
	                return prevWeight;

	            }
	            else{
	                //update edge
	                this.deleteEdge(source,target);
	                this.findVertex(source).setNeighbour(target,weight);
	                this.findVertex(target).setNeighbour(source,weight);
	                return prevWeight;

	            }
	        }
	    }
	    
	    @Override public boolean remove(L vertex) {
	        //false if nothing removed
	        //true elsewise
	    	
	        Vertex<L> tempV = this.findVertex(vertex);
	        if(tempV == null){
	            //new vertex
	            return false;
	        }
	        else{
	        	//remove all associated pathes to that vertex from other vertices
	            for(L vertexLabel: tempV.getNeighbours().keySet()) {
	            	findVertex(vertexLabel).deleteEdge(tempV.getLabel());
	            }
	            vertices.remove(tempV);
	            vertexLabelMap.remove(vertex);
	            return true;
	        }
	            
	    }
	    
	    @Override public Set<L> vertices() {
	        Set<L> result =  new HashSet<>();
	        for(Vertex<L> v:this.vertices){
	            result.add(v.getLabel());
	        }
	        return result;
	    }
	    
	    
	    @Override
	    public Map<L, Integer> neighbours(L source){
	    	Vertex<L> tempV = this.findVertex(source);
	    	if(tempV!= null) {
	    		return new HashMap<L,Integer>(tempV.getNeighbours());
	    	}
	    	return new HashMap<L,Integer>();
	    }
	    /**
	     * Finds a given vertex given its label
	     * @param label
	     * the string value of the vertex
	     * @return
	     * vertex object or null if no vertex found
	     */
	    private Vertex<L> findVertex(L label){
	    	if(vertexLabelMap.containsKey(label)) {
	    		return vertexLabelMap.get(label);
	    	}
	    	return null;
	        //for(Vertex<L> v: vertices){
	           // if(v.getLabel().equals(label)){
	           //     //vertex found
	          //      return v;
	           // }
	      //  }
	      //  return null;
	    }
	    /**
	     * Returns the weight of the edge between two given vertices 
	     * - ASSUMES that edge exists, otherwiose throws exception
	     * @param v1
	     * Label of first vertex
	     * @param v2
	     * Label of second vertex
	     * @return
	     * the weight
	     */
	    private int findVertexWeight(L v1, L v2){
	        Vertex<L> vertex1 = findVertex(v1);
	        //check sources
	        int result = vertex1.getNeighbours().getOrDefault(v2, 0);
	        if (result == 0) {
	            throw new IllegalArgumentException("vertices given to find weight fucntion are not linked with an edge");
	        }
	        else {
	        	return result;
	        }
	    }
	    /**
	     * Returns boolean value indicating if the edge exists in the graph or not
	     * AASSUMES that the vertices exist in the graph
	     * @param v1Label
	     * Label of the first vertex
	     * @param v2Label
	     * label of the second vertex
	     * @return
	     * true iff there is an edge between the two vertices, false otherwise
	     */
	    private boolean edgeExists(L v1Label, L v2Label){
	        Set<L> neighbgoursFromV1 = this.findVertex(v1Label).getNeighbours().keySet();
	        Set<L> neighbgoursFromV2 = this.findVertex(v2Label).getNeighbours().keySet();
	        
	        boolean inV1 = neighbgoursFromV1.contains(v2Label);
	        boolean inV2 = neighbgoursFromV2.contains(v1Label);
	        if(inV1!=inV2) {
	        	throw new IllegalArgumentException("neighbour present for one vertex but not the other");
	        }
	        else {
	        	return inV1;
	        }
	    }
	    
	    /**
	     * Helper function to delete an edge given the vertex labels of the source and thetarget
	     * @param v1Label
	     * @param v2Label
	     * @return
	     * true if edge is deleted, else false if no edge existed;
	     */
	    private boolean deleteEdge(L v1Label, L v2Label){
	        boolean result1 = this.findVertex(v1Label).deleteEdge(v2Label);
	        boolean result2 = this.findVertex(v2Label).deleteEdge(v1Label);
	        
	        if(result1!= result2){
	            throw new IllegalArgumentException("an edge was present in one vertex but not another");
	        }
	        else{
	            return result1;
	        }
	    }


		@Override
		public Map<L, Integer> sources(L target) {
			return this.neighbours(target);
		}


		@Override
		public Map<L, Integer> targets(L source) {
			return this.neighbours(source);			
		}
	    
	    // TODO toString()
	    
}

/**
 * TODO specification
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex<L> {
    
    private L label;
    private Map<L,Integer> neighbours = new HashMap<>();
    private Map<L, Integer> sources = new HashMap<>();
    private Map<L, Integer> targets = new HashMap<>();
    
    // Abstraction function:
    // Stores the label of a vertex, 
    // A map of neihbours and the weights to those neighbours. This acts as source and target one in the same
    // Representation invariant:
    // Map of strings to integers represents the sources to the vertex
    // Map of strings to integers represents the targets from the vertex
    // String value r
    // Safety from rep exposure:
    //  - All data feilds private
    //  - No set method for label value - this cannot change
    //  - All methods recieve only immutable types
    //  - Where mutable types are returned, (maps) defensive copying is implemented
    
    public Vertex(L label){
        this.label = label;
    }
    
    private boolean checkRep(){
        /**
         * to check
         * 1. No sources and targets the same
         * 2. no sources or targets equal to label value
         * 3. all weights>0
         * 
         */
        
        if(this.targets.keySet().contains(this.label)
                || this.sources.keySet().contains(this.label)){
            //the vertex targets itself
            System.out.println("The vertex is a source or label for itself");
            return false;

        }
        for(L source: this.sources.keySet()){
            if(this.targets.keySet().contains(source)){
                //the source is a target
                System.out.println("There is a vertex stored as a source and also a target: " + source);
                return false;
            }
            if(this.sources.get(source) <=0){
               //source weight is wrong
                System.out.println("The source " + source + " has an invalid weight");
                return false;
            }
        }
        for(L target: this.targets.keySet()){
            if(this.targets.get(targets) <=0){
                System.out.println("The target " + target + " has an invalid weight");
                return false;
            }   
        }
        return true;
    }
    
    
    // TODO methods
    /**
     * Method to get the label of the vertex
     * @return
     * Returns vertex label
     */
    public L getLabel(){
        return this.label;
    }
    
    /**
     * Sets the neighbour value given a set weight. Delets target if weight is 0
     * @param neighbour
     * @param weight
     * @return previous weight of the neighbour edge
     */
    public int setNeighbour(L neighbour, Integer weight) {
    	if(neighbour.equals(this.label)){
            throw new IllegalArgumentException("trying to add label as neighbour");
        }
    	
    	int result = neighbours.getOrDefault(neighbour, 0);
    	if(result!=0 && weight ==0) {
    		//delete edge
    		this.neighbours.remove(neighbour);
    	}
    	else if(weight != 0) {
    		this.neighbours.put(neighbour, weight);
    	}
    	return result;
    }
    
    /**
     * Sets a target for the given vertex with the given weight. deletes target if weight 0
     * @param target
     * label of target vertex
     * @param weight
     * weight of edge
     * @return
     * returns previous edge weight or 0 if edge is new
     */
    public int setTarget(L target, Integer weight){
        if(target.equals(this.label)){
            throw new IllegalArgumentException("trying to add label as target");
        }
        
        
        int result = this.targets.getOrDefault(target,0);	//
        if(result!= 0 &&weight == 0){
            //delete edge
            this.targets.remove(target);
            return result;
        }
        else if(weight!= 0){
            this.targets.put(target, weight);
            return result;
        }
        else{
            return result;
        }
        
    }
    
    /**
     * Sets a source to the vertex with the given weight and label. deletes target if weight is 0
     * @param label
     *  label of vertex to be set as source
     * @param weight
     *  weight of source to vertex edge
     * @return
     *  previous weight or 0 if edge did not exist 
     */
    public int setSource(L source, Integer weight){
        if(source.equals(this.label)){
            throw new IllegalArgumentException("trying to add label as source");
        }
        int result = this.sources.getOrDefault(source,0);
        if(result != 0 && weight == 0 ){
            this.sources.remove(source);
            return result;
        }
        else if(weight!=0){
            this.sources.put(source, weight);
            return result;
        }
        else{
            //delete edge that doesnt exist. do nothing
            return result;
        }
    }
    
    /**
     * Returns a map of targets to the vertex and their weights
     * @return
     * The map of targets and their weights for the vertex object
     * returns a blank map if no sources exist
     */
    public Map<L, Integer> getTargets(){
        Map<L, Integer> result =  new HashMap<>(this.targets);
        return result;
    }

    //Returns map of neighhbours for the vertex
    public Map<L,Integer> getNeighbours(){
    	return new HashMap<L,Integer>(this.neighbours);
    }
	
    
    /**
     * Returns a map of sources to the vertex and their weights
     * @return
     * The map of sources and their weights for the vertex object
     * returns a blank map if no sources exist
     */
    public Map<L, Integer> getSources(){
        Map<L, Integer> result =  new HashMap<>(this.sources);
        return result;
    }
    // TODO toString()
    /**
     * Deletes an directionally unspecified edge from the vertex
     * @param v1Label
     * label of vertex fpr edge to be deleted
     * @return
     * returns true if an edge was deleted, false elsewise
     */
    public boolean deleteEdge(L v1Label){
        int weightFromEdge;
        weightFromEdge = setNeighbour(v1Label, 0);
        if(weightFromEdge !=0){
            return true;
        }
        return false;
    }
}

