package io.erb.prieglius.base;

import io.erb.prieglius.Message;

import java.util.List;

public class BasicVertex<V,E,M> extends AbstractVertex<V,E,M> {

	public BasicVertex(String id, V value) {
		super(id, value);
	}

	@Override
	public void compute(List<Message<M>> messages) {
	}

}
