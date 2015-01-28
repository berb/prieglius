package io.erb.prieglius;

/**
 * Represents a message sent between vertices. 
 * 
 * @author benjamin
 *
 * @param <M> The type of data of the actual message content 
 */
public interface Message<M> {
	/**
	 * Returns the actual message content.
	 * @return
	 */
	M getMessage();
	/**
	 * Returns the source vertex id of this message.
	 * @return
	 */
	String getSourceVertexId();
	/**
	 * Returns the destination vertex id of this message.
	 * @return
	 */
	String getDestinationVertexId();
}
