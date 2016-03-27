package com.moybl.restql.ast;

public interface Visitor {

	void visit(Query acceptor);

	void visit(Assignment acceptor);

	void visit(Literal acceptor);

	void visit(Identifier acceptor);

	void visit(UnaryOperation acceptor);

	void visit(BinaryOperation acceptor);

	void visit(Call acceptor);

	void visit(Member acceptor);

}
