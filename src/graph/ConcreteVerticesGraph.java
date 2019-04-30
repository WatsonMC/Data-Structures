/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;


/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph<L> implements Graph<L> {
    
    private final List<Vertex<L>> vertices = new ArrayList<>();
    
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
        for(Vertex<L> v: vertices){
            if(vertex.equals(v.getLabel())){
                return false;
            }
        }
        vertices.add(new Vertex<L>(vertex));
        return true;
    }
    
    
    /**
     * Add, change, or remove a weighted directed edge in this graph.
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
        
        if(isNewEdge || !this.edgeExists(source, target)){
            int prevWeight = 0;
            
            if(weight == 0){
                //do nothing
                return prevWeight;
            }
            else{
                //add edge
                    //add target to source
                this.findVertex(source).setTarget(target,weight);
                this.findVertex(target).setSource(source,weight);
                    //add source to target
                return prevWeight;
            }
        }
        else{
            //not a new edge
            int prevWeight = this.findVertexWeight(source,target);

            if(weight == 0){
                //remove edge
                this.deleteEdge(source,target);
                return prevWeight;

            }
            else{
                //update edge
                this.deleteEdge(source,target);
                this.findVertex(source).setTarget(target,weight);
                this.findVertex(target).setSource(source,weight);
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
        	//TODO remove all targets and sources when a vertex is removed, bug currently
            vertices.remove(tempV);
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
    
    @Override public Map<L, Integer> sources(L target) {
        //find vertex object
        Vertex<L> tempV = this.findVertex(target);
        if(tempV !=null){
            //vertex is in graph
            return new HashMap<L, Integer>(tempV.getSources());
        }
        return new HashMap<>();
    }
    
    @Override public Map<L, Integer> targets(L source) {
        Vertex<L> tempV = this.findVertex(source);
        if(tempV != null){
            //vertex is in graph
            return new HashMap<L, Integer>(tempV.getTargets());
        }
        return new HashMap<>();
    }
    
    /**
     * Finds a given vertex given its label
     * @param label
     * the string value of the vertex
     * @return
     * vertex object or null if no vertex found
     */
    private Vertex<L> findVertex(L label){
        for(Vertex<L> v: vertices){
            if(v.getLabel().equals(label)){
                //vertex found
                return v;
            }
        }
        return null;
    }
    /**
     * Returns the weight of the edge between two given vertices 
     * - ASSUMES that edge exists
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
        int result = vertex1.getSources().getOrDefault(v2,0);
        if(result == 0){
            result = vertex1.getTargets().getOrDefault(v2,0);
        }
        if(result == 0){
            throw new IllegalArgumentException("vertices given to find weight fucntion are not linked with an edge");
        }
        return result;        
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
        Set<L> sourcesFromV1 = this.findVertex(v1Label).getSources().keySet();
        Set<L> targetsFromV1 = this.findVertex(v1Label).getTargets().keySet();
        if(sourcesFromV1.contains(v2Label)
                ||targetsFromV1.contains(v2Label)){
            return true;
        }
        return false;
    }
    
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
    private Map<L, Integer> sources = new HashMap<>();
    private Map<L, Integer> targets = new HashMap<>();
    
    // Abstraction function:
    // Stores the label of a vertex, 
    //  A list of targets from the vertex and their weights
    // and a list of sources to the vertex with their weights
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
        
        int result = this.targets.getOrDefault(target,0);
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
        weightFromEdge = setTarget(v1Label,0);
        if(weightFromEdge !=0){
            return true;
        }
        weightFromEdge = setSource(v1Label,0);
        if(weightFromEdge !=0){
            //edge found
            return true;
        }
        return false;
    }
    
}
