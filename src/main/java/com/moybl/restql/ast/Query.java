package com.moybl.restql.ast;

import java.util.List;

public class Query extends AstNode {

	private List<AstNode> elements;

	public Query(List<AstNode> elements) {
		this.elements = elements;
	}

	public List<AstNode> getElements() {
		return elements;
	}

	@Override
	public String toString() {
		return "Query";
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
