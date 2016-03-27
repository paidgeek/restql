package com.moybl.restql.ast;

import java.util.List;

public class Sequence extends AstNode {

	private List<AstNode> elements;

	public Sequence(List<AstNode> elements) {
		this.elements = elements;
	}

	public List<AstNode> getElements() {
		return elements;
	}

	@Override
	public String toString() {
		return "Sequence";
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
