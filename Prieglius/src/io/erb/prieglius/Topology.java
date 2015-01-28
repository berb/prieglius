package io.erb.prieglius;

import java.util.List;


/**
 * Represents the graph topology of a Pregel computation.
 * 
 * @author benjamin
 *
 */
public interface Topology {
	/**
	 * Returns all vertices of this topology.
	 * 
	 * @return
	 */
	List<Vertex<?,?,?>> getAllVertices();
	/**
	 * Returns all edges of this topology.
	 * @return
	 */
	List<Edge<?>> getAllEdges();
	
	/**
	 * Returns a vertex by its ID.
	 * @param id
	 * @return
	 */
	Vertex<?,?,?> getVertexById(String id);
	
	/**
	 * Adds a vertex to the topology.
	 * @param vertex
	 */
	void addVertex(Vertex<?,?,?> vertex);
	
	/**
	 * Adds an edge from the soure vertex to the destination vertex.
	 * @param sourceVertex
	 * @param e
	 * @param destinationVertex
	 */
	<E> void addEdge(Vertex<?,E,?> sourceVertex, E e, Vertex<?,?,?> destinationVertex);
	
	/**
	 * Returns all edges connected to the given vertex.
	 * @param vertex
	 * @return
	 */
	List<Edge<?>> getEdges(Vertex<?,?,?> vertex);
	
}
