package com.moybl.restql;

import com.moybl.restql.ast.Visitor;
import com.moybl.restql.ast.*;

public class DumpVisitor implements Visitor {

	private int ident;

	public void visit(Query query) {
		print(query);

		ident++;
		for (AstNode element : query.getElements()) {
			element.accept(this);
		}
		ident--;
	}

	public void visit(Assignment assignment) {
		print(assignment);

		ident++;
		assignment.getDestination()
					 .accept(this);
		assignment.getSource()
					 .accept(this);
		ident--;
	}

	public void visit(Literal acceptor) {
		print(acceptor);
	}

	public void visit(Identifier acceptor) {
		print(acceptor);
	}

	public void visit(UnaryOperation acceptor) {
		print(acceptor);
		ident++;
		acceptor.getChild()
				  .accept(this);
		ident--;
	}

	public void visit(BinaryOperation acceptor) {
		print(acceptor);
		ident++;
		acceptor.getLeft()
				  .accept(this);
		acceptor.getRight()
				  .accept(this);
		ident--;
	}

	public void visit(Call acceptor) {
		print(acceptor);
		ident++;
		acceptor.getTarget()
				  .accept(this);

		acceptor.getArguments()
				  .accept(this);

		ident--;
	}

	public void visit(Member acceptor) {
		print(acceptor);
		ident++;
		acceptor.getTarget()
				  .accept(this);
		acceptor.getExpression()
				  .accept(this);
		ident--;
	}

	public void visit(Sequence sequence) {
		print(sequence);

		ident++;
		for (AstNode element : sequence.getElements()) {
			element.accept(this);
		}
		ident--;
	}

	private void print(Object obj) {
		String message = obj.toString();

		for (int i = 0; i < ident * 2; i++) {
			message = " " + message;
		}

		System.out.println(message);
	}

}
