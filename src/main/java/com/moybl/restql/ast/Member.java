package com.moybl.restql.ast;

public class Member extends AstNode {

	private AstNode target;
	private AstNode expression;

	public Member(AstNode target, AstNode expression) {
		this.target = target;
		this.expression = expression;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public AstNode getTarget() {
		return target;
	}

	public AstNode getExpression() {
		return expression;
	}

	@Override
	public String toString() {
		return "Member";
	}

}
