/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {
    
    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();
    
    // Abstraction function:
    //   TODO
    // Represents a graph of vertices with string labels, and 
    // the edges connecting these vertices.
    // Representation invariant:
    //   TODO
    // vertices is the set of String objects representing the vertices
    // edges is a list of Edge objects connecting the vertices
    //  - edges cannot contain vertex label reference which is not in vertices
    //  - edges cannot contain the same target/source vertex label combination more than once
    //  - edges cannot contain an a/b and b/a target/source pair, graph is directed
    // Safety from rep exposure:
    //   - vertices and edges are both private, cannot be directly accessed
    //   - All methods take immutable types
    //   - All methods return immutable types, or return defensive copies of mutable types
    //   TODO
    
    // TODO constructor
    public  ConcreteEdgesGraph(){
        
        
    }
    
    // TODO checkRep
    
    /*
     * (non-Javadoc)
     * @see graph.Graph#add(java.lang.Object)
     */
    @Override public boolean add(L vertex) {
        //check if the vertex is already present in the set:
        if(this.vertices.contains(vertex)){
            // already present
            return false;
        }
        else{
            this.vertices.add(vertex);
            return true;
        }
        
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
        //Will only be false if both edges already exist
        // also adds vertices if they aren't here
        boolean isNewEdge;
        boolean targetInGraph;
        boolean sourceInGraph;
        targetInGraph = this.add(target);
        sourceInGraph =this.add(source);
        isNewEdge = targetInGraph || sourceInGraph;
        
        int prevWeight;
        //check if edge is already in there, including if it is reversed in direction
        if(isNewEdge||!this.edgeInList(source, target)){
          //edge is not in list already
            //create it
            prevWeight =0;
            if(weight == 0){
                return prevWeight;
            }
            else{
                Edge<L> newEdge = new Edge<>(source, target, weight);
                this.edges.add(newEdge);
                return prevWeight;
            }
        }
        else{
            //edge exists
            prevWeight =  removeEdge(source,target);
            if(weight == 0){
                //remove edge
                return prevWeight;
            }
            else{
                //create new edge, remove old edge
                Edge<L> newEdge = new Edge<>(source, target, weight);
                this.edges.add(newEdge);
                return prevWeight;
            }
        }
    }
    
    
    @Override public boolean remove(L vertex) {
        //remove vertex
        //remove edges associated with it
        //copy edgelist to use as iterator
        boolean isVertexInGraph = vertices.contains(vertex);
        List<Edge<L>> edgesCopy = new ArrayList<>(this.edges);
        
        //iterate over edge list
        for(Edge<L> e:edgesCopy){
            if(e.getSource().equals(vertex)
                    || e.getTarget() .equals(vertex)){
                //if the source or target is the vertex, remove that edge
                edges.remove(e);
            }
        }
        vertices.remove(vertex);
        return isVertexInGraph;
    }
    
    @Override public Set<L> vertices() {
        return new HashSet<>(this.vertices);
    }
    
    @Override public Map<L, Integer> sources(L target) {
        //cycle through edges
        Map<L, Integer> sources = new HashMap<>();
        for(Edge<L> e:this.edges){
            if(e.getTarget().equals(target)){
                //this edge has required target
                sources.put(e.getSource(), e.getWeight());
            }
        }
        return new HashMap<L, Integer>(sources);
        
    }
    
    @Override public Map<L, Integer> targets(L source) {
      //cycle through edges
        Map<L, Integer> targets = new HashMap<>();
        for(Edge<L> e:this.edges){
            if(e.getSource().equals(source)){
                //this edge has required source
                targets.put(e.getTarget(), e.getWeight());
            }
        }
        return new HashMap<L, Integer>(targets);
    }
    
    // TODO toString()
    public String toString(){
        String newline = System.getProperty("line.separator");
        String result = new String();
        
        // for each vertex
        for(L vertex:vertices){
            result += vertex + ":"+ newline;
            for(Edge<L> e:edges){
                if(e.getSource().equals(vertex)){
                    result+= "   " +e.toString() + newline;
                }
            }
            
        }
        return result;
    }
    
    private boolean checkRep(){
        /**
         * to check
         * 1. All edges in graph have vertices in graph
         * 2. All edges are only in edge list onceNo duplicate edges
         * 3. check edges are legit -> done in edge constructor
         * 
        */
        
        //1
        for(Edge<L> e:this.edges){
            if(!this.vertices.contains(e.getTarget())){
                //target not in graph
                System.out.println("Target for edge" + e.toString() +" is not in graph");
                return false;
            }
            if(!this.vertices.contains(e.getSource())){
                System.out.println("Source for edge" + e.toString() +" is not in graph");
                return false;
            }
        }
        //2
        //create set of 'pairs'
        Set<String> setOfEdges = new HashSet<>();
        for(Edge<L> e:this.edges){
            if(String.valueOf(e.getTarget()).compareTo(String.valueOf(e.getSource()))< 0){
                //source first
                setOfEdges.add(String.valueOf(e.getSource()) + String.valueOf(e.getTarget()));
                continue;
            }
            else{
                setOfEdges.add(String.valueOf(e.getTarget()) + String.valueOf(e.getSource()));
            }
            if(setOfEdges.size() != edges.size()){
                System.out.println("there are duplicate edges in the graph!");
                return false;
            }
        }
        return true;
    }

    /**
     * Removes the edge object from the list. Mutates the objects edgeList
     * @param source
     * String of source
     * @param target
     * String of target
     * @return
     * previous weight of edge
     */
    private int removeEdge(L source, L target){
        Edge<L> edgeToRemove = this.returnEdgeObject(source, target);
        int prevWeight = edgeToRemove.getWeight();
        this.edges.remove(edgeToRemove);
        return prevWeight;
    }
    
    /**
     * Internal helper method to find an edge object in the edge list given its source and target
     * Used when mutation of the object is required eg:
     *  - changing weight value of edge in a graph
     *  - removing edge from graph+
     *  - NOTE : the edge object can be in either direction. That is, String source == target, string target  == source
     * @param source
     * Source as a string
     * @param target
     * Target as a string
     * @return
     * Edge object if found, exception if not
     */
    private Edge<L> returnEdgeObject(L source, L target){
        for(Edge<L> e: this.edges){
            if((e.getSource().equals(source) 
                    && e.getTarget().equals(target))
                    || (e.getSource().equals(target) 
                    && e.getTarget().equals(source))){
                //found edge
                return e;
            }
        }
        throw new IllegalArgumentException("edge does not exist in list");
    }
    /**
     * Returns boolean true/false whether a given edge is in the edge list
     * Basically same as returnEdgeObject, except forchecking only
     *
     * @param source
     * String of source vertex
     * @param target
     * String of target vertex
     * @return
     * True if the source and target are connected by an edge in any direction
     * false if no such connection exists
     */
    private boolean edgeInList(L source, L target){
        for(Edge<L> e: this.edges){
            if((e.getSource().equals(source) 
                && e.getTarget().equals(target))
                || (e.getSource().equals(target) 
                && e.getTarget().equals(source))){
                //found edge
                return true;
            }
            
        }
        return false;
    }
}

