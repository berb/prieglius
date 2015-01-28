package io.erb.prieglius.base;

import io.erb.prieglius.Message;

public class BasicMessage<M> implements Message<M>{
	
	private final M message;
	private final String sourceId;
	private final String destinationId;
	
	public BasicMessage( String sourceId, String destinationId, M message) {
		this.message = message;
		this.sourceId = sourceId;
		this.destinationId = destinationId;
	}

	@Override
	public M getMessage() {
		return message;
	}

	@Override
	public String getSourceVertexId() {
		return sourceId;
	}
	
	@Override
	public String getDestinationVertexId() {
		return destinationId;
	}

}
