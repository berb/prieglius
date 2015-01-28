package io.erb.prieglius.runtime;

import io.erb.prieglius.Message;
import io.erb.prieglius.Topology;
import io.erb.prieglius.Vertex;
import io.erb.prieglius.base.AbstractVertex;
import io.erb.prieglius.base.Superstepping;
import io.erb.prieglius.base.actions.Action;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Provides the actual logic of the Pregel runtime.
 * 
 * @author benjamin
 *
 */
public class Prieglius {

	// Parallel actions of the same superstep are executed in a threadpool
	public static final int DEFAULT_WORKER_COUNT = Runtime.getRuntime().availableProcessors();
	
	
	/**
	 * Executes a Pregel-based computation on all vertices of the graph.
	 * @param topology The topology to execute
	 * @return Blocks until the computation has halted. Returns the resulting graph.
	 */
	@SuppressWarnings("rawtypes")
	public Topology submit(final Topology topology){
		
		 ExecutorService executorService = Executors.newFixedThreadPool(DEFAULT_WORKER_COUNT);
		
		
		final Map<String,List<Message<?>>> inboxes = new HashMap<String, List<Message<?>>>();
		
		for(Vertex v: topology.getAllVertices()){ 
			inboxes.put(v.getId(), new LinkedList<Message<?>>());
		}
		
		boolean isRunning = true;
		
		int superstep = 0;
		
		while(isRunning){
			
//			System.out.println("["+superstep+"]");
			
			List<Callable<Superstepping.Result<?>>> tasks = new LinkedList<Callable<Superstepping.Result<?>>>();
			
			int totalVertices = topology.getAllVertices().size();
			int haltedVertices = 0;
			
			for(Vertex v: topology.getAllVertices()){
					
				
				final AbstractVertex basicVertex = (AbstractVertex) v;
				final int currentSuperstep = superstep; 
				
				final List<Message<?>> inbox =new LinkedList<Message<?>>();
				for(Message m: inboxes.get(basicVertex.getId())){
					inbox.add(m);
				}
				
				tasks.add(new Callable<Superstepping.Result<?>>() {
					@SuppressWarnings("unchecked")
					@Override
					public Superstepping.Result<?> call() throws Exception {
						return basicVertex.doStep(topology.getEdges(basicVertex), inbox, currentSuperstep);
					}						
				});
				inboxes.put(basicVertex.id, new LinkedList<Message<?>>());
			
			}
		
			
			try {
				List<Future<Superstepping.Result<?>>> results = executorService.invokeAll(tasks);
				
				//Barrier point: wait for all nodes to be completed
				
				
				for(Future<Superstepping.Result<?>> result: results){
					Superstepping.Result<?> resultObject = result.get();
					for(Message m: resultObject.messages){
						inboxes.get(m.getDestinationVertexId()).add(m);

						//TODO: handle incoming messages for the aggregators  
					}
					
					
					for(Action a: resultObject.actions){
						//TODO: handle topology change actions 
					}
				}


			} catch (Exception e) {
				e.printStackTrace();
			} 

			for(Vertex v: topology.getAllVertices()){
				if(v.isHalted()){
					haltedVertices++;
				}
			}

			

			if(haltedVertices >= totalVertices){
				isRunning = false;
			}
			else{
				superstep++;
			}
		}
		executorService.shutdown();
		System.out.println("[Execution halted]");
		return topology;
		
	}
}
