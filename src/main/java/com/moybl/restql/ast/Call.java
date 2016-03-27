package com.moybl.restql.ast;

public class Call extends AstNode {

	private AstNode target;
	private AstNode arguments;

	public Call(AstNode target, AstNode arguments) {
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
