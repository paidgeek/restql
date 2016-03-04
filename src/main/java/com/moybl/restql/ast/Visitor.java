package com.moybl.restql.ast;

public interface Visitor {

	void visit(Literal acceptor);

	void visit(Identifier acceptor);

	void visit(UnaryOperation acceptor);

	void visit(BinaryOperation acceptor);

}
