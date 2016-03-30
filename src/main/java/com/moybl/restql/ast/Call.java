package com.moybl.restql.ast;

public class Call extends AstNode {

	private AstNode target;
	private Sequence arguments;

	public Call(AstNode target, Sequence arguments) {
		this.target = target;
		this.arguments = arguments;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public AstNode getTarget() {
		return target;
	}

	public AstNode getArguments() {
		return arguments;
	}

	@Override
	public String toString() {
		return "Call";
	}

}
