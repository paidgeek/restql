package com.moybl.restql.ast;

public class Literal extends AstNode {

	public enum Type {
		INTEGER, DECIMAL, STRING
	}

	private Object value;
	private Type type;

	public Literal(Object value, Type type) {
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

	public Type getType() {
		return type;
	}

	@Override
	public String toString() {
		return String.format("Literal(%s)", value);
	}

}
