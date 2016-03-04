package com.moybl.restql.ast;

public class Identifier extends AstNode {

	private String name;

	public Identifier(String name) {
		this.name = name;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return String.format("Identifier('%s')", name);
	}

}
