package io.erb.prieglius.base;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.erb.prieglius.Edge;
import io.erb.prieglius.Topology;
import io.erb.prieglius.Vertex;

public class BasicTopology implements Topology{

	private final Map<String, Vertex<?,?,?>> vertexByName = new HashMap<String, Vertex<?,?,?>>();
	private final Map<String, List<Edge<?>>> edgesByVertex = new HashMap<String, List<Edge<?>>>();
	
	public BasicTopology() {
	}
	
	@Override
	public List<Vertex<?, ?, ?>> getAllVertices() {
		return new LinkedList<Vertex<?,?,?>>(vertexByName.values());
	}

	@Override
	public List<Edge<?>> getAllEdges() {
		List<Edge<?>> allEdges = new LinkedList<>();
		for(List<Edge<?>> edges: edgesByVertex.values()){
			for(Edge<?> edge: edges){
				allEdges.add(edge);
			}
		}
		return allEdges;
	}

	@Override
	public Vertex<?, ?, ?> getVertexById(String id) {
		return vertexByName.get(id);
	}

	@Override
	public void addVertex(Vertex<?, ?, ?> vertex) {
		vertexByName.put(vertex.getId(), vertex);
		//TODO: fix
		edgesByVertex.put(vertex.getId(), new LinkedList<Edge<?>>());
	}

	@Override
	public <E> void addEdge(Vertex<?, E, ?> sourceVertex, E e,
			Vertex<?, ?, ?> destinationVertex) {
		edgesByVertex.get(sourceVertex.getId()).add(new BasicEdge<E>(e, destinationVertex.getId()));		
	}

	@Override
	public List<Edge<?>> getEdges(Vertex<?, ?, ?> vertex) {
		return edgesByVertex.get(vertex.getId());
	}

	

}
