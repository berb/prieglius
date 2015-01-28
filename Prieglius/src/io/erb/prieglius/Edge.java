package io.erb.prieglius;

/**
 * Represents an outbound (directed) edge of a vertex
 * 
 * @author benjamin
 *
 * @param <E>
 *            Type of the value that the edge can store
 */
public interface Edge<E> {
	/**
	 * Returns the vertex this edge is aiming to.
	 * 
	 * @return
	 */
	String getTargetVertexId();

	/**
	 * Returns the current edge value.
	 * 
	 * @return
	 */
	E getEdgeValue();

	/**
	 * Updates the value of this edge.
	 * 
	 * @param edgeValue
	 */
	void setEdgeValue(E edgeValue);

}
