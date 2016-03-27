package com.moybl.restql.ast;

import java.util.List;

public class Assignment extends AstNode {

	private AstNode target;
	private List<AstNode> elements;

	public Assignment(AstNode target, List<AstNode> elements) {
		this.target = target;
		this.elements = elements;
	}

	public AstNode getTarget() {
		return target;
	}

	public List<AstNode> getElements() {
		return elements;
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String toString() {
		return "Assignment";
	}

}
