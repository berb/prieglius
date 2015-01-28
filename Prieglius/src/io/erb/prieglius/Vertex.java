package io.erb.prieglius;

import java.util.List;

/**
 * Represents a vertex of the graph topology of a Pregel computation.
 * 
 * @author benjamin
 *
 * @param <V>
 *            The value type this vertex holds.
 * @param <E>
 *            The value type of the edges of this vertex.
 * @param <M>
 *            The value type of the message that this vertex exchanges.
 */
public interface Vertex<V, E, M> {

	/**
	 * Returns the ID of the current vertex
	 * 
	 * @return
	 */
	public String getId();

	/**
	 * Executes the actual computation step. This method is invoked for each
	 * superstep. If the vertices have sent messages to this vertex in the
	 * previous superstep, these messages are provided as parameter.
	 * 
	 * 
	 * @param messages
	 */
	public void compute(List<Message<M>> messages);

	/**
	 * Returns the current superstep.
	 * 
	 * @return
	 */
	public long getSuperstep();

	/**
	 * Returns the current value of this vertex.
	 * 
	 * @return
	 */
	public V getVertexValue();

	/**
	 * Sets the value of this vertex.
	 * 
	 * @param value
	 */
	public void setVertexValue(V value);

	/**
	 * Returns a list of all outgoing edges of this vertex.
	 * 
	 * @return
	 */
	public List<Edge<E>> getEdges();

	/**
	 * Sends the given message to the vertex with the given ID.
	 * 
	 * @param destinationVertexId
	 * @param message
	 */
	public void sendMessageTo(String destinationVertexId, M message);

	/**
	 * Signals to the runtime that this vertex is currently done. Note that the
	 * vertex may still be executed and even receive new messages in the next
	 * superstep, when other vertices have voted not to halt. Must be re-issued
	 * in every superstep unless the computation as eventually halted.
	 */
	public void voteToHalt();

	/**
	 * Returns whether the vertex has voted to halt.
	 * 
	 * @return
	 */
	public boolean isHalted();

}
