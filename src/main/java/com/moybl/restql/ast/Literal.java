package com.moybl.restql.ast;

import com.moybl.restql.Token;

public class Literal extends AstNode {

	private Object value;
	private Token type;

	public Literal(Object value, Token type) {
		this.value = value;
		this.type = type;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public Object getValue() {
		return value;
	}

	public Token getType() {
		return type;
	}

	@Override
	public String toString() {
		return String.format("Literal(%s, %s)", type, value);
	}

}
