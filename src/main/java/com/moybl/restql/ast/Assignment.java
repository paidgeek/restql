package com.moybl.restql.ast;

public class Assignment extends AstNode {

	private AstNode destination;
	private AstNode source;

	public Assignment(AstNode destination, AstNode source) {
		this.destination = destination;
		this.source = source;
	}

	public AstNode getDestination() {
		return destination;
	}

	public AstNode getSource() {
		return source;
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String toString() {
		return "Assignment";
	}

}
