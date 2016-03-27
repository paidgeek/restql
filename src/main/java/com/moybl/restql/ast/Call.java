package com.moybl.restql.ast;

import java.util.List;

public class Call extends AstNode {

	private AstNode target;
	private List<AstNode> arguments;

	public Call(AstNode target, List<AstNode> arguments) {
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

	public List<AstNode> getArguments() {
		return arguments;
	}

	@Override
	public String toString() {
		return "Call";
	}

}
