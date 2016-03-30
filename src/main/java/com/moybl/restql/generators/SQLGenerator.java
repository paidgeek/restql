package com.moybl.restql.generators;

import com.moybl.restql.Pair;
import com.moybl.restql.RestQLException;
import com.moybl.restql.Token;
import com.moybl.restql.ast.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SQLGenerator implements Visitor {

	private StringBuilder result;
	private String select, from, where;
	private List<Pair<String, String>> joins;

	public SQLGenerator() {
		result = new StringBuilder();
		select = null;
		from = null;
		where = null;
		joins = new ArrayList<Pair<String, String>>();
	}

	public void visit(Query acceptor) {
		for (AstNode element : acceptor.getElements()) {
			element.accept(this);
		}
	}

	public void visit(Assignment acceptor) {
		acceptor.getDestination()
				  .accept(this);
		result.append(" = ");
		acceptor.getSource()
				  .accept(this);
	}

	public void visit(Literal acceptor) {
		if (acceptor.getType() == Token.STRING) {
			result.append("'")
					.append(acceptor.getValue())
					.append("'");

		} else {
			result.append(acceptor.getValue()
										 .toString()
										 .replaceAll("\\.[0-9]$", ""));
		}
	}

	public void visit(Identifier acceptor) {
		result.append(acceptor.getName());
	}

	public void visit(UnaryOperation acceptor) {
		acceptor.getChild()
				  .accept(this);
	}

	public void visit(BinaryOperation acceptor) {
		result.append("(");
		acceptor.getLeft()
				  .accept(this);

		switch (acceptor.getOperator()) {
			case EQUAL:
				result.append(" = ");
				break;
			case NOT_EQUAL:
				result.append(" != ");
				break;
			case LESS:
				result.append(" < ");
				break;
			case LESS_OR_EQUAL:
				result.append(" <= ");
				break;
			case GREATER:
				result.append(" > ");
				break;
			case GREATER_OR_EQUAL:
				result.append(" >= ");
				break;
			case AND:
				result.append(" AND ");
				break;
			case OR:
				result.append(" OR ");
				break;
		}

		acceptor.getRight()
				  .accept(this);
		result.append(")");
	}

	public void visit(Call acceptor) {
		if (acceptor.getTarget() instanceof Identifier) {
			String stmt = ((Identifier) acceptor.getTarget()).getName();

			if (stmt.equals("select")) {
				acceptor.getArguments()
						  .accept(this);

				select = result.toString();
			} else if (stmt.equals("from")) {
				acceptor.getArguments()
						  .accept(this);

				from = result.toString();
			} else if (stmt.equals("where")) {
				acceptor.getArguments()
						  .accept(this);

				where = result.toString();
			} else if (stmt.equals("join")) {
				acceptor.getArguments()
						  .getElements()
						  .get(0)
						  .accept(this);

				Pair<String, String> join = new Pair<String, String>();
				join.setFirst(result.toString());
				result = new StringBuilder();

				acceptor.getArguments()
						  .getElements()
						  .get(1)
						  .accept(this);
				join.setSecond(result.toString());

				joins.add(join);
			}

			result = new StringBuilder();
		} else {
			throw new RestQLException();
		}
	}

	public void visit(Member acceptor) {
		acceptor.getTarget()
				  .accept(this);
		result.append(".");
		acceptor.getExpression()
				  .accept(this);
	}

	public void visit(Sequence acceptor) {
		Iterator<AstNode> i = acceptor.getElements()
												.iterator();

		while (i.hasNext()) {
			i.next()
			 .accept(this);

			if (i.hasNext()) {
				result.append(", ");
			}
		}
	}

	public String getResult() {
		StringBuilder query = new StringBuilder();

		if (select != null) {
			query.append("SELECT ")
				  .append(select)
				  .append(" ");

			query.append("FROM ")
				  .append(from)
				  .append(" ");
		}

		for (Pair<String, String> join : joins) {
			query.append("JOIN ")
				  .append(join.getFirst())
				  .append(" ON(")
				  .append(join.getSecond())
				  .append(") ");
		}

		if (where != null) {
			query.append("WHERE ")
				  .append(where)
				  .append(" ");
		}

		return query.toString()
						.trim();
	}

}
