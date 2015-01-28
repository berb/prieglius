package io.erb.prieglius.base;

import io.erb.prieglius.Edge;

public class BasicEdge<E> implements Edge<E> {
	
	private E value;
	private final String targetVertexId;
	
	public BasicEdge(E value, String targetVertexId) {
		this.targetVertexId = targetVertexId;
		this.value = value;
	}
	

	@Override
	public String getTargetVertexId() {
		return targetVertexId;
	}

	@Override
	public E getEdgeValue() {
		return value;
	}

	@Override
	public void setEdgeValue(E edgeValue) {
		value = edgeValue;
	}

}
