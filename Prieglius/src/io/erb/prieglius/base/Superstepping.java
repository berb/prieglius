package io.erb.prieglius.base;

import io.erb.prieglius.Edge;
import io.erb.prieglius.Message;
import io.erb.prieglius.base.actions.Action;

import java.util.List;
import java.util.Queue;

/**
 * Encapsulates the actual compute() invocation by introducing a doStep()
 * method, which does housekeeping before and after the compute() invocation.
 * 
 * @author benjamin
 *
 * @param <E>
 * @param <M>
 */
public interface Superstepping<E, M> {

	/**
	 * This methodes injects the parameters into the vertex before invoking compute()
	 * @param edges the current list of edges the vertex is connected with. 
	 * @param messages the list of messages received since the last superstep
	 * @param superstep The current superstep
	 * @return
	 */
	Result<M> doStep(List<Edge<E>> edges, List<Message<M>> messages,
			int superstep);

	/**
	 * Holder class for the results of a superstep execution of a single vertex.
	 * 
	 * @author benjamin
	 *
	 * @param <M>
	 */
	public static class Result<M> {
		/**
		 * All message emitted by the vertex in this superstep.
		 */
		public final Queue<Message<M>> messages;
		/**
		 * All topology change actions emitted by the vertex in this superstep.
		 */
		public final Queue<Action> actions;
		/**
		 * The vertex ID having emitted this result.
		 */
		public final String vertexId;

		public Result(String vertexId, Queue<Message<M>> messages,
				Queue<Action> actions) {
			this.vertexId = vertexId;
			this.messages = messages;
			this.actions = actions;
		}
	}
}
