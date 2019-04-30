package undirectedWeightedGraph;

import java.util.Map;
import java.util.Set;

import graph.ConcreteEdgesGraph;
import graph.Graph;

/**
 * A mutable undirected weighted graph with labeled vertices.
 * Vertices have distinct labels of an immutable type {@code L} when compared
 * using the {@link Object#equals(Object) equals} method.
 * Edges are undirected and have a positive weight of type {@code int}.
 * 
 * Extends but differs from Graph in its undirectionality
 *TODO fix this shit, should not have subclass completely change how the class operates
 * 
 * @param <L> type of vertex labels in this graph, must be immutable
 */
public interface UndirectedWeightedGraph<L> extends Graph<L>{
    
    /**
     * Create an empty graph.
     * 
     * @param <L> type of vertex labels in the graph, must be immutable
     * @return a new empty weighted directed graph
     */
    public static <L> Graph<L> empty() {
        return new ConcreteVerticesUndirectedWeightedGraph<L>();
    }
    
    public static <L> Graph<L> instanceOf(Class<L> aClass){
    	return new ConcreteVerticesUndirectedWeightedGraph<L>();
    }
    
    /**
     * Add a vertex to this graph.
     * 
     * @param vertex label for the new vertex
     * @return true if this graph did not already include a vertex with the
     *         given label; otherwise false (and this graph is not modified)
     */
    public boolean add(L vertex);
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
    @Override
    public int set(L source, L target, int weight);
    
    /**
     * Remove a vertex from this graph; any edges to or from the vertex are
     * also removed.
     * 
     * @param vertex label of the vertex to remove
     * @return true if this graph included a vertex with the given label;
     *         otherwise false (and this graph is not modified)
     */
    public boolean remove(L vertex);
    
    /**
     * Get all the vertices in this graph.
     * 
     * @return the set of labels of vertices in this graph
     */
    public Set<L> vertices();
    
    
    /**
     * Get the connected vertices which have an edge to the given source 
     * @param source vertex 
     * @return Map of vertices and Integer di
     * 
     */
    public Map<L, Integer> neighbours(L source);
    	
    
    /**
     * Get the source vertices with directed edges to a target vertex and the
     * weights of those edges.
     * 
     * @param target a label
     * @return a map where the key set is the set of labels of vertices such
     *         that this graph includes an edge from that vertex to target, and
     *         the value for each key is the (nonzero) weight of the edge from
     *         the key to target
     */
    
}
