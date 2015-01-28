package io.erb.prieglius.base;

import io.erb.prieglius.Edge;
import io.erb.prieglius.Message;
import io.erb.prieglius.Vertex;
import io.erb.prieglius.base.actions.Action;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * An abstract super-class for concrete vertex implementations. Provides the
 * decoupling of the {@link AbstractVertex} interface methodes and the actual
 * invocation of the compute method via the {@link Superstepping} interface.
 * 
 * @author benjamin
 *
 * @param <V>
 * @param <E>
 * @param <M>
 */
public abstract class AbstractVertex<V, E, M> implements Vertex<V, E, M>,
		Superstepping<E, M> {

	public final String id;

	private V value;

	public AbstractVertex(String id, V value) {
		this.id = id;
		this.value = value;
	}

	@Override
	public final void sendMessageTo(String destinationVertexId, M message) {
		outgoingMessages.add(new BasicMessage<M>(id, destinationVertexId,
				message));
	}

	public final void sendMessageToAllNeighbors(M message) {
		for (Edge<E> e : getEdges()) {
			sendMessageTo(e.getTargetVertexId(), message);
		}
	}

	private boolean isHalted = false;
	private int superstep;
	private List<Edge<E>> edges;
	Queue<Action> actions = new LinkedList<Action>();
	Queue<Message<M>> outgoingMessages = new LinkedList<>();

	@Override
	public final Superstepping.Result<M> doStep(List<Edge<E>> edges,
			List<Message<M>> messages, int superstep) {

		this.superstep = superstep;
		this.edges = edges;

		actions = new LinkedList<Action>();
		outgoingMessages = new LinkedList<>();
		isHalted = false;

		compute(messages);

		return new Superstepping.Result<M>(getId(), outgoingMessages, actions);
	}

	@Override
	public final List<Edge<E>> getEdges() {
		return edges;
	}

	@Override
	public final long getSuperstep() {
		return superstep;
	}

	@Override
	public final boolean isHalted() {
		return isHalted;
	}

	@Override
	public final void setVertexValue(V value) {
		this.value = value;
	}

	@Override
	public final String getId() {
		return id;
	}

	@Override
	public final V getVertexValue() {
		return value;
	}

	@Override
	public final void voteToHalt() {
		isHalted = true;
	}

}