/**
 * TODO specification
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Edge<L> {
    
    // TODO fields
    private Integer weight;
    private L target;
    private L source;
    
    // Abstraction function:
    // Edge represent a weighted edge between a source and a target in the graph
    
    // Representation invariant:
    //   TODO
    // Target and source are both held as private  Strings
    // - These are generated during the constructor call
    // - Target and source cannot be the same
    // Weight is held as a private Integer as well
    // - Weight cannot be negative
    
    // Safety from rep exposure:
    //   TODO
    //  Both target and source held as private  Strings, defined only once during the consturctor call
    //  weight is held privately as well. 
    //  weight can only be set using helper set method, which is private
    //  weight, target and source are all accessed by getter methods
    
    // TODO constructor
    //
    public Edge(L source,L target, Integer weight){
        if(target.equals(source)){
            // not viable edge
           throw new IllegalArgumentException("source cannot be equal to target");
        }
        else{
            this.target = target;
            this.source = source;
        }
        if(weight<0){
            throw new IllegalArgumentException("cannot have negative weight");
        }
        else{
            this.weight = weight;
        }
        this.checkRep();
        
    }
    
    /**
     * Method to check that the rep invariants are held true
     * @return true or false depending on whether rep invariants are true still
     */
    private boolean checkRep(){
        if(this.weight <0){
            System.out.println("weight is negative");
            return false;
        }
        if(this.source.equals(this.target)){
            System.out.println("Source and target are equal");
            return false;
        }
        if(this.source.equals(null) || this.target.equals(null)){
            System.out.println("Null value for either target or source");
            return false;
        }
        return true;
    }
    // TODO methods
    
    /**
     * Helper function to return the target of an edge object
     * @return
     * returns a copy of the target string value
     */
    public L getTarget(){
        return this.target;
        
    }
    
    /**
     * Helper function to return the source of an edge object
     * @return
     * returns a copy of the source string value
     */
    public L getSource(){
        return this.source;
        
    }
    
    /**
     * Helper function to return the weight of an edge object
     * @return
     * returns a copy of the weight string value
     */
    public Integer getWeight(){
        return new Integer(this.weight);
    }    
    
    /**
     * Takes in a non-negative integer value and assigns that value to the
     * weight of the edge object
     * @param newWeight
     * The weight value to be assigned
     */
    private void setWeight(Integer newWeight){
        this.weight = newWeight;
        if(!this.checkRep()){
            throw new IllegalArgumentException("weight value given is negative");
        }
        
    }
    
    /**
     * Method to compare two edge objects
     * @param E
     *  Edge object to be compared to the subject edge object which is called form
     * @return
     *  Returns true iff the source, target, and weight of both objects is the same
     *  and both objects are representationaly okay.
     */
    public boolean edgesEqual(Edge<L> E){
        if(E.getTarget().equals(this.target) 
                && E.getSource().equals(this.target)
                && E.getWeight() == this.weight
                && E.checkRep() == this.checkRep() ==true){
            return true;
        }
        else{
            return false;
        }
    }
    
    
    
    // TODO toString()
    
    /**
     * @ return
     * String value of the form source --weight--> target
     */
    public String toString(){
        String weightString = this.getWeight().toString();        
        String edgeString = this.getSource() + " --" + weightString + "--> " + this.getTarget();
        return edgeString;
    }
    
}
