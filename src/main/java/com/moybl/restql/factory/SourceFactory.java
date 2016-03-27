package com.moybl.restql.factory;

import com.moybl.restql.ast.*;

import java.util.Iterator;

public class SourceFactory implements Visitor {

	public static String build(AstNode root) {
		SourceFactory sourceFactory = new SourceFactory();
		root.accept(sourceFactory);

		return sourceFactory.build();
	}

	private StringBuilder query;

	public SourceFactory() {
		query = new StringBuilder();
	}

	public String build() {
		return query.toString();
	}

	public void visit(Query acceptor) {
		Iterator<AstNode> i = acceptor.getElements()
												.iterator();

		while (i.hasNext()) {
			i.next()
			 .accept(this);

			if (i.hasNext()) {
				query.append("&");
			}
		}
	}

	public void visit(Assignment acceptor) {
		acceptor.getDestination()
				  .accept(this);
		query.append("=");
		acceptor.getSource()
				  .accept(this);
	}

	public void visit(Literal acceptor) {
		switch (acceptor.getType()) {
			case NUMBER:
				query.append(acceptor.getValue());
				break;
			case STRING:
				query.append("'");
				query.append(acceptor.getValue());
				query.append("'");
				break;
		}
	}

	public void visit(Identifier acceptor) {
		query.append(acceptor.getName());
	}

	public void visit(UnaryOperation acceptor) {
	}

	public void visit(BinaryOperation acceptor) {
		acceptor.getLeft()
				  .accept(this);

		switch (acceptor.getOperator()) {
			case EQUAL:
				query.append(":");
				break;
			case NOT_EQUAL:
				query.append("!:");
				break;
			case LESS:
				query.append("<");
				break;
			case LESS_OR_EQUAL:
				query.append("<:");
				break;
			case GREATER:
				query.append(">");
				break;
			case GREATER_OR_EQUAL:
				query.append(">:");
				break;
		}

		acceptor.getRight()
				  .accept(this);
	}

	public void visit(Call acceptor) {
		acceptor.getTarget()
				  .accept(this);
		query.append("(");
		acceptor.getArguments()
				  .accept(this);
		query.append(")");
	}

	public void visit(Member acceptor) {
		acceptor.getTarget()
				  .accept(this);
		query.append(".");
		acceptor.getExpression()
				  .accept(this);
	}

	public void visit(Sequence sequence) {
		Iterator<AstNode> i = sequence.getElements()
												.iterator();

		while (i.hasNext()) {
			i.next()
			 .accept(this);

			if (i.hasNext()) {
				query.append(",");
			}
		}
	}
}
