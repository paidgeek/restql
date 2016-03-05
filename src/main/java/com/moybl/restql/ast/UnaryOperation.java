package com.moybl.restql.ast;

import com.moybl.restql.*;

public class UnaryOperation extends AstNode {

	private Token operator;
	private AstNode child;

	public UnaryOperation(Token operator, AstNode child) {
		this.operator = operator;
		this.child = child;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public Token getOperator() {
		return operator;
	}

	public AstNode getChild() {
		return child;
	}

	@Override
	public String toString() {
		return String.format("Unary(%s)", operator);
	}

}
