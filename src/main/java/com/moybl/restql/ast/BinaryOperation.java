package com.moybl.restql.ast;

import com.moybl.restql.*;

public class BinaryOperation extends AstNode {

	private Token operator;
	private AstNode left;
	private AstNode right;

	public BinaryOperation(Token operator, AstNode left, AstNode right) {
		this.operator = operator;
		this.left = left;
		this.right = right;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public Token getOperator() {
		return operator;
	}

	public AstNode getLeft() {
		return left;
	}

	public AstNode getRight() {
		return right;
	}

	@Override
	public String toString() {
		return String.format("Binary(%s)", operator);
	}

}
