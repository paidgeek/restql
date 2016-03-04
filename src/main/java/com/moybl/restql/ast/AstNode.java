package com.moybl.restql.ast;

public abstract class AstNode {

	public abstract void accept(Visitor visitor);

}
